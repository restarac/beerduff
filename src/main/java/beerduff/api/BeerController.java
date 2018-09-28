package beerduff.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class BeerController {

    private final BeerRepository repository;

    @Autowired
    public BeerController(BeerRepository repo) {
	repository = repo;
    }

    @RequestMapping(method = GET, value = "/beers/search/findBestPlaylistBeer")
    public @ResponseBody ResponseEntity<?> findByAverageTemperature(String temperature) {
	if (StringUtils.isEmpty(temperature)) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("missing param 'temperature'");
	}

	List<Beer> beers = repository.findByAverageTemperature(Integer.parseInt(temperature));

	if (beers.isEmpty()) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no beers found!");
	}

	Resource<Beer> resources = new Resource<Beer>(beers.get(0));

	return ResponseEntity.ok(resources);
    }

}