package beerduff.api.beer.spotify;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;
import com.wrapper.spotify.SpotifyApi;
import beerduff.api.beer.Beer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotifyServiceTest {

    @Autowired
    private SpotifyService subject;

    @Qualifier("authenticated")
    @MockBean(answer = Answers.RETURNS_MOCKS)
    private SpotifyApi spotifyApi;

    @Test
    public void name_shows_not_found_when_came_from_not_found() throws Exception {
        String beerName = new Faker().beer().name();
        Beer beer = new Beer(beerName, -10, 10);
        
//        need to finish
//        when(spotifyApi.searchPlaylists(anyString())).then
//                .limit(anyInt())
//                .offset(anyInt())
//                .build()
//                .execute())
//        .thenThrow(new Exception());
//        
//        assertNotNull(subject.findSpotifyPlaylistWithTracksBy(beer));
    }

}
