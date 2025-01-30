package ru.antoncharov.billing.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchUserRequest {
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private Integer page;
    private Integer pageSize;
}
