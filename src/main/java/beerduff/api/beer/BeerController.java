package beerduff.api.beer;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import beerduff.api.beer.spotify.BeerWithSpotify;
import beerduff.api.beer.spotify.SpotifyService;

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
}