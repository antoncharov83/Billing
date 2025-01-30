package ru.antoncharov.billing.dto;

import lombok.Data;

@Data
public class PhoneRequest {
    private String oldNumber;
    private String newNumber;
}
