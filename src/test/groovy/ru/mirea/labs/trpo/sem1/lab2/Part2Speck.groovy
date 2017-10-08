package ru.mirea.labs.trpo.sem1.lab2

import spock.lang.Specification
import spock.lang.Unroll


class Part2Speck extends Specification {
    @Unroll
    "Find words should find most frequent words in  '#allWords'"() {
        expect:
        new Part2().findMostFrequentWords(allWords, k) == words

        where:
        allWords                         || k || words
        [aaa: 3, bbb: 2, ccc: 1, eee: 1] || 2 || [aaa: 3, bbb: 2]
    }

}