package beerduff.api.hello;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import beerduff.api.beer.Beer;
import beerduff.api.beer.BeerController;
import beerduff.api.beer.BeerRepository;
import beerduff.api.beer.spotify.BeerWithSpotify;
import beerduff.api.beer.spotify.SpotifyService;

@JGivenStage
public class HelloStage extends Stage<HelloStage> {

    @Autowired
    MockMvc mvc;

//    @Autowired
//    BeerController controller;
    
    @MockBean
    private SpotifyService service;
    @MockBean
    private BeerRepository repository;
    @Mock
    private Beer beer;
    @Mock
    private BeerWithSpotify beerWithSpotify;

    private ResultActions mvcResult;

    @BeforeStage
    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    public HelloStage get(@Quoted String path) throws Exception {
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON));
        return this;
    }

    public HelloStage there_is_no_beers() throws Exception {
        Mockito.when(repository.findByAverageTemperature(0)).thenReturn(Collections.emptyList());
        return this;
    }

    public HelloStage there_is_no_playlist() throws Exception {
        Mockito.when(service.findSpotifyPlaylistWithTracksBy(Mockito.any())).thenReturn(null);
        return this;
    }

    public HelloStage the_status_is(HttpStatus status) throws Exception {
        mvcResult.andExpect(status().is(status.value()));
        return this;
    }

    public HelloStage the_content_is(@Quoted String content) throws Exception {
        mvcResult.andExpect(content().string(equalTo(content)));
        return this;
    }
}