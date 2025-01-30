package ru.antoncharov.billing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private String to;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
}
