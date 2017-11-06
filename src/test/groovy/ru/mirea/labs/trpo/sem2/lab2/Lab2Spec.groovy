package ru.mirea.labs.trpo.sem2.lab2

import ru.mirea.labs.trpo.sem2.lab2.controller.ElevatorController
import ru.mirea.labs.trpo.sem2.lab2.model.InputInfo
import spock.lang.Specification
import spock.lang.Unroll

import static ru.mirea.labs.trpo.sem2.lab2.Lab2.*

class Lab2Spec extends Specification {
    @Unroll
    "should move elevator to #input"() {
        given:
        def controller = new ElevatorController()

        expect:
        controller.processMovement input

        where:
        input <<
                [
                        [new InputInfo(time: 1, src: 1, dest: 1)],
                        [new InputInfo(time: 1, src: 1, dest: 2), new InputInfo(time: 1, src: 1, dest: 3)],
                        [new InputInfo(time: 1, src: 2, dest: 3), new InputInfo(time: 1, src: 2, dest: 1)],
                        [new InputInfo(time: 1, src: 1, dest: 10), new InputInfo(time: 2, src: 5, dest: 10)],
                        [new InputInfo(time: 1, src: 10, dest: 5), new InputInfo(time: 2, src: 4, dest: 1)],
                        [new InputInfo(time: 1, src: 10, dest: 5), new InputInfo(time: 999, src: 4, dest: 1)],
                        [new InputInfo(time: 1, src: 1, dest: 2), new InputInfo(time: 2, src: 3, dest: 4)],
                        [new InputInfo(time: 1, src: 10, dest: 1), new InputInfo(time: 2, src: 5, dest: 2)],
                        [new InputInfo(time: 1, src: 10, dest: 5), new InputInfo(time: 2, src: 7, dest: 1)],
                        [new InputInfo(time: 1, src: 3, dest: 5), new InputInfo(time: 2, src: 4, dest: 6), new InputInfo(time: 6, src: 6, dest: 4), new InputInfo(time: 7, src: 7, dest: 9), new InputInfo(time: 15, src: 9, dest: 1)],
                        [new InputInfo(time: 1, src: 3, dest: 5), new InputInfo(time: 2, src: 4, dest: 6), new InputInfo(time: 6, src: 6, dest: 6)]
                ]
    }

    def "should move elevator to file input"() {
        setup:
        main()
    }
}