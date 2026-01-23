package com.globus.labs.converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Данные о валюте")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class ValuteDto {

    @Schema(description = "Идентификатор валюты")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Идентификатор валюты в ЦБ")
    @JsonProperty("rateId")
    private String rateId;

    @Schema(description = "Числовой код валюты")
    @JsonProperty("numCode")
    private int numCode;

    @Schema(description = "Символьный код валюты")
    @JsonProperty("charCode")
    private String charCode;

    @Schema(description = "Номинал")
    @JsonProperty("nominal")
    private int nominal;

    @Schema(description = "Название валюты")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Значение курса")
    @JsonProperty("value")
    private BigDecimal value;

    @Schema(description = "Курс за единицу")
    @JsonProperty("vunitRate")
    private BigDecimal vunitRate;

}
