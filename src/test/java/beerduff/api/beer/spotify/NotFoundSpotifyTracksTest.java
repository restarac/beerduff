package beerduff.api.beer.spotify;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NotFoundSpotifyTracksTest {

    @Test
    public void name_shows_not_found_when_came_from_not_found() {
        assertThat(SpotifyTracks.notFound().getName(), equalTo("Track not found"));
    }

    @Test
    public void artist_shows_not_found_when_came_from_not_found() {
        assertThat(SpotifyTracks.notFound().getArtist(), equalTo("Artist not found"));
    }

    @Test
    public void link_is_null_when_came_from_not_found() {
        assertThat(SpotifyTracks.notFound().getLink(), is(equalTo(null)));
    }
}
