package beerduff.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
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
		List<Beer> beers = repository.findByAverageTemperature(-2);

		Resource<Beer> resources = new Resource<Beer>(beers.get(0));

		return ResponseEntity.ok(resources);
	}

}