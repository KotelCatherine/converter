package com.globus.labs.converter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "valute")
@Accessors(fluent = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Valute {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "rate_id")
    private String rateId;

    @Column(name = "num_code")
    private int numCode;

    @Column(name = "char_code")
    private String charCode;

    @Column(name = "nominal")
    private int nominal;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "vunit_rate")
    private BigDecimal vunitRate;

}
