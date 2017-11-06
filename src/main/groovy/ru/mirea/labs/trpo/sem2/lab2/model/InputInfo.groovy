package ru.mirea.labs.trpo.sem2.lab2.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder

import static java.util.Comparator.*;

@ToString(includePackage = false)
@Builder
@EqualsAndHashCode
class InputInfo implements Comparable<InputInfo> {
    int time
    int src
    int dest

    @Override
    int compareTo(InputInfo o) {
        comparingInt { it.time }.compare(this, o)
    }
}
