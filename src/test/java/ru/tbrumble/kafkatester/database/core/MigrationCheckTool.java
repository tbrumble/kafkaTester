package ru.tbrumble.kafkatester.database.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Base class helper for migrations check
 */
@Component
public class MigrationCheckTool {
    @Autowired
    private MigrationOrderController migrationOrderController;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void putOrderToCheck(Integer key, String value) {
        migrationOrderController.putValues(key, value);
    }

    public boolean checkOrder() {
        return migrationOrderController.checkOrderWithDatabase();
    }

    public void cleanOrder() {
        migrationOrderController.clearOrderedMap();
    }

}
