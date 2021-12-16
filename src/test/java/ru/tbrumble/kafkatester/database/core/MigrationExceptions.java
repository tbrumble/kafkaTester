package ru.tbrumble.kafkatester.database.core;

public class MigrationExceptions extends RuntimeException{

    public MigrationExceptions() {}

    public MigrationExceptions(String message) {
            super(message);
        }

    public MigrationExceptions(String message, Throwable cause) {
            super(message, cause);
        }

    public MigrationExceptions(Throwable cause) {
            super(cause);
        }

    public MigrationExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
}
