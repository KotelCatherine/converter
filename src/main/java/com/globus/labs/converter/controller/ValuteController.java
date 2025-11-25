package com.globus.labs.converter.controller;

import com.globus.labs.converter.dto.ValuteDto;
import com.globus.labs.converter.request.ValuteRequest;
import com.globus.labs.converter.resource.CentralBankClient;
import com.globus.labs.converter.resource.ValuteResource;
import com.globus.labs.converter.service.ValuteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/valute")
@RequiredArgsConstructor
public class ValuteController implements ValuteResource {

    public final CentralBankClient centralBankClient;
    public final ValuteService service;

    @PostMapping
    @Override
    public ValuteDto create(@RequestBody ValuteRequest val) {
        return service.create(val);
    }

    @GetMapping
    @Override
    public List<ValuteDto> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    @Override
    public ValuteDto update(@PathVariable UUID id, @RequestBody ValuteRequest val) {
        return service.update(id, val);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Scheduled(cron = "00 19 13 * * ?")
    @GetMapping("/getCurrentRate")
    public List<ValuteDto> getCurrentRate(){
        LocalDate date = LocalDate.now();
        centralBankClient.getCurrentRate(date);

        System.out.println("Обновил данные");

        return service.updateValute();

    }

    @Scheduled(cron = "00 19 13 * * ?")
    @GetMapping("/fakeGetCurrentRate")
    public void fakeGetCurrentRate(){

        System.out.println("Сделал вид, что обновил данные");

    }


}
