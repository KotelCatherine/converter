package com.globus.labs.converter.service;

import com.globus.labs.converter.dto.ValuteDto;
import com.globus.labs.converter.entity.Valute;
import com.globus.labs.converter.repository.ValuteRepository;
import com.globus.labs.converter.request.ValuteRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@Transactional
class ValuteServiceTest extends TestContainerInitialization {

    @Autowired
    private ValuteService valuteService;

    @Autowired
    private ValuteRepository repository;

    @Test
    void create_whenInvalidData_thenThrowException() {

        ValuteRequest request = new ValuteRequest(null, 0, null, 0, null, null, null);

        Assertions.assertThrows(DataIntegrityViolationException.class, () ->
                valuteService.create(request)
        );

    }

    @Test
    void create_whenValuteExistsByRateId_thenThrowException() {

        BigDecimal value = new BigDecimal("50.9903");
        BigDecimal vuniteRate = new BigDecimal("50.9903");
        Valute valute = new Valute(UUID.randomUUID(),"R01010", 36, "AUD", 1, "Австралийский доллар", value, vuniteRate);
        repository.save(valute);

        ValuteRequest request = new ValuteRequest("R01010", 36, "AUD", 1, "Австралийский доллар", value, vuniteRate);

        Assertions.assertThrows(RuntimeException.class, () -> valuteService.create(request));

    }

    @Test
    void create_whenValidData_thenSuccess() {

        BigDecimal value = new BigDecimal("50.9903");
        BigDecimal vuniteRate = new BigDecimal("50.9903");
        Valute valute = new Valute(UUID.randomUUID(), "R01010", 36, "AUD", 1, "Австралийский доллар", value, vuniteRate);
        repository.save(valute);

        ValuteRequest request = new ValuteRequest("123", 113, "AD", 1, "Австралийский доллар", value, vuniteRate);

        ValuteDto valuteDto = Assertions.assertDoesNotThrow(() -> valuteService.create(request));

        Assertions.assertEquals(request.rateId(), valuteDto.rateId());

    }

}
