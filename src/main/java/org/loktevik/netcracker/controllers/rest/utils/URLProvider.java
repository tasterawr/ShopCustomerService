package org.loktevik.netcracker.controllers.rest.utils;

public class URLProvider {
    private static String orderServiceUrl = "http://localhost:8083";

    private static String offerServiceUrl = "http://localhost:8082";

    public static String getOrderServiceUrl() {
        return orderServiceUrl;
    }

    public static String getOfferServiceUrl() {
        return offerServiceUrl;
    }
}
