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
class ProjectMigrationTest extends MigrationStepImpl {
    private final String checkString = "SELECT EXISTS (" +
            "SELECT FROM information_schema.tables\n" +
            "WHERE  table_schema = 'appdata' AND table_name = 'project')";

    private final String redoScript = "\n" +
            "CREATE TABLE APPDATA.PROJECT\n" +
            "(\n" +
            "    ID SERIAL NOT NULL UNIQUE PRIMARY KEY,\n" +
            "    NAME VARCHAR(128) NOT NULL,\n" +
            "    APPVERSION VARCHAR(36),\n" +
            "    DESCRIPTION VARCHAR(512),\n" +
            "    KAFKAVERSION VARCHAR(36) NOT NULL,\n" +
            "    STATUS VARCHAR(36) CHECK (STATUS IN ('active', 'archived')),\n" +
            "    CREATEDTIME TIMESTAMP NOT NULL DEFAULT NOW(),\n" +
            "    MODIFEDTIME TIMESTAMP NOT NULL DEFAULT NOW()\n" +
            ");\n" +
            "\n" +
            "COMMENT ON TABLE APPDATA.PROJECT IS 'Kafka testing project. Contain info about testing project';\n" +
            "COMMENT ON COLUMN APPDATA.PROJECT.NAME IS 'Project name. Can not be null';\n" +
            "COMMENT ON COLUMN APPDATA.PROJECT.APPVERSION IS 'Project app version.';\n" +
            "COMMENT ON COLUMN APPDATA.PROJECT.DESCRIPTION IS 'Project description.';\n" +
            "COMMENT ON COLUMN APPDATA.PROJECT.KAFKAVERSION IS 'Project kafka testing version. Can not be null.';\n" +
            "COMMENT ON COLUMN APPDATA.PROJECT.STATUS IS 'Project status. Can be active or archived.';\n" +
            "COMMENT ON COLUMN APPDATA.PROJECT.CREATEDTIME IS 'Project created time. Default now.';\n" +
            "COMMENT ON COLUMN APPDATA.PROJECT.MODIFEDTIME IS 'Project modified time. Default now. Last project data update.';\n";

    private final String undoScript  = "DROP TABLE APPDATA.project";

    @Test
    void checkOrder() {
        Map<Integer, String> map = new HashMap();
        map.put(1, "V1__initial_script.sql");
        map.put(2, "R__initial_script.sql");

        Assert.isTrue(checkMigrationOrder(map),"Migration is ok" );
    }

    @Test
    void checkMigrationScripts() {
        List<String> list = new ArrayList<>();
        list.add(checkString);
        Assert.isTrue(checkMigrationScripts(list), "Table exist, migration is ok");
    }

    @Test
    void checkUndoScript() {
        Map<String, String> mapScripts = new HashMap<>();
        mapScripts.put(undoScript, redoScript);
        Assert.isTrue(checkUndoScripts(mapScripts, checkString), "Undo/redo scripts passed");
    }
}
