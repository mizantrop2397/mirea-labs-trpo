package ru.mirea.labs.trpo.sem2.lab2.controller

import ru.mirea.labs.trpo.sem2.lab2.core.ElevatorCore
import ru.mirea.labs.trpo.sem2.lab2.core.ElevatorMovementNotifier
import ru.mirea.labs.trpo.sem2.lab2.model.InputInfo
import ru.mirea.labs.trpo.sem2.lab2.model.OutputInfo

import static java.util.Comparator.naturalOrder
import static java.util.Comparator.reverseOrder
import static ru.mirea.labs.trpo.sem2.lab2.constant.Direction.UP
import static ru.mirea.labs.trpo.sem2.lab2.constant.Direction.direction
import static ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState.CLOSED
import static ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState.OPEN
import static ru.mirea.labs.trpo.sem2.lab2.io.system.IoSystem.write

class ElevatorController {
    private ElevatorCore elevatorCore
    private int timeCounter

    void processMovement(List<InputInfo> input) {
        if (input == null || input.empty) return
        def output = []
        initializeElevatorCore new PriorityQueue<InputInfo>(input), output
        write output
    }

    private void initializeElevatorCore(Queue<InputInfo> input, List<OutputInfo> output) {
        timeCounter = input.peek().time
        ElevatorMovementNotifier elevatorMovementNotifier = new ElevatorMovementNotifier() {
            @Override
            void doorsOpen(int level) {
                println "Doors open on level: $level. timeCounter: ${timeCounter}"
                output << new OutputInfo(time: timeCounter++, level: level, doorsState: OPEN)
            }

            @Override
            void doorsClosed(int level) {
                println "Doors closed on level: $level, timeCounter: ${timeCounter}"
                output << new OutputInfo(time: timeCounter++, level: level, doorsState: CLOSED)
            }

            @Override
            void levelChanged(int from, int to) {
                println "level changed from: $from, to: $to doorState: $CLOSED, timeCounter: ${timeCounter}"
                output << new OutputInfo(time: timeCounter++, level: to, doorsState: CLOSED)
                addAssociatedPassengers input
            }
        }
        elevatorCore = new ElevatorCore(elevatorMovementNotifier, input.peek().src)
        processElevatorMovement input
    }

    private void processElevatorMovement(Queue<InputInfo> input) {
        while (!input.empty) {
            def inputInfo = input.poll()
            while (inputInfo.time > timeCounter) {
                println "elevator waiting. timeCounter: $timeCounter"
                timeCounter++
            }
            if (!elevatorCore.at(inputInfo.src)) {
                elevatorCore.moveTo inputInfo.src
            }
            def destinationsFromCommonSrc = peekDestinationsFromCommonSrc input, inputInfo
            if (!destinationsFromCommonSrc) {
                elevatorCore.addDestination inputInfo.dest
                elevatorCore.startMoving()
                continue
            }
            while (!destinationsFromCommonSrc.empty) {
                elevatorCore.addDestination destinationsFromCommonSrc.poll()
            }
            elevatorCore.startMoving()
        }
    }

    private void addAssociatedPassengers(Queue<InputInfo> input) {
        new LinkedList<InputInfo>(input).stream()
                .filter { elevatorCore.at it.src }
                .filter { it.src != it.dest }
                .filter { it.time <= timeCounter }
                .filter { elevatorCore.willBeEmptyNow() || elevatorCore.currentDirection() == direction(it.src, it.dest) }
                .forEach { elevatorCore.addDestination it.dest; input.remove it }
    }

    private static Queue<Integer> peekDestinationsFromCommonSrc(Queue<InputInfo> input, InputInfo srcInfo) {
        def destinationsFromCommonSrc = null
        while (!input.empty && input.peek().src == srcInfo.src && input.peek().time == srcInfo.time) {
            if (!destinationsFromCommonSrc) {
                destinationsFromCommonSrc = new PriorityQueue<>(direction(srcInfo) == UP ? naturalOrder() : reverseOrder())
                destinationsFromCommonSrc << srcInfo.dest
            }
            destinationsFromCommonSrc << input.poll().dest
        }
        destinationsFromCommonSrc
    }
}