package com.globus.labs.converter.service;

import com.globus.labs.converter.dto.ValuteDto;
import com.globus.labs.converter.entity.Rate;
import com.globus.labs.converter.entity.Valute;
import com.globus.labs.converter.mapper.ValuteMapper;
import com.globus.labs.converter.repository.ValuteRepository;
import com.globus.labs.converter.request.ValuteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValuteService {

    private final ValuteRepository repository;
    private final ValuteMapper mapper;

    private final CentralBankService centralBankService;
    private final KafkaProducerService kafkaProducerService;

    @Transactional(rollbackFor = Exception.class)
    public ValuteDto create(ValuteRequest val) {

        if (repository.existsByRateId(val.rateId())) {
            throw new RuntimeException("Курс с таким идентификатором [ " + val.rateId() + " ] уже создан");
        }

        Valute valute = mapper.mapToValute(val);

        repository.saveAndFlush(valute);

        String message = String.format("Добавлена новая валюта: %s, %s", valute.charCode(), valute.name());

        kafkaProducerService.sendMessage(message);

        return mapper.mapToDto(valute);

    }

    public List<ValuteDto> findAll() {

        List<Valute> valutes = repository.findAll();

        if (valutes.isEmpty()) {
            throw new RuntimeException("Список валют пуст");
        }

        return valutes.stream()
                .map(mapper::mapToDto)
                .toList();

    }

    @Transactional(rollbackFor = Exception.class)
    public ValuteDto update(UUID id, ValuteRequest val) {

        Valute valute = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Валюта с таким идентификатором [ " + id + " ] не найдена"));

        Valute updatedValute = mapper.mapToUpadateValute(valute, val);

        repository.saveAndFlush(updatedValute);

        String message = String.format("Обновлена валюта: %s", updatedValute.charCode());
        kafkaProducerService.sendMessage(message);

        return mapper.mapToDto(updatedValute);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) {

        if (repository.existsById(id)) {

            String message = String.format("Удалена валюта: %s", repository.findById(id)
                    .get()
                    .charCode());

            repository.deleteById(id);

            kafkaProducerService.sendMessage(message);

        } else {
            throw new RuntimeException("Валюта с таким идентификатором [ " + id + " ] не найдена");
        }

    }


    @Transactional(rollbackFor = Exception.class)
    public List<ValuteDto> updateValute() {

        List<Valute> valuteList = repository.findAll();
        List<Rate> currentRates = centralBankService.getCurrentRate(LocalDate.now());
        List<Valute> updatedValutes = new ArrayList<>();

        for (Rate currentRate : currentRates) {

            Optional<Valute> foundValute = valuteList.stream()
                    .filter(valute -> isEqualsRateId(currentRate.rateId(), valute.rateId()))
                    .findFirst();

            if (foundValute.isPresent()) {

                Valute valute = mapper.mapToUpadateValute(foundValute.get(), currentRate);
                repository.saveAndFlush(valute);
                updatedValutes.add(valute);

            } else {

                Valute newValute = createNewValute(mapper.mapToValute(currentRate));
                repository.saveAndFlush(newValute);
                updatedValutes.add(newValute);

            }

        }

        setMessage(updatedValutes);


        return updatedValutes.stream()
                .map(mapper::mapToDto)
                .toList();

    }

    private void setMessage(List<Valute> updatedValutes) {
        for (Valute updatedVal : updatedValutes) {
            String message = String.format("Обновлена валюта: %s", updatedVal.charCode());
            kafkaProducerService.sendMessage(message);
        }
    }

    private Valute createNewValute(Valute valute) {
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

    private boolean isEqualsRateId(String currentValuteRateId, String valuteRateId) {
        return currentValuteRateId.equals(valuteRateId);
    }

}
