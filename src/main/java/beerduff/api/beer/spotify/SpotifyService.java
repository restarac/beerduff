package beerduff.api.beer.spotify;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;

import beerduff.api.beer.Beer;

@Service
public class SpotifyService {
    private static final int PLAYLIST_LIMIT = 3;
    private static final int TRACKS_LIMIT = 5;

    @Qualifier("authenticated")
    @Autowired
    private SpotifyApi spotifyApi;

    public BeerWithSpotify findSpotifyPlaylistWithTracksBy(Beer beer) throws SpotifyWebApiException, IOException {
        List<PlaylistSimplified> playlists = searchPlaylistsBy(beer.getName());

        Function<PlaylistSimplified, SpotifyPlaylistWithTracks> convertPlaylistSimplified = tempPlaylist -> {
            try {
                List<PlaylistTrack> searchedTracks = searchTracksBy(tempPlaylist);
                List<SpotifyTracks> tracks = searchedTracks.stream()
                        .map(track -> new SpotifyTracks(track))
                        .collect(Collectors.toList());
                return new SpotifyPlaylistWithTracks(tempPlaylist, tracks);
            } catch (SpotifyWebApiException | IOException e) {
                return new SpotifyPlaylistWithTracks(tempPlaylist);
            }
        };
        List<SpotifyPlaylistWithTracks> playlistWithTracks = playlists.parallelStream()
                .map(convertPlaylistSimplified)
                .collect(Collectors.toList());

        return new BeerWithSpotify(beer, playlistWithTracks);
    }

    private List<PlaylistSimplified> searchPlaylistsBy(String beername) throws SpotifyWebApiException, IOException {
        PlaylistSimplified[] items = spotifyApi.searchPlaylists(beername)
                .limit(PLAYLIST_LIMIT)
                .offset(0)
                .build()
                .execute()
                .getItems();
        return Arrays.asList(items);
    }

    private List<PlaylistTrack> searchTracksBy(PlaylistSimplified playlist) throws SpotifyWebApiException, IOException {
        String user_id = playlist.getOwner().getId();
        String playlist_id = playlist.getId();
        PlaylistTrack[] items = spotifyApi.getPlaylistsTracks(user_id, playlist_id)
                .limit(TRACKS_LIMIT)
                .build()
                .execute()
                .getItems();
        return Arrays.asList(items);
    }
}