package ru.mirea.labs.trpo.sem2.lab2.constant

enum Directional {
    UP,
    DOWN,

    static directional(int src, int dest) {
        dest > src ? DOWN : UP
    }
}
