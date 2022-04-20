package ru.tbrumble.kafkatester.database.tests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.tbrumble.kafkatester.database.core.MigrationStepImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
class SettingMigrationTest extends MigrationStepImpl {
    private final String CHECK_STRING = "SELECT EXISTS (" +
            "SELECT FROM information_schema.tables\n" +
            "WHERE  table_schema = 'appdata' AND table_name = 'setting')";

    private final String REDO_SCRIPT = "\n" +
            "CREATE TABLE APPDATA.Setting\n" +
            "(\n" +
            "    ID SERIAL NOT NULL UNIQUE PRIMARY KEY,\n" +
            "    NAME VARCHAR(32) NOT NULL,\n" +
            "    VALUE VARCHAR(512)\n" +
            ");\n" +
            "\n" +
            "COMMENT ON TABLE APPDATA.Setting IS 'Application settings. Key-value map structure.';\n" +
            "COMMENT ON COLUMN APPDATA.Setting.NAME IS 'Setting name.';\n" +
            "COMMENT ON COLUMN APPDATA.Setting.VALUE IS 'Setting value. Can be null, empty.';\n";

    private final String UNDO_SCRIPT = "DROP TABLE APPDATA.SETTING";

    private final String CHECK_SETTING_PARAM_1 = "SELECT count(*) from APPDATA.SETTING WHERE NAME = 'version' AND VALUE = '00.001';";
    private final String CHECK_SETTING_PARAM_2 = "SELECT count(*) from APPDATA.SETTING WHERE NAME = 'version_name' AND VALUE = 'Gundabad bread';";
    private final String ADD_REPEATABLE_DATA = "INSERT INTO APPDATA.SETTING(name, value) VALUES\n" +
            "    ('version', '00.001'),\n" +
            "    ('version_name', 'Gundabad bread') returning id ;";

    @Test
    void testOrder() {
        Map<Integer, String> map = new HashMap();
        map.put(1, "V1__initial_script.sql");
        map.put(2, "R__initial_script.sql");

        Assert.isTrue(checkMigrationOrder(map),"Migration order is ok" );
    }

    @Test
    void testMigrationScripts() {
        List<String> list = new ArrayList<>();
        list.add(CHECK_STRING);
        Assert.isTrue(checkMigrationScripts(list), "Table exist, migration is ok");
    }

    @Test
    void testUndoRedoScript() {
        Map<String, String> mapScripts = new HashMap<>();
        mapScripts.put(UNDO_SCRIPT, REDO_SCRIPT);
        Assert.isTrue(checkUndoScripts(mapScripts, CHECK_STRING), "Undo/redo scripts passed");
        Assert.isTrue(queryScript(ADD_REPEATABLE_DATA), "Repeatable data is ok");
    }

    @Test
    void testRepeatableMigration() {
        List<String> checkRepeatableQueries = new ArrayList<>();
        checkRepeatableQueries.add(CHECK_SETTING_PARAM_1);
        checkRepeatableQueries.add(CHECK_SETTING_PARAM_2);
        Assert.isTrue(checkRepeatableScripts(checkRepeatableQueries), "Repeatable data is ok");
    }
}
