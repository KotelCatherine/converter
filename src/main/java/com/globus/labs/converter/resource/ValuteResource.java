package com.globus.labs.converter.resource;

import com.globus.labs.converter.dto.ValuteDto;
import com.globus.labs.converter.request.ValuteRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Valutes", description = "Валюты")
public interface ValuteResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ValuteDto create(@RequestBody ValuteRequest val);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ValuteDto> findAll();

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ValuteDto update(@PathVariable UUID id, @RequestBody ValuteRequest val);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

}
