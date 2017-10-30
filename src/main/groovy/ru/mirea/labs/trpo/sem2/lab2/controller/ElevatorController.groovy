package ru.mirea.labs.trpo.sem2.lab2.controller

import ru.mirea.labs.trpo.sem2.lab2.core.ElevatorCore
import ru.mirea.labs.trpo.sem2.lab2.core.ElevatorMovementNotifier
import ru.mirea.labs.trpo.sem2.lab2.model.InputInfo
import ru.mirea.labs.trpo.sem2.lab2.model.OutputInfo

import static ru.mirea.labs.trpo.sem2.lab2.constant.Direction.direction
import static ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState.CLOSED
import static ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState.OPEN
import static ru.mirea.labs.trpo.sem2.lab2.io.system.IoSystem.read
import static ru.mirea.labs.trpo.sem2.lab2.io.system.IoSystem.write

class ElevatorController {
    private ElevatorCore elevatorCore

    void processMovement() {
        List<InputInfo> input = read()
        if (input == null || input.empty) return
        def output = []
        initializeElevatorCore new PriorityQueue<InputInfo>(input), output
        write output
    }

    private void initializeElevatorCore(Queue<InputInfo> input, List<OutputInfo> output) {
        int timeCounter = input.peek().time
        ElevatorMovementNotifier elevatorMovementNotifier = new ElevatorMovementNotifier() {
            @Override
            void doorsOpen(int level) {
                println "Doors open on level: $level. timeCounter: $timeCounter"
                output << new OutputInfo(time: timeCounter++, level: level, doorsState: OPEN)
            }

            @Override
            void doorsClosed(int level) {
                println "Doors closed on level: $level, timeCounter: $timeCounter"
                output << new OutputInfo(time: timeCounter++, level: level, doorsState: CLOSED)
            }

            @Override
            void levelChanged(int from, int to, boolean exited) {
                println "level changed from: $from, to: $to doorState: $CLOSED, timeCounter: $timeCounter"
                output << new OutputInfo(time: timeCounter++, level: to, doorsState: CLOSED)
                if (exited | addAssociatedPassengers(timeCounter, input)) {
                    elevatorCore.openDoors()
                    elevatorCore.closeDoors()
                }
            }
        }
        def inputInfo = input.peek()
        elevatorCore = new ElevatorCore(elevatorMovementNotifier, inputInfo.src)
        elevatorCore.closeDoors()
        processElevatorMovement input
    }

    private void processElevatorMovement(Queue<InputInfo> input) {
        def exited = false
        while (!input.empty) {
            def inputInfo = input.poll()
            if (inputInfo.src == inputInfo.dest) continue
            if (!elevatorCore.at(inputInfo.src)) {
                exited = elevatorCore.moveTo inputInfo.src
            }
            if (!exited) {
                elevatorCore.openDoors()
                elevatorCore.closeDoors()
            }
            exited |= elevatorCore.moveTo inputInfo.dest
            if (input.empty || !elevatorCore.at(input.peek().src)) {
                elevatorCore.openDoors()
                elevatorCore.closeDoors()
            }
        }
    }

    private boolean addAssociatedPassengers(int timeCounter, Queue<InputInfo> input) {
        new LinkedList<InputInfo>(input).stream().filter { elevatorCore.at it.src }
                .filter { it.src != it.dest }
                .filter { it.time <= timeCounter }
                .filter { elevatorCore.currentDirection() == direction(it.src, it.dest) }
                .filter { !elevatorCore.atDestinations(it.dest) }
                .peek { elevatorCore.addDestination it.dest; input.remove it }
                .count()
    }
}