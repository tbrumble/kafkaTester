package ru.tbrumble.kafkatester.database.tests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.tbrumble.kafkatester.database.core.MigrationCheckTool;

@SpringBootTest
public class MigrationTest {
    @Autowired
    MigrationCheckTool migrationCheckTool;

    @BeforeEach
    void prepareData() {
        migrationCheckTool.cleanOrder();
    }

    @Test
    void checkMigrationOrder() {
        migrationCheckTool.putOrderToCheck(1, "V1__initial_script.sql");
        Assert.isTrue(migrationCheckTool.checkOrder(), "migration Order is ok");
    }
}
