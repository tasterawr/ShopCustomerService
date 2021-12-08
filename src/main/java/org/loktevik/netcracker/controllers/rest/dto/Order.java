package org.loktevik.netcracker.controllers.rest.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {
    private Long id;
    private Long offerId;
    private String name;
    private Date deliveryTime;
    private String status;
    private Long customerId;
    private boolean paid;
}
