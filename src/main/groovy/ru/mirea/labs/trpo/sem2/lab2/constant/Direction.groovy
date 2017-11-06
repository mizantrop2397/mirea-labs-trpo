package ru.mirea.labs.trpo.sem2.lab2.constant

import ru.mirea.labs.trpo.sem2.lab2.model.InputInfo

enum Direction {
    UP,
    DOWN,

    static direction(int src, int dest) {
        dest < src ? DOWN : UP
    }


    static direction(InputInfo inputInfo) {
        inputInfo.dest < inputInfo.src ? DOWN : UP
    }
}
