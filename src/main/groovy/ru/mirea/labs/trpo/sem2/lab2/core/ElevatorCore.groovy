package ru.mirea.labs.trpo.sem2.lab2.core

import ru.mirea.labs.trpo.sem2.lab2.constant.Direction

import static Direction.DOWN
import static Direction.UP

class ElevatorCore {
    private ElevatorMovementNotifier elevatorMovementNotifier
    private int current
    private Queue<Integer> destinations
    private Direction currentDirection

    ElevatorCore(ElevatorMovementNotifier elevatorMovementNotifier, int initialLevel) {
        this.elevatorMovementNotifier = elevatorMovementNotifier
        current = initialLevel
        destinations = new PriorityQueue<>()
    }

    void openDoors() {
        elevatorMovementNotifier.doorsOpen current
    }

    void closeDoors() {
        elevatorMovementNotifier.doorsClosed current
    }

    void addDestination(int to) {
        destinations << to
    }

    boolean moveTo(int to) {
        if (current == 0 || current == to) {
            return false
        }
        boolean exited
        to > current ? (exited = moveDown(to)) : (exited = moveUp(to))
        exited
    }

    Direction currentDirection() {
        currentDirection
    }

    boolean at(int level) {
        current == level
    }

    boolean atDestinations(int level) {
        destinations.contains(level)
    }

    private boolean moveUp(int to) {
        currentDirection = UP
        def exited = false
        while (current != to) {
            current--
            if (destinations.empty || destinations.peek() != current) {
                elevatorMovementNotifier.levelChanged current + 1, current, false
                exited = false
                continue
            }
            elevatorMovementNotifier.levelChanged current + 1, current, true
            exited = true
            destinations.remove(current)
        }
        exited
    }

    private boolean moveDown(int to) {
        currentDirection = DOWN
        def exited = false
        while (current != to) {
            current++
            if (destinations.empty || destinations.peek() != current) {
                elevatorMovementNotifier.levelChanged current - 1, current, false
                exited = false
                continue
            }
            exited = true
            elevatorMovementNotifier.levelChanged current - 1, current, true
            destinations.remove(current)
        }
        exited
    }
}
