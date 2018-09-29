package beerduff.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.util.ArrayUtils;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;

import beerduff.api.BeerController.BeerWithSpotify;
import beerduff.api.BeerController.SpotifyPlaylistWithTracks;
import beerduff.api.BeerController.SpotifyTracks;

@RepositoryRestController
public class BeerController {
    @Autowired
    private BeerRepository repository;
    @Autowired
    private SpotifyService service;

    @RequestMapping(method = GET, value = "/beers/search/findBestPlaylistBeer")
    public @ResponseBody ResponseEntity<?> findByAverageTemperature(String temperature)
            throws SpotifyWebApiException, IOException {
        if (StringUtils.isEmpty(temperature)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("missing param 'temperature'");
        }

        List<Beer> beers = repository.findByAverageTemperature(Integer.parseInt(temperature));

        if (beers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no beers found!");
        }

        Beer firstBeer = beers.get(0);
        BeerWithSpotify beerWithSpotify = service.findSpotifyPlaylistWithTracksBy(firstBeer);

        Resource<?> resources = new Resource<>(beerWithSpotify);

        return ResponseEntity.ok(resources);
    }

    @Service
    class SpotifyService {
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

    class SpotifyPlaylistWithTracks {
        private String name;
        private List<SpotifyTracks> tracks;

        public SpotifyPlaylistWithTracks(PlaylistSimplified playlist, List<SpotifyTracks> tracks) {
            this.name = playlist.getName();
            this.tracks = tracks;
        }

        public SpotifyPlaylistWithTracks(PlaylistSimplified playlist) {
            this.name = playlist.getName();
            this.tracks = Arrays.asList(SpotifyTracks.notFound());
        }

        public String getName() {
            return name;
        }

        public List<SpotifyTracks> getTracks() {
            return tracks;
        }

        @Override
        public String toString() {
            return "SpotifyPlaylistWithTracks [beerStyle=" + name + ", tracks=" + tracks + "]";
        }
    }

    static class SpotifyTracks {
        private String name;
        private String artist;
        private String link;

        private SpotifyTracks() {
        }

        public SpotifyTracks(PlaylistTrack track) {
            this.name = track.getTrack().getName();
            this.artist = track.getTrack().getArtists()[0].getName();
            this.link = track.getTrack().getUri();
        }

        public static SpotifyTracks notFound() {
            SpotifyTracks track = new SpotifyTracks();
            track.name = "Track not found";
            track.artist = "Artist not found";
            track.link = null;

            return track;
        }

        public String getName() {
            return name;
        }

        public String getArtist() {
            return artist;
        }

        public String getLink() {
            return link;
        }

        @Override
        public String toString() {
            return "SpotifyTracks [beerStyle=" + name + ", artist=" + artist + ", link=" + link + "]";
        }
    }

    class BeerWithSpotify {
        private String beerStyle;
        private List<SpotifyPlaylistWithTracks> playlist;

        public BeerWithSpotify(Beer beer, List<SpotifyPlaylistWithTracks> playlist) {
            this.beerStyle = beer.getName();
            this.playlist = playlist;
        }

        public String getBeerStyle() {
            return beerStyle;
        }

        public List<SpotifyPlaylistWithTracks> getPlaylist() {
            return playlist;
        }

        @Override
        public String toString() {
            return "BeerWithSpotify [playlist=" + playlist + ", beerStyle=" + beerStyle + "]";
        }

    }
}