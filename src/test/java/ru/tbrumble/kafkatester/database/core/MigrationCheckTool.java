package ru.tbrumble.kafkatester.database.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MigrationCheckTool {
    @Autowired
    private MigrationOrderController migrationOrderController;

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
