package ru.mirea.labs.trpo.sem2.lab1.constant

enum Parameter {
    CURRENT_SPEED("current_speed"),
    TEMPERATURE("temperature"),
    SALINITY("salinity")

    String name

    Parameter(String name) {
        this.name = name
    }

    static Parameter parseParameter(String parameter) {
        switch (parameter) {
            case CURRENT_SPEED.name: return CURRENT_SPEED
            case TEMPERATURE.name: return CURRENT_SPEED
            case SALINITY.name: return CURRENT_SPEED
        }
        throw new RuntimeException("Unknown parameter: $parameter")
    }
}