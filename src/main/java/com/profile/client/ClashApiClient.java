package com.profile.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.App;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.data.Profile;
import com.profile.utils.exceptions.ProfileFetchException;

import io.github.cdimascio.dotenv.Dotenv;

public class ClashApiClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Dotenv dotenv = Dotenv.load();

    private static final Logger logger = LoggerFactory.getLogger(ClashApiClient.class);

    public static Profile fetchProfile(String tag) throws Exception {
        logger.info(String.format("Fetching Clash API data for #%s", tag));

        HttpClient client = HttpClient.newHttpClient();

        String apiUrl = "https://api.clashofclans.com/v1/players/%23" + tag;        

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + getClashApiToken())
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), Profile.class);
        } else {
            int status = response.statusCode();
            logger.error(String.format("Error %d when fetching Clash API for tag #%s", status, tag));

            throw new ProfileFetchException("Failed to fetch profile: " + ClashClientErrorCodes.getProfileErrorMessage(status), status);
        }
    }

    public static String getClashApiToken() {
        return dotenv.get("CLASH_API_TOKEN");
    }
}