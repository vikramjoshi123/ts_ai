package com.hdfcbank.touchstone.ollamauser.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OllamaService {

    private final WebClient webClient;

    public OllamaService(@Value("${ollama.api.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // Single request: get full response at once
    public Mono<String> generateOnce(String model, String prompt) {
        JSONObject body = new JSONObject();
        body.put("model", model);
        body.put("prompt", prompt);

        return webClient.post()
                .bodyValue(body.toString())
                .retrieve()
                .bodyToMono(String.class)
                .map(resp -> {
                    try {
                        JSONObject obj = new JSONObject(resp);
                        return obj.optString("response", resp);
                    } catch (Exception e) {
                        return resp;
                    }
                });
    }

    // Streaming request: incremental response as Flux<String>
    public Flux<String> streamResponse(String model, String prompt) {
        JSONObject body = new JSONObject();
        body.put("model", model);
        body.put("prompt", prompt);

        return webClient.post()
                .bodyValue(body.toString())
                .retrieve()
                .bodyToFlux(String.class)
                .map(line -> {
                    try {
                        JSONObject obj = new JSONObject(line);
                        return obj.optString("response", "");
                    } catch (Exception e) {
                        return "";
                    }
                });
    }
}
