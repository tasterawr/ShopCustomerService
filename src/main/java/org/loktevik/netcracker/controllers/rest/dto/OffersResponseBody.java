package org.loktevik.netcracker.controllers.rest.dto;

import lombok.Data;

@Data
public class OffersResponseBody {
    private Offer[] offers;
    private Category[] categories;
}
