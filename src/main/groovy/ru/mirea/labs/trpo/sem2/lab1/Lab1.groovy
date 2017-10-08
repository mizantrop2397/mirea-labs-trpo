package ru.mirea.labs.trpo.sem2.lab1

import groovy.json.JsonOutput
import ru.mirea.labs.trpo.sem2.lab1.constant.Parameter
import ru.mirea.labs.trpo.sem2.lab1.model.ParameterInfo

import static ru.mirea.labs.trpo.sem2.lab1.constant.Parameter.*

class Lab1 {
    private static final String JSON_URL = "http://www.neracoos.org/erddap/tabledap/E05_aanderaa_all.json?station%2Cmooring_site_desc%2Cwater_depth%2Ctime%2Ccurrent_speed%2Ccurrent_speed_qc%2Ccurrent_direction%2Ccurrent_direction_qc%2Ccurrent_u%2Ccurrent_u_qc%2Ccurrent_v%2Ccurrent_v_qc%2Ctemperature%2Ctemperature_qc%2Cconductivity%2Cconductivity_qc%2Csalinity%2Csalinity_qc%2Csigma_t%2Csigma_t_qc%2Ctime_created%2Ctime_modified%2Clongitude%2Clatitude%2Cdepth&time%3E=2015-08-25T15%3A00%3A00Z&time%3C=2016-12-05T14%3A00%3A00Z"

    static void main(String[] args) {
        def info = getParametersInfo(new URL(JSON_URL).text, CURRENT_SPEED, TEMPERATURE, SALINITY)
        def jsonOutput = new JsonOutput()
        println jsonOutput.prettyPrint(jsonOutput.toJson(formatToOutput(info)))
    }

    private static Map<?, ?> formatToOutput(Map<Parameter, ParameterInfo> info) {
        info.collectEntries { [(it.key.name): it.value.asJsonMap()] }
    }

    private static Map<Parameter, ParameterInfo> getParametersInfo(String json, Parameter... parameters) {
        return [(CURRENT_SPEED): new ParameterInfo(parameterName: CURRENT_SPEED), (TEMPERATURE): new ParameterInfo(parameterName: TEMPERATURE)]
    }
}

