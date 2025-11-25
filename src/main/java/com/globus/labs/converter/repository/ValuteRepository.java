package com.globus.labs.converter.repository;

import com.globus.labs.converter.entity.Valute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValuteRepository extends JpaRepository<Valute, UUID> {
    boolean existsByRateId(String rateId);
}
