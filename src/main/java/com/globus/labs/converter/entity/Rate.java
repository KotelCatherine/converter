package com.globus.labs.converter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class Rate {

    private String rateId;
    private int numCode;
    private String charCode;
    private int nominal;
    private String name;
    private BigDecimal value;
    private BigDecimal vunitRate;

}
