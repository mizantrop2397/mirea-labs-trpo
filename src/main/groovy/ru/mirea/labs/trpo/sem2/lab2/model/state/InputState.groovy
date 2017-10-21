package ru.mirea.labs.trpo.sem2.lab2.model.state

import ru.mirea.labs.trpo.sem2.lab2.json.Jsonable

class InputState implements Jsonable {
    int time
    int srcLevel
    int destLevel

    @Override
    String toJson() {
        toJson(this).str
    }
}
