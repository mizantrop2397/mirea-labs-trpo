package ru.mirea.labs.trpo.sem2.lab2.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder
import ru.mirea.labs.trpo.sem2.lab2.constant.DoorsState;

@ToString
@Builder
@EqualsAndHashCode
class OutputInfo {
    int time
    int level
    DoorsState doorsState
}
