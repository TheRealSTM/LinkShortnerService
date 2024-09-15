package com.stm.linkshortnerservice.linkprocessing;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;

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
     * Handles the request to shorten a given URL. This method receives a POST request with the original URL
     * in the request body, shortens the URL using the {@link LinkShortner} service, and returns the
     * shortened URL in a structured response.
     *
     * @param originalUrl Original URL to be shortened, provided in the request body
     * @return {@link ResponseEntity} containing the {@link LinkCreationResponse} with the original and shortened URLs,
     *         along with a status of {@code 201 Created}
     * @throws IllegalArgumentException if the provided URL is null or empty
     */
    @PostMapping
    public ResponseEntity<LinkCreationResponse> shortenLink(@RequestBody String originalUrl) {
        System.out.println("Shorten link creation request: " + originalUrl);
        String shortenedUrl = linkShortner.shortenLink(originalUrl);
        System.out.println("Shortened URL: " + shortenedUrl);
        LinkCreationResponse response = new LinkCreationResponse(originalUrl, shortenedUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    public ResponseEntity<Void> getLink(@PathVariable String shortenedUrl) {
        System.out.println("Shorten link retrieval request.");
        String fullUrl = linkShortner.getFullUrl(shortenedUrl);
        fullUrl = fullUrl.trim().replaceAll("^\"|\"$", "");  // Remove leading/trailing quotes
        System.out.println(fullUrl);
        return ResponseEntity.status(HttpStatus.FOUND)  // 302 status for redirect
                .location(URI.create(fullUrl))  // Redirect to the original URL
                .build();
    }
}
