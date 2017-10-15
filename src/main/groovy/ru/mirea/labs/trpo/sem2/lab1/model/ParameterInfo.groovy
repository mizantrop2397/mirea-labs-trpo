package ru.mirea.labs.trpo.sem2.lab1.model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import ru.mirea.labs.trpo.sem2.lab1.constant.Parameter

import static ru.mirea.labs.trpo.sem2.lab1.constant.Lab1Constants.DATE_FORMAT_YYYY_MM_DD
import static ru.mirea.labs.trpo.sem2.lab1.constant.Lab1Constants.PARAMETER_DATE_TIME_FORMAT

@Builder
@ToString
class ParameterInfo {
    Parameter parameterName
    Date startDate
    Date endDate
    int numRecords
    Date minTime
    Date maxTime
    double min
    double max
    double sum
    double avg

    Map asJsonMap() {
        [
                start_date                                   : startDate.format(DATE_FORMAT_YYYY_MM_DD),
                end_date                                     : endDate.format(DATE_FORMAT_YYYY_MM_DD),
                num_records                                  : numRecords,
                ('min_' + (parameterName?.name ?: "UNKNOWN")): min,
                min_time                                     : minTime.format(PARAMETER_DATE_TIME_FORMAT),
                ('max_' + (parameterName?.name ?: "UNKNOWN")): max,
                max_time                                     : maxTime.format(PARAMETER_DATE_TIME_FORMAT),
                ('avg_' + (parameterName?.name ?: "UNKNOWN")): avg

        ]
    }
}
