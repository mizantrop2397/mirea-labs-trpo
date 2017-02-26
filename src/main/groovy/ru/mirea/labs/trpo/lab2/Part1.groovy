package ru.mirea.labs.trpo.lab2

class Part1 {
    final static String FILE_PATH = "lab2/text.txt"

    static void main(String[] args) {
        println "Выберите режим ручного ввода (c - консоль, f - файл): "
        def scanner = new Scanner(System.in)
        def mode = scanner.nextLine()
        if (mode == null || (mode != 'c' && mode != 'f'))
            writeWrongInputAndExit()
        switch (mode) {
            case "c":
                findWords readConsoleString()
                break
            case "f":
                findWords readFile()
                break
        }
    }

    private static String readConsoleString() {
        println "Введите строку: "
        def scanner = new Scanner(System.in)
        def string = scanner.nextLine()
        if (string == null || string.empty)
            writeWrongInputAndExit()
        string
    }

    private static String readFile() {
        println "Имя считавемого файла: $FILE_PATH"
        this.classLoader.getResource(FILE_PATH).text
    }

    static Map<String, Integer> findWords(String text) {
        println "Исходный текст: $text"
        def result = text.split("([^a-zA-Z']+)").countBy { it }
        println "Слова: $result"
        result
    }

    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }

}