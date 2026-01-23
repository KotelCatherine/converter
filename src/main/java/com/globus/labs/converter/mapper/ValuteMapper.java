package com.globus.labs.converter.mapper;

import com.globus.labs.converter.dto.ValuteDto;
import com.globus.labs.converter.entity.Rate;
import com.globus.labs.converter.entity.Valute;
import com.globus.labs.converter.request.ValuteRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ValuteMapper {

    public ValuteDto mapToDto(Valute valute) {
        return new ValuteDto()
                .id(valute.id())
                .rateId(valute.rateId())
                .numCode(valute.numCode())
                .charCode(valute.charCode())
                .nominal(valute.nominal())
                .name(valute.name())
                .value(valute.value())
                .vunitRate(valute.vunitRate());
    }


    public Valute mapToValute(Rate rate) {
        return new Valute()
                .id(UUID.randomUUID())
                .rateId(rate.rateId())
                .numCode(rate.numCode())
                .charCode(rate.charCode())
                .nominal(rate.nominal())
                .name(rate.name())
                .value(rate.value())
                .vunitRate(rate.vunitRate());
    }

    public Valute mapToValute(ValuteRequest valute) {
        return new Valute()
                .id(UUID.randomUUID())
                .rateId(valute.rateId())
                .numCode(valute.numCode())
                .charCode(valute.charCode())
                .nominal(valute.nominal())
                .name(valute.name())
                .value(valute.value())
                .vunitRate(valute.vunitRate());
    }

    public Valute mapToUpadateValute(Valute valute, ValuteRequest val) {

        valute.rateId(val.rateId());
        valute.numCode(val.numCode());
        valute.charCode(val.charCode());
        valute.nominal(val.nominal());
        valute.name(val.name());
        valute.value(val.value());
        valute.vunitRate(val.vunitRate());

        return valute;

    }

    public Valute mapToUpadateValute(Valute valute, Rate currentRate) {

        valute.rateId(currentRate.rateId());
        valute.numCode(currentRate.numCode());
        valute.charCode(currentRate.charCode());
        valute.nominal(currentRate.nominal());
        valute.name(currentRate.name());
        valute.value(currentRate.value());
        valute.vunitRate(currentRate.vunitRate());

        return valute;

    }

}
