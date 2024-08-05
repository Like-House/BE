package backend.like_house.global.error.exception.alarmClient.service;

import backend.like_house.global.error.exception.alarmClient.dto.DiscordMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DiscordClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    @Value("${spring.discord-url}")
    private String WEBHOOK_URL;

    public void sendAlarm(DiscordMessage discordMessage) {
        try {

            String jsonMessage = objectMapper.writeValueAsString(discordMessage);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<String> response = restTemplate.exchange(WEBHOOK_URL, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
                throw new RuntimeException("Failed to send Discord message: " + response.getStatusCode());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send Discord message", e);
        }
    }
}