package com.carlosnazario.hackathon.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SimulacaoRequest(
        @NotNull(message = "O valor desejado não pode ser nulo.")
        @Min(value = 200, message = "O valor desejado deve ser maior que 200.")
        BigDecimal valorDesejado,

        @NotNull(message = "O prazo não pode ser nulo.")
        @Min(value = 1, message = "O prazo deve ser no mínimo 1.")
        Integer prazo
) {}