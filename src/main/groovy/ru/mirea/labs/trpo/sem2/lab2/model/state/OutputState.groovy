package ru.mirea.labs.trpo.sem2.lab2.model.state

import ru.mirea.labs.trpo.sem2.lab2.constant.DoorState
import ru.mirea.labs.trpo.sem2.lab2.json.Jsonable

class OutputState implements Jsonable {
    int time
    int level
    DoorState doorState
}
