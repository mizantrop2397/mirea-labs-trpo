package ru.mirea.labs.trpo.sem2.lab1.model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import ru.mirea.labs.trpo.sem2.lab1.constant.Parameter

@Builder
@ToString
class ParameterInfo {
    Parameter parameterName
    String startDate
    String endDate
    int numRecords
    String minTime
    String maxTime
    double min
    double max
    double avg

    Map asJsonMap() {
        [
                start_date                                   : startDate,
                end_date                                     : endDate,
                num_records                                  : numRecords,
                ('min_' + (parameterName?.name ?: "UNKNOWN")): minTime,
                min_time                                     : maxTime,
                ('max_' + (parameterName?.name ?: "UNKNOWN")): max,
                max_time                                     : maxTime,
                ('avg_' + (parameterName?.name ?: "UNKNOWN")): avg

        ]
    }
}
