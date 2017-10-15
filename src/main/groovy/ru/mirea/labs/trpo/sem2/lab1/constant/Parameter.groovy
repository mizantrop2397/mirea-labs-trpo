package ru.mirea.labs.trpo.sem2.lab1.constant

enum Parameter {
    CURRENT_SPEED("current_speed"),
    TEMPERATURE("temperature"),
    SALINITY("salinity"),
    TIME("time"),
    TIME_CREATED("time_created"),
    TIME_MODIFIED("time_modified")

    String name

    Parameter(String name) {
        this.name = name
    }

    static Parameter parseParameter(String parameter) {
        switch (parameter) {
            case CURRENT_SPEED.name: return CURRENT_SPEED
            case TEMPERATURE.name: return TEMPERATURE
            case SALINITY.name: return SALINITY
            case TIME.name: return TIME
            case TIME_CREATED.name: return TIME_CREATED
            case TIME_MODIFIED.name: return TIME_MODIFIED
        }
        throw new RuntimeException("Unknown parameter: $parameter")
    }
}