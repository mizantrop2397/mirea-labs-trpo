package ru.mirea.labs.trpo.sem2.lab2

import ru.mirea.labs.trpo.sem2.lab2.model.InputInfo
import ru.mirea.labs.trpo.sem2.lab2.model.OutputInfo
import spock.lang.Specification

import static ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState.OPEN
import static ru.mirea.labs.trpo.sem2.lab2.io.system.IoSystem.read
import static ru.mirea.labs.trpo.sem2.lab2.io.system.IoSystem.write

class Lab2Spec extends Specification {
    def "should read input info"() {
        expect:
        read() == expectedInfo

        where:
        expectedInfo = [new InputInfo(time: 1, src: 1, dest: 3), new InputInfo(time: 8, src: 3, dest: 9), new InputInfo(time: 11, src: 7, dest: 1)]
    }

    def "should write output info"() {
        setup:
        write([new OutputInfo(time: 1, level: 1, doorsState: OPEN)])
    }
}