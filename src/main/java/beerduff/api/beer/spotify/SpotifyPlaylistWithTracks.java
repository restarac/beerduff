package beerduff.api.beer.spotify;

import java.util.Arrays;
import java.util.List;

import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;

public class SpotifyPlaylistWithTracks {
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