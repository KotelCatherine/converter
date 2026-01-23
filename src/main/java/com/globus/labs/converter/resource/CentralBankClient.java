package com.globus.labs.converter.resource;

import com.globus.labs.converter.entity.ValCurs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "central-bank-client", url = "${app.central-bank-client.url}")
public interface CentralBankClient {


    @GetMapping(produces = "application/xml")
    ValCurs getCurrentRate(@RequestParam("date_req") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date);

}
