package beerduff.api.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;

@Configuration
public class SpotifyConfiguration {

    private static final String CLIENT_ID = "38d6c55318274447bd626100f9958e1a";
    private static final String CLIENT_TOKEN = "5432b20647c247669a134f57520244ea";

    @Bean
    @Lazy(true)
    public SpotifyApi spotifyApi() {
        return new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_TOKEN).build();
    }

    @Bean("authenticated")
    @Lazy(true)
    public SpotifyApi credentials() throws SpotifyWebApiException, IOException {
        SpotifyApi spotifyApi = spotifyApi();

        ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
        // System.out.println("Expires in: " + credentials.getExpiresIn());

        spotifyApi.setAccessToken(credentials.getAccessToken());

        return spotifyApi;
    }
}
