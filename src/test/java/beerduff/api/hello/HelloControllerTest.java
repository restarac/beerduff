package beerduff.api.hello;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.JGivenConfiguration;
import com.tngtech.jgiven.integration.spring.SimpleSpringRuleScenarioTest;

import beerduff.api.JGivenBeerDuffConfiguration;
import beerduff.api.JGivenTestContext;

@SpringBootTest(classes = { MockServletContext.class, JGivenTestContext.class })
@WebAppConfiguration
@JGivenConfiguration(JGivenBeerDuffConfiguration.class)
public class HelloControllerTest extends SimpleSpringRuleScenarioTest<HelloStage> {

    @Test
    public void the_root_path_returns_greetings_from_JGiven() throws Exception {
        when().get("/findBestPlaylistBeer");
        then().the_status_is(HttpStatus.BAD_REQUEST).and().the_content_is("missing param 'temperature'");
    }

    @Test
    @As("The path '/foo' returns NOT FOUND")
    public void the_path_foo_returns_not_found() throws Exception {
        when().get("/foo");
        then().the_status_is(HttpStatus.NOT_FOUND);
    }
}