package ru.tbrumble.kafkatester.database.core;

import java.util.List;
import java.util.Map;


/**
 * Abstract class for test migration step
 */
public interface MigrationStep {
    /**
     * Check migrated history in flyaway_schema_history, migration must be success
     * @param mapOrdered map with order and script name
     * @return result
     */
    boolean checkMigrationOrder(Map<Integer, String> mapOrdered);

    /**
     * Check actual database for migrated scripts
     * @param mapScripts sql script to check database after migration
     * @return result
     */
    boolean checkMigrationScripts(List<String> mapScripts);

    /**
     * Check undo scripts
     * @param undoScripts sql scripts to check undo migrations
     * @return result
     */
    boolean checkUndoScripts(Map<String, String> undoScripts, String checkQuery);
}
