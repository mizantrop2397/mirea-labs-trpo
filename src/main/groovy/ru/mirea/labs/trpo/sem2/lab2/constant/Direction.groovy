package ru.mirea.labs.trpo.sem2.lab2.constant

enum Direction {
    UP,
    DOWN,

    static direction(int src, int dest) {
        dest > src ? DOWN : UP
    }
}
