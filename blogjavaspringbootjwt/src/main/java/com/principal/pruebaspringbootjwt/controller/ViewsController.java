package com.principal.pruebaspringbootjwt.controller;

import com.principal.pruebaspringbootjwt.scopes.ViewsCounter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/views")
public class ViewsController {

    private final ViewsCounter viewsCounter;

    public ViewsController(ViewsCounter viewsCounter) {
        this.viewsCounter = viewsCounter;
    }

    @PutMapping("/increment")
    public ResponseEntity<Long> incrementCount() {
        long count = viewsCounter.increment();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getCount() {
        long count = viewsCounter.getCount();
        return ResponseEntity.ok(count);
    }
}
