package ru.geekbrains.hitech.webstore.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    // context-path: http://localhost:8189/webstore

    // GET http://localhost:8189/webstore/index
    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/control_panel")
    public String showControlPanel() {
        return "control_panel";
    }

    @GetMapping("/exception")
    public String showExceptionPage() {
        return "exception";
    }
}
