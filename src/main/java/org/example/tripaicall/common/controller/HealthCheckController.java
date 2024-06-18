package org.example.tripaicall.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/error")
    public ResponseEntity<String> error() {
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
