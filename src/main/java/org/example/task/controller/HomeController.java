package org.example.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/") // Base API path: /
public class HomeController {

    // This is a placeholder for the home controller.
    // You can add methods to handle requests and return views as needed.
    // For example, you might want to add a method to return the home page view.

    // Example method:
     @GetMapping("/")
     public String home() {
         return "home"; // This would return the home view (home.html or home.jsp)
     }
}
