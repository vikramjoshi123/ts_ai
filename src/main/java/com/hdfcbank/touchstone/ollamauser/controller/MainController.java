package com.hdfcbank.touchstone.ollamauser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home() {
        return "chat"; // this will render templates/chat.html
    }
}
