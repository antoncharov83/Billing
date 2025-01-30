package ru.antoncharov.billing.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String password;
    private List<EmailDto> emails;
    private List<PhoneDto> phones;
}
