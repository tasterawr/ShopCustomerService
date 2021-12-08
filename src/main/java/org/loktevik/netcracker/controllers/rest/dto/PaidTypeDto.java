package org.loktevik.netcracker.controllers.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class PaidTypeDto {
    private Long id;
    private String name;
}
