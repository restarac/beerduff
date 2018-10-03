package beerduff.api.beer.spotify;

import com.wrapper.spotify.model_objects.specification.PlaylistTrack;

public class SpotifyTracks {
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