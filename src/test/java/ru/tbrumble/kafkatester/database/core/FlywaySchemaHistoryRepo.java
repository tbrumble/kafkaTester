package ru.tbrumble.kafkatester.database.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlywaySchemaHistoryRepo extends JpaRepository<FlywaySchemaHistory, Integer> {
}
