package ru.mirea.labs.trpo.sem1.lab4

class Lab4 {
    final static String FILE_PATH = "lab4/text.txt"

    static void main(String[] args) {
        findPattern readStrings()
    }

    private static List<String> readStrings() {
        println "Имя считавемого файла: $FILE_PATH"
        this.classLoader.getResource(FILE_PATH).readLines()
    }

    static String findPattern(List<String> strings) {
        println "Исходный текст: ${strings}"
        if (strings.empty) return ""
        strings = strings.findAll { !it.empty }
        def pattern = strings[0].toSet()
        if (strings.size() == 1) return pattern
        def result = strings.stream().skip(1).reduce pattern, { accumulator, value -> value.findAll(/[$accumulator]/).join("") }
        println "Повторяемая строка: '${result ?: ""}'"
        result

        //Переписать на множества
    }

    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }

}
