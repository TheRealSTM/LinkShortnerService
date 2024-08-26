package com.stm.linkshortnerservice.linkprocessing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    @PostMapping
    public void shortenLink(@RequestBody String originalUrl) {
        System.out.println("Shorten link creation request.");
    }

    @GetMapping("/{shortenedUrl}")
    public void getLink(@PathVariable String shortenedUrl) {
        System.out.println("Shorten link retrieval request.");
    }
}
