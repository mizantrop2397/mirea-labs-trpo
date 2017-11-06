package ru.mirea.labs.trpo.sem2.lab2.core

import ru.mirea.labs.trpo.sem2.lab2.constant.Direction
import ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState

import static java.util.Comparator.naturalOrder
import static java.util.Comparator.reverseOrder
import static ru.mirea.labs.trpo.sem2.lab2.constant.Direction.DOWN
import static ru.mirea.labs.trpo.sem2.lab2.constant.Direction.UP
import static ru.mirea.labs.trpo.sem2.lab2.constant.Direction.direction
import static ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState.CLOSED
import static ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState.OPEN

class ElevatorCore {
    private ElevatorMovementNotifier elevatorMovementNotifier
    private int current
    private Queue<Integer> destinations
    private Direction currentDirection
    private DoorsState doorsState

    ElevatorCore(ElevatorMovementNotifier elevatorMovementNotifier, int initialLevel) {
        this.elevatorMovementNotifier = elevatorMovementNotifier
        current = initialLevel
        destinations = new PriorityQueue<>()
        closeDoors()
    }

    void addDestination(int to) {
        if (destinations.empty) destinations = new PriorityQueue<>(direction(current, to) == UP ? naturalOrder() : reverseOrder())
        if (doorsState == CLOSED) openDoors()
        if (!destinations.contains(to)) {
            destinations << to
        }
    }

    void startMoving() {
        if (destinations.empty) return
        if (destinations.peek() == current) {
            releasePassengers()
            closeDoors()
            return
        }
        moveToFirstDest()
    }

    void moveTo(int to) {
        to > current ? moveUp(to) : moveDown(to)
    }

    boolean willBeEmptyNow() {
        destinations.size() == 1 && destinations.peek() == current
    }

    Direction currentDirection() {
        currentDirection
    }

    boolean at(int level) {
        current == level
    }

    private void moveToFirstDest() {
        while (!destinations.empty) {
            if (doorsState == OPEN) closeDoors()
            destinations.peek() > current ? moveUp(destinations.peek()) : moveDown(destinations.peek())
            if (doorsState == OPEN && destinations.empty) closeDoors()
        }
    }

    private void releasePassengers() {
        if (doorsState == CLOSED && !destinations.empty && destinations.peek() == current) openDoors()
        destinations.removeAll { it == current }
    }

    private void openDoors() {
        elevatorMovementNotifier.doorsOpen current
        doorsState = OPEN
    }

    private void closeDoors() {
        elevatorMovementNotifier.doorsClosed current
        doorsState = CLOSED
    }


    private void moveDown(int to) {
        currentDirection = DOWN
        while (current != to) {
            if (doorsState == OPEN) closeDoors()
            current--
            elevatorMovementNotifier.levelChanged current + 1, current
            if (!destinations.empty) {
                releasePassengers()
            }
        }
    }

    private void moveUp(int to) {
        currentDirection = UP
        while (current != to) {
            if (doorsState == OPEN) closeDoors()
            current++
            elevatorMovementNotifier.levelChanged current - 1, current
            if (!destinations.empty) {
                releasePassengers()
            }
        }
    }
}
