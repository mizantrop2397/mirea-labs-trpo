package ru.mirea.labs.trpo.lab1

import spock.lang.Specification
import spock.lang.Unroll


class Part1Speck extends Specification {
    @Unroll
    "Move array should move array from #array to #newArray when k=#k"() {
        expect:
        new Part1().moveArray(array, k) == newArray

        where:
        array           || k  || newArray
        [3, 2, 5, 9, 7] || 3  || [5, 9, 7, 3, 2]
        [3, 2, 5, 9, 7] || -3 || [9, 7, 3, 2, 5]
        [3, 2, 9, 7]    || 0  || [3, 2, 9, 7]
    }

}