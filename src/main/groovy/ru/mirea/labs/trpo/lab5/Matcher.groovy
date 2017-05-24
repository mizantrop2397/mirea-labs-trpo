package ru.mirea.labs.trpo.lab5

class Matcher {
    private static DATE_REGEX = /[0-9]{4}\/(0[1-9]|1[012])\/(0[1-9]|1[0-9]|2[0-9]|3[01])/
    private static ANOTHER_DATE_REGEX = /(0[1-9]|1[0-9]|2[0-9]|3[01])\/(0[1-9]|1[012])\/[0-9]{4}/
    private static TIME_REGEX = /(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2}/
    private static EMAIL_REGEX = /^[-\w.]+""([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}\\u0024/
    private static DOMEN_REGEX = /([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,6}/

    private static String DATE = " date"
    private static String TIME = " time"
    private static String EMAIL = " email"
    private static String DOMEN = " domen"
    private static String UNKNOWN = "???"

    static String match(String s) {
        def matched = false
        def outStr = "String is: "
        if (s =~ DATE_REGEX || s =~ ANOTHER_DATE_REGEX) {
            outStr += DATE
            matched = true
        }
        if (s =~ TIME_REGEX) {
            outStr += TIME
            matched = true
        }
        if (s =~ EMAIL_REGEX) {
            outStr += EMAIL
            matched = true
        }
        if (s =~ DOMEN_REGEX) {
            outStr += DOMEN
            matched = true
        }
        if (!matched) return outStr + UNKNOWN
        outStr
    }
}