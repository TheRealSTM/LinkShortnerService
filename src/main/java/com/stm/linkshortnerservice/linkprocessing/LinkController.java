package com.stm.linkshortnerservice.linkprocessing;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * REST controller for handling link shortening and retrieval requests.
 */
@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    @Autowired
    LinkShortner linkShortner;

    /**
     * Handles the request to shorten a given URL. This method receives a POST request with the original URL in the
     * request body, shortens the URL using the {@link LinkShortner} service, and prints the shortened URL to the
     * console.
     *
     * @param originalUrl original URL to be shortened
     * @throws IllegalArgumentException if the provided URL is null or empty
     */
    @PostMapping
    public void shortenLink(@RequestBody String originalUrl) {
        System.out.println("Shorten link creation request: " + originalUrl);
        String shortenedUrl = linkShortner.shortenLink(originalUrl);
        System.out.println("Shortened URL: " + shortenedUrl);
    }

    /**
     * Handles the request to retrieve the original URL based on the shortened URL. This method receives a GET
     * request with the shortened URL as a path variable, retrieves the full URL from the {@link LinkShortner} service,
     * and prints it to the console.
     *
     * @param shortenedUrl the shortened URL to look up
     * @throws IllegalArgumentException if the provided shortened URL is null or empty
     */
    @GetMapping("/{shortenedUrl}")
    public void getLink(@PathVariable String shortenedUrl) {
        System.out.println("Shorten link retrieval request.");
        String fullUrl = linkShortner.getFullUrl(shortenedUrl);
        System.out.println(fullUrl);
    }
}
