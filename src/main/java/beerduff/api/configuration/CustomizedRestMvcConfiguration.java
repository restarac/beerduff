package beerduff.api.configuration;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomizedRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//		there is a bug or a unimplemented feature that doesnt allow disable this ALPS
//		See this PR for more information: https://github.com/spring-projects/spring-data-rest/pull/280/files
//		(Its seens that in the past it was implemented https://github.com/spring-projects/spring-boot/issues/1718 ? :thinking_face:)
		config.getMetadataConfiguration().setAlpsEnabled(false);
		config.setBasePath("/api");
	}
}