package ru.tbrumble.kafkatester.database.tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.tbrumble.kafkatester.database.core.MigrationStepImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SettingMigrationImpl extends MigrationStepImpl {
    private final String checkString = "SELECT EXISTS (" +
            "SELECT FROM information_schema.tables\n" +
            "WHERE  table_schema = 'appdata' AND table_name = 'setting')";

    private final String redoScript = "\n" +
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

    private final String undoScript  = "DROP TABLE APPDATA.Setting";

    @Test
    public void checkSettingOrder() {
        Map<Integer, String> map = new HashMap();
        map.put(1, "V1__initial_script.sql");

        Assert.isTrue(this.checkMigrationOrder(map),"Order for setting migration is ok" );
    }

    @Test
    public void checkSettingMigrationScripts() {
        List<String> list = new ArrayList<>();
        list.add(checkString);
        Assert.isTrue(this.checkMigrationScripts(list), "Table exist, migration is ok");
    }

    @Test
    public void checkUndoSettingScript() {
        Map<String, String> mapScripts = new HashMap<>();
        mapScripts.put(undoScript, redoScript);
        Assert.isTrue(checkUndoScripts(mapScripts, checkString), "Undo/redo scripts passed");
    }


}
