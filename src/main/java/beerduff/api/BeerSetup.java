package beerduff.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class BeerSetup {
	
	@Autowired
	private BeerRepository repository;

	@EventListener(ApplicationReadyEvent.class)
	public void setup() {
		repository.save(new Beer("Weissbier", -1, 3));
		repository.save(new Beer("Pilsens", -2, 4));
		repository.save(new Beer("Weizenbier", -4, 6));
		repository.save(new Beer("Red ale", -5, 5));
		repository.save(new Beer("India pale ale", -6, 7));
		repository.save(new Beer("IPA", -7, 10));
		repository.save(new Beer("Dunkel", -8, 2));
		repository.save(new Beer("Imperial Stouts", -10, 13));
		repository.save(new Beer("Brown ale", -0, 14));
	}
}
