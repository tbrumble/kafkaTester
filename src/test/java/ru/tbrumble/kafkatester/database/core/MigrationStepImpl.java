package ru.tbrumble.kafkatester.database.core;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MigrationStepImpl implements MigrationStep {

    @Autowired
    MigrationCheckTool migrationCheckTool;

    /**
     * {@inheritDoc}
     */
    public boolean checkMigrationOrder(Map<Integer, String> mapOrdered) {
        migrationCheckTool.cleanOrder();
        for (Map.Entry<Integer, String> migrationOrder : mapOrdered.entrySet()) {
            migrationCheckTool.putOrderToCheck(migrationOrder.getKey(), migrationOrder.getValue());
        }
        return migrationCheckTool.checkOrder();
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkMigrationScripts(List<String> mapScripts) {
        migrationCheckTool.cleanOrder();
        boolean resultValue;
        for (String sql: mapScripts) {
            resultValue = migrationCheckTool.getJdbcTemplate().queryForObject(sql, Boolean.class);
            if (!resultValue) {
                break;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkUndoScripts(Map<String, String> undoScripts, String checkQuery) {
        migrationCheckTool.cleanOrder();
        Boolean resultValue;

        for (Map.Entry<String, String> undoScript : undoScripts.entrySet()) {
            migrationCheckTool.getJdbcTemplate().execute(undoScript.getKey());
            migrationCheckTool.getJdbcTemplate().execute(undoScript.getValue());

            resultValue = migrationCheckTool.getJdbcTemplate().queryForObject(checkQuery, Boolean.class);

            if (!resultValue) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
