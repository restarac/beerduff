package beerduff.api.beer.spotify;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;

@RunWith(SpringRunner.class)
public class SpotifyTracksTest {
    
    private String artistName;
    private String trackerName;
    private String linkUri;

    @Before
    public void setup() {
        Faker faker = new Faker();
        artistName = faker.name().fullName();
        trackerName = faker.funnyName().name();
        linkUri = "www.example.com.br";
    }
    
    @Test
    public void name_shows_tracks_name_when_came_from_not_found() {
        PlaylistTrack playlistTrack = buildPlaylistTrack(artistName, trackerName, linkUri);

        SpotifyTracks subject = new SpotifyTracks(playlistTrack);

        assertThat(subject.getName(), is(equalTo(trackerName)));
    }

    @Test
    public void artist_shows_artist_name_when_came_from_not_found() {
        PlaylistTrack playlistTrack = buildPlaylistTrack(artistName, trackerName, linkUri);

        SpotifyTracks subject = new SpotifyTracks(playlistTrack);

        assertThat(subject.getArtist(), is(equalTo(artistName)));
    }

    @Test
    public void link_shows_link_uri_when_came_from_not_found() {
        PlaylistTrack playlistTrack = buildPlaylistTrack(artistName, trackerName, linkUri);

        SpotifyTracks subject = new SpotifyTracks(playlistTrack);

        assertThat(subject.getLink(), is(equalTo(linkUri)));
    }

    private PlaylistTrack buildPlaylistTrack(String artistName, String trackerName, String linkUri) {
        com.wrapper.spotify.model_objects.specification.PlaylistTrack.Builder builderPlaylistTrack = new PlaylistTrack.Builder();
        com.wrapper.spotify.model_objects.specification.Track.Builder builderTrack = new Track.Builder();
        com.wrapper.spotify.model_objects.specification.ArtistSimplified.Builder builderArtists = new ArtistSimplified.Builder();

        builderArtists.setName(artistName);
        builderTrack.setArtists(builderArtists.build());
        builderTrack.setName(trackerName);
        builderTrack.setUri(linkUri);
        builderPlaylistTrack.setTrack(builderTrack.build());

        return builderPlaylistTrack.build();
    }
}
