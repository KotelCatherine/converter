package com.globus.labs.converter.mapper;

import com.globus.labs.converter.entity.Rate;
import com.globus.labs.converter.entity.ValuteXml;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RateMapper {

    public Rate mapToRate(ValuteXml valuteXml, BigDecimal valueBD, BigDecimal vunitRateBD) {
        return new Rate()
                .rateId(valuteXml.id())
                .numCode(Integer.parseInt(valuteXml.numCode()))
                .charCode(valuteXml.charCode())
                .nominal(Integer.parseInt(valuteXml.nominal()))
                .name(valuteXml.name())
                .value(valueBD)
                .vunitRate(vunitRateBD);
    }

}
