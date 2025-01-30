package ru.antoncharov.billing.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String oldEmail;
    private String newEmail;
}
