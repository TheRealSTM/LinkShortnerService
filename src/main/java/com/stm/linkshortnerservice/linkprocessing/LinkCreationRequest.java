package com.stm.linkshortnerservice.linkprocessing;

/**
 * Request object containing the original URL to be shortened.
 *
 * @param originalUrl the original URL provided in the request
 */
public record LinkCreationRequest(String originalUrl) {
}
