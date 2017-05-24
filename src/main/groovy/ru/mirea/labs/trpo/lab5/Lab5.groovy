package ru.mirea.labs.trpo.lab5

import static ru.mirea.labs.trpo.lab5.Matcher.match

class Lab5 {
    private static TEST_STRINGS =
            [

                    "01/01/2017",
                    "05:01:00",
                    "31/12/2017 12:00:00",
                    "test.com",
                    "test.ru.com",
                    "just String"
            ]

    static void main(String[] args) {
        TEST_STRINGS.each {
            println it + " " + match(it)
        }
    }
}


