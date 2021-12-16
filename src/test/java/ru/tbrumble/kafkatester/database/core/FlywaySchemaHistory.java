package ru.tbrumble.kafkatester.database.core;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "flyway_schema_history", schema = "public")
@Getter
@Setter
@Accessors(chain = true)
public class FlywaySchemaHistory {
    public FlywaySchemaHistory() {}

    @Id
    @Column(name = "installed_rank", nullable = false)
    private Integer installedRank;

    @Column(name = "version")
    private String version;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "script", nullable = false)
    private String script;

    @Column(name = "checksum")
    private Integer checksum;

    @Column(name = "installed_by", nullable = false)
    private String installedBy;

    @Column(name = "installed_on", nullable = false)
    private Timestamp installedOn;

    @Column(name = "execution_time", nullable = false)
    private Integer executionTime;

    @Column(name = "success", nullable = false)
    private boolean success;
}
