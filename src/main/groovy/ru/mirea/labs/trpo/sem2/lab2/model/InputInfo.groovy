package ru.mirea.labs.trpo.sem2.lab2.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder;

@ToString
@Builder
@EqualsAndHashCode
class InputInfo {
    int time
    int src
    int dest
}
