package beerduff.api.beer;

import static beerduff.api.beer.util.CaseInsensitiveSubstringMatcher.containsIgnoringCase;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import beerduff.api.beer.util.CaseInsensitiveSubstringMatcher;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BeerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void appears_on_the_index_route() throws Exception {
        this.mockMvc.perform(get("/api"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.beers.href", equalTo("http://localhost/api/beers{?page,size,sort}")));
    }

    @Test
    public void shows_message_when_temperature_param_is_missing() throws Exception {
        this.mockMvc.perform(get("/api/beers/search/findBestPlaylistBeer"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("missing param 'temperature'")));
    }

    @Test
    public void shows_message_when_there_is_no_beers() throws Exception {
        this.mockMvc.perform(get("/api/beers/search/findBestPlaylistBeer?temperature=15"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("no beers found!")));
    }

    @Test
    public void shows_beer_with_a_playlist_on_spotify() throws Exception {
        // called BeerSetup.setup()

        this.mockMvc.perform(get("/api/beers/search/findBestPlaylistBeer?temperature=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beerStyle", equalTo("India pale ale")))
                .andExpect(jsonPath("$.playlist", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.playlist[0].name", containsIgnoringCase("India Pale Ale")))
                .andExpect(jsonPath("$.playlist[0].tracks", hasSize(greaterThanOrEqualTo(1))));
    }
}