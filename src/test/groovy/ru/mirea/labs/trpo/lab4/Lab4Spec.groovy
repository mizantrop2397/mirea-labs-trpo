package ru.mirea.labs.trpo.lab4

import spock.lang.Specification

class Lab4Spec extends Specification {
    def "Should find pattern: '#pattern' in text: '#text'"() {
        expect:
        Lab4.findPattern(text) == pattern
        where:
        text                                          || pattern
        ["AACABDDEF", "", "B=33{NCA", "", "IOACDBDE"] || "ACB"
    }
}
