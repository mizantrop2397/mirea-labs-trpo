package ru.mirea.labs.trpo.sem2.lab2.core

interface ElevatorMovementNotifier {
    void doorsOpen(int level)
    void doorsClosed(int level)
    void levelChanged(int from, int to)
}
