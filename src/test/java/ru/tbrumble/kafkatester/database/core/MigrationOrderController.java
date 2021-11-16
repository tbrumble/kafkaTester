package ru.tbrumble.kafkatester.database.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MigrationOrderController {
    @Autowired
    FlywaySchemaHistoryDto flywaySchemaHistoryDto;

    private final Map<Integer, String> mapOrdered = new HashMap<>();

    public void putValues(Integer key, String value) {
        if (mapOrdered.containsKey(key)) {
            throw new MigrationExceptions(
                    String.format("MigrationOrderController has entered key already. New key %s value %s, old key %s value %s",
                            key, value, key, mapOrdered.get(key)));
        } else {
            mapOrdered.put(key, value);
        }
    }

    public void clearOrderedMap() {
        mapOrdered.clear();
    }

    public boolean checkOrderWithDatabase() {
        List<FlywaySchemaHistory> flywaySchemaHistories = flywaySchemaHistoryDto.findAll();
        if (((mapOrdered.size() > 0) && (flywaySchemaHistories.size() <= 0)) || ((mapOrdered.size() <= 0) && (flywaySchemaHistories.size() > 0))) {
            throw new MigrationExceptions("Error in order lists size. One of the list is empty");
        }

        boolean resultValue = mapOrdered.size() == flywaySchemaHistories.size();

        if (!resultValue) {
            return false;
        }

        int i = 0;
        for (FlywaySchemaHistory flywaySchemaHistory : flywaySchemaHistories) {
            i++;
            resultValue = mapOrdered.containsKey(flywaySchemaHistory.getInstalledRank());
            resultValue = resultValue && (flywaySchemaHistory.getInstalledRank() == i == flywaySchemaHistory.isSuccess());
            if (!resultValue) {
                break;
            }
        }

        return resultValue;
    }
}
