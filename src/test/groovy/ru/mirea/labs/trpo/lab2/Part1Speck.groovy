package ru.mirea.labs.trpo.lab2

import spock.lang.Specification
import spock.lang.Unroll


class Part1Speck extends Specification {
    @Unroll
    "Find words should count words in text '#text'"() {
        expect:
        new Part1().findWords(text) == words

        where:
        text                              || words
        "aaa bbb: ccc, aaa eee - bbb aaa" || [aaa: 3, bbb: 2, ccc: 1, eee: 1]
    }

}