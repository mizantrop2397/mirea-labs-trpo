package ru.mirea.labs.trpo.sem2.lab2.core

import ru.mirea.labs.trpo.sem2.lab2.constant.Directional

import static ru.mirea.labs.trpo.sem2.lab2.constant.Directional.DOWN
import static ru.mirea.labs.trpo.sem2.lab2.constant.Directional.UP

class ElevatorCore {
    private ElevatorMovementNotifier elevatorMovementNotifier
    private int current
    private Queue<Integer> destinations
    private Directional currentDirectional

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

    Directional currentDirectional() {
        currentDirectional
    }

    boolean at(int level) {
        current == level
    }

    boolean atDestinations(int level) {
        destinations.contains(level)
    }

    private boolean moveUp(int to) {
        currentDirectional = UP
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
        currentDirectional = DOWN
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
