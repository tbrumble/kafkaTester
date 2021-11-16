package ru.tbrumble.kafkatester.database.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlywaySchemaHistoryDto {
    @Autowired
    FlywaySchemaHistoryRepo repo;

    List<FlywaySchemaHistory> findAll() {
        return repo.findAll();
    }
}
