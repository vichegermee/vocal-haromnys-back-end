package com.vocalharmonys.backend.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Thin wrapper around Resend's REST API — a single POST, so this skips
 * adding the resend-java SDK as a dependency for it. Pure HTTP concern only;
 * failure isolation (catch, log, never rethrow) lives in {@link EmailService}.
 */
@Component
public class ResendClient {

    private static final URI RESEND_EMAILS_ENDPOINT = URI.create("https://api.resend.com/emails");

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String fromAddress;

    public ResendClient(
            ObjectMapper objectMapper,
            @Value("${app.resend.api-key}") String apiKey,
            @Value("${app.resend.from-address}") String fromAddress
    ) {
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.fromAddress = fromAddress;
    }

    private record ResendEmailPayload(String from, List<String> to, String subject, String html) {
    }

    public void send(List<String> to, String subject, String html) throws IOException, InterruptedException {
        String body = objectMapper.writeValueAsString(new ResendEmailPayload(fromAddress, to, subject, html));

        HttpRequest request = HttpRequest.newBuilder(RESEND_EMAILS_ENDPOINT)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new IOException("Resend API returned " + response.statusCode() + ": " + response.body());
        }
    }
}
