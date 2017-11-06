package ru.mirea.labs.trpo.sem2.lab2

import ru.mirea.labs.trpo.sem2.lab2.controller.ElevatorController

import static ru.mirea.labs.trpo.sem2.lab2.io.system.IoSystem.read

class Lab2 {
    static void main(String[] args) {
        new ElevatorController().processMovement read()
    }
}
