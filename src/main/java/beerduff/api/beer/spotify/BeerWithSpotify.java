package beerduff.api.beer.spotify;

import java.util.List;

import beerduff.api.beer.Beer;

public class BeerWithSpotify {
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