package ru.tbrumble.kafkatester.database.core;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MigrationStepImpl implements MigrationStep {

    public static final int SELECT_COUNT_ZERO_VALUE = 0;
    @Autowired
    MigrationCheckTool migrationCheckTool;


    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
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
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkRepeatableScripts(List<String> scripts) {
        migrationCheckTool.cleanOrder();
        Integer resultCount = null;
        for (String sql: scripts) {
            resultCount = migrationCheckTool.getJdbcTemplate().queryForObject(sql, Integer.class);
            if ((resultCount == null) || (resultCount.intValue() == SELECT_COUNT_ZERO_VALUE)) {
                break;
            }
        }
        return ((resultCount != 0) && (resultCount != null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean queryScript(String sql) {
        Integer resultCount = migrationCheckTool.getJdbcTemplate().queryForObject(sql, Integer.class);
        return ((resultCount != 0) && (resultCount != null));
    }

}
