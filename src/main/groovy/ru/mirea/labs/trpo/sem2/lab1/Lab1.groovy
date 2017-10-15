package ru.mirea.labs.trpo.sem2.lab1

import groovy.json.JsonOutput
import groovy.json.JsonSlurperClassic
import ru.mirea.labs.trpo.sem2.lab1.constant.Parameter
import ru.mirea.labs.trpo.sem2.lab1.model.ParameterInfo

import java.text.SimpleDateFormat

import static java.lang.Double.MAX_VALUE as DOUBLE_MAX
import static java.lang.Double.MIN_VALUE as DOUBLE_MIN
import static java.lang.Long.MAX_VALUE as LONG_MAX
import static java.lang.Long.MIN_VALUE as LONG_MIN
import static ru.mirea.labs.trpo.sem2.lab1.constant.Lab1Constants.JSON_URL
import static ru.mirea.labs.trpo.sem2.lab1.constant.Lab1Constants.PARAMETER_DATE_TIME_FORMAT
import static ru.mirea.labs.trpo.sem2.lab1.constant.Parameter.*

class Lab1 {
    static void main(String[] args) {
        def json = loadJson()
        def table = json.table as Map
        if (!json) return
        def info = loadParameters table, CURRENT_SPEED, TEMPERATURE, SALINITY
        def jsonOutput = new JsonOutput()
        println jsonOutput.prettyPrint(jsonOutput.toJson(formatToOutput(info)))
    }

    private static Map loadJson() {
        new JsonSlurperClassic().parse(new URL(JSON_URL)) as Map
    }

    private static Map<?, ?> formatToOutput(Map<Parameter, ParameterInfo> info) {
        info.collectEntries { [(it.key.name): it.value.asJsonMap()] }
    }

    private static Map<Parameter, ParameterInfo> loadParameters(Map table, Parameter... parameters) {
        def columnKeys = table.columnNames as List<String>
        def rows = table.rows as List<List>
        Map<Parameter, Integer> parameterIndexes = parameters.collectEntries { [(it): columnKeys.indexOf(it.name)] }
        def parameterQcIndexes = getParameterQcIndexes columnKeys, parameters
        def timeCreatedIndex = columnKeys.indexOf TIME_CREATED.name
        def timeIndex = columnKeys.indexOf TIME.name
        def timeModifiedIndex = columnKeys.indexOf TIME_MODIFIED.name
        String.metaClass.toParameterDate = {
            new SimpleDateFormat(PARAMETER_DATE_TIME_FORMAT).parse(delegate as String)
        }
        Map<Parameter, ParameterInfo> parametersInfo = [:]
        rows.eachWithIndex { row, rowIndex ->
            parameters.each { parameter ->
                def parameterQcValue
                def parameterQcIndex = parameterQcIndexes[parameter]
                if (parameterQcIndex == null || (parameterQcValue = row[parameterQcIndex]) != null && parameterQcValue == 0) {
                    getOrCreateParameterInfo(parameter, parametersInfo).with {
                        numRecords++
                        def value = row[parameterIndexes[parameter]] as Double
                        def timeCreated = (row[timeCreatedIndex] as String).toParameterDate()
                        def timeModified = (row[timeModifiedIndex] as String).toParameterDate()
                        if (startDate > timeCreated) {
                            startDate = timeCreated
                        }
                        if (endDate < timeModified) {
                            endDate = timeModified
                        }
                        if (min > value) {
                            minTime = (row[timeIndex] as String).toParameterDate()
                            min = value
                        }
                        if (max < value) {
                            maxTime = (row[timeIndex] as String).toParameterDate()
                            max = value
                        }
                        sum = sum + value
                    }
                }
                def parameterInfo
                if (rows.size() - 1 == rowIndex && (parameterInfo = parametersInfo[parameter]) != null && parameterInfo.numRecords > 0) {
                    parameterInfo.avg = parameterInfo.sum / parameterInfo.numRecords
                }
            }
        }
        parametersInfo
    }

    private static ParameterInfo getOrCreateParameterInfo(Parameter parameter, Map<Parameter, ParameterInfo> parametersInfo) {
        ParameterInfo parameterInfo
        if (!parametersInfo.containsKey(parameter)) {
            parameterInfo = new ParameterInfo(parameterName: parameter, min: DOUBLE_MAX, max: DOUBLE_MIN, startDate: new Date(LONG_MAX), endDate: new Date(LONG_MIN))
            parametersInfo << [(parameter): parameterInfo]
            return parameterInfo
        }
        return parametersInfo[parameter]
    }

    private static Map<Parameter, Integer> getParameterQcIndexes(List<String> columnKeys, Parameter... parameters) {
        def parameterQcIndexes = [:]
        parameters.each { parameter ->
            def parameterQcIndex = columnKeys.findIndexOf { it == "${parameter.name}_qc" }
            if (parameterQcIndex != -1) {
                parameterQcIndexes << [(parameter): parameterQcIndex]
            }
        }
        parameterQcIndexes
    }
}

