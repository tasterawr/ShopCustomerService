package org.loktevik.netcracker.controllers.rest.dto;

import lombok.Data;

@Data
public class CustomerOrdersDto {
    private OrderDto[] orders;
}
