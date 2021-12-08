package org.loktevik.netcracker.controllers.rest.dto;

import lombok.Data;

@Data
public class Offer {
    private Long id;
    private String name;
    private Double price;
    private int paidTypeId;
    private String category;
    private String[] characteristics;
}
