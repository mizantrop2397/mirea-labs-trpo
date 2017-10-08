package ru.mirea.labs.trpo.sem1.lab1

import spock.lang.Specification
import spock.lang.Unroll


class Part2Speck extends Specification {
    @Unroll
    "Find alone element in array should find element in array=#array without pair"() {
        expect:
        new Part2().findAloneElement(*array) == k

        where:
        array                                        || k
        [3L, 3L, 5L, 6L, 7L, 7L, 5L, 8L, 7L, 6L, 8L] || 7L

    }

}