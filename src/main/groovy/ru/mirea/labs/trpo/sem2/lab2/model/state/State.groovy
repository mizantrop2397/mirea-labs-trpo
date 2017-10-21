package ru.mirea.labs.trpo.sem2.lab2.model.state

import ru.mirea.labs.trpo.sem2.lab2.constant.DoorState
import ru.mirea.labs.trpo.sem2.lab2.json.Jsonable

class State implements Jsonable {
    int currentLevel
    int destLevel
    DoorState doorState
}
