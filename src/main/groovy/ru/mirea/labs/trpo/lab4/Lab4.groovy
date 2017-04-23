package ru.mirea.labs.trpo.lab4

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
        def pattern = strings[0].toSet().join("")
        if (strings.size() == 1) return pattern
        for (def strIndex = 1; strIndex < strings.size(); strIndex++) {
            pattern = strings[strIndex].findAll(/[$pattern]/).join("")
        }
        println "Повторяемая строка: '${pattern ?: ""}'"
        pattern
    }

    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }

}
