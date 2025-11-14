package com.globus.labs.converter.service;

import com.globus.labs.converter.entity.Rate;
import com.globus.labs.converter.entity.ValCurs;
import com.globus.labs.converter.entity.ValuteXml;
import com.globus.labs.converter.mapper.RateMapper;
import com.globus.labs.converter.resource.CentralBankClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CentralBankService {

    private final CentralBankClient centralBankClient;
    private final RateMapper rateMapper;

    public List<Rate> getCurrentRate(LocalDate date) {

        ValCurs valCurs = centralBankClient.getCurrentRate(date);

        return valCurs.getValute().stream()
                .map(this::mapToRate)
                .toList();

    }

    private Rate mapToRate(ValuteXml valuteXml) {

        BigDecimal valueBD = new BigDecimal(valuteXml.value().trim().replace(",", "."));
        BigDecimal vunitRateBD = new BigDecimal(valuteXml.vunitRate().trim().replace(",", "."));

        return rateMapper.mapToRate(valuteXml, valueBD, vunitRateBD);

    }


}
