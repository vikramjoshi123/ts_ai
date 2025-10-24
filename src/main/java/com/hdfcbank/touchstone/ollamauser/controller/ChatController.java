package com.hdfcbank.touchstone.ollamauser.controller;

import com.hdfcbank.touchstone.ollamauser.entity.User;
import com.hdfcbank.touchstone.ollamauser.service.OllamaService;
import com.hdfcbank.touchstone.ollamauser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final OllamaService ollamaService;
    private final UserService userService;

    @GetMapping(value = "/ask", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String prompt) {

        String lower = prompt.toLowerCase().trim();
        if (lower.startsWith("create user")) {
            String username = extractBetween(prompt, "create user", "email").trim();
            if (username.isEmpty()) username = "unknown user";
            String email = extractAfter(prompt, "email").split("\\s+")[0].trim();

            String rolesPart = containsKeyword(prompt, "roles") ? extractAfter(prompt, "roles") : "";
            String deptsPart = containsKeyword(prompt, "departments") ? extractAfter(prompt, "departments") : "";

            Set<String> roles = splitToSet(rolesPart);
            Set<String> depts = splitToSet(deptsPart);

            User created = userService.createUser(username, email, "changeme", roles, depts);
            String msg = String.format("✅ User '%s' created with roles %s and departments %s\n", created.getUsername(), roles, depts);
            return Flux.just(msg, "[DONE]");
        }

        // Streaming response from Ollama
        return ollamaService.streamResponse(System.getenv().getOrDefault("OLLAMA_MODEL", "gemma3:1b"), prompt)
                .map(s -> "data: " + s + "\n\n")
                .concatWith(Flux.just("data: [DONE]\n\n"));
    }

    private static boolean containsKeyword(String text, String kw) {
        return text.toLowerCase().contains(kw.toLowerCase());
    }

    private static String extractBetween(String text, String start, String end) {
        String low = text;
        int s = low.toLowerCase().indexOf(start.toLowerCase());
        if (s < 0) return "";
        s += start.length();
        int e = low.toLowerCase().indexOf(end.toLowerCase(), s);
        if (e < 0) e = text.length();
        return text.substring(s, e);
    }

    private static String extractAfter(String text, String kw) {
        int i = text.toLowerCase().indexOf(kw.toLowerCase());
        if (i < 0) return "";
        return text.substring(i + kw.length());
    }

    private static Set<String> splitToSet(String s) {
        if (s == null) return Collections.emptySet();
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .collect(Collectors.toSet());
    }
}
