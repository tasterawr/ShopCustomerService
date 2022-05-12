package org.loktevik.netcracker.controllers.rest.dto;

import lombok.Data;

@Data
public class OffersResponseBody {
    private OfferDto[] offers;
    private Category[] categories;
}
