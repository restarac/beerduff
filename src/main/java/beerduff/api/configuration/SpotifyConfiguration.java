package beerduff.api.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;

@Component
public class SpotifyConfiguration {

    @Bean
    public SpotifyApi spotifyApi() {
        return new SpotifyApi.Builder().setClientId("client-id")
                .setClientSecret("client-token").build();
    }

    @Bean("authenticated")
    public SpotifyApi credentials() throws SpotifyWebApiException, IOException {
        SpotifyApi spotifyApi = spotifyApi();

        ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
        // System.out.println("Expires in: " + credentials.getExpiresIn());

        spotifyApi.setAccessToken(credentials.getAccessToken());

        return spotifyApi;
    }
}
