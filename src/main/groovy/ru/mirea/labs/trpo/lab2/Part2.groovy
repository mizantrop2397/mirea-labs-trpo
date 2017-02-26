package ru.mirea.labs.trpo.lab2

class Part2 {
    final static String FILE_PATH = "lab2/text.txt"

    static void main(String[] args) {
        println "Выберите режим ручного ввода (c - консоль, f - файл): "
        def scanner = new Scanner(System.in)
        def mode = scanner.nextLine()
        if (mode == null || (mode != 'c' && mode != 'f'))
            writeWrongInputAndExit()
        switch (mode) {
            case "c":
                findMostFrequentWords findWords(readConsoleString()), readK()
                break
            case "f":
                findMostFrequentWords findWords(readFile()), readK()
                break
        }
    }

    static int readK() {
        println "Введите k: "
        def scanner = new Scanner(System.in)
        def k = ++scanner
        if (k == null || k.empty)
            writeWrongInputAndExit()
        convertStringToIntChecked k
    }

    private static String readConsoleString() {
        println "Введите строку: "
        def scanner = new Scanner(System.in)
        def string = scanner.nextLine()
        if (string == null || string.empty)
            writeWrongInputAndExit()
        string
    }

    private static Integer convertStringToIntChecked(String string) {
        def integer
        try {
            integer = Integer.valueOf string
            integer
        } catch (Exception ignored) {
            println "Неверный ввод объекта: $string"
            System.exit(-1)
        }
    }

    private static String readFile() {
        println "Имя считавемого файла: $FILE_PATH"
        this.classLoader.getResource(FILE_PATH).text
    }

    private static Map<String, Integer> findWords(String text) {
        println "Исходный текст: $text"
        def result = text.split("([^a-zA-Z']+)").countBy { it }
        println "Слова: $result"
        result
    }

    static Map<String, Integer> findMostFrequentWords(Map<String, Integer> allWords, int k) {
        if (k > allWords.size())
            writeWrongInputAndExit()
        def words = (allWords as MapWithCustomRaddixSort).sortFirst k
        println "Наиболее частые слова: $words"
        words
    }

    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }
}

trait MapWithCustomRaddixSort {
    def sortFirst(int firstElementsCount) {
        def resultMap = []
        def entrySet = entrySet()
        int maxValue = entrySet[0].value
        int minValue = entrySet[0].value

        for (int i = 1; i < entrySet.size(); i++) {
            if (entrySet[i].value > maxValue)
                maxValue = entrySet[i].value

            if (entrySet[i].value < minValue)
                minValue = entrySet[i].value
        }

        def bucket = []

        for (int i = 0; i < maxValue - minValue + 1; i++) {
            bucket[i] = []
        }

        for (int i = 0; i < entrySet.size(); i++) {
            bucket[entrySet[i].value - minValue] << entrySet[i]
        }

        int position = 0
        for (int i = bucket.size() - 1; i >= 0; i--) {
            if (position == firstElementsCount)
                break
            if (bucket[i].size() > 0) {
                for (int j = 0; j < bucket[i].size(); j++) {
                    if (position == firstElementsCount)
                        break
                    resultMap[position] = bucket[i][j]
                    position++
                }
            }
        }
        resultMap.collectEntries { [(it.key): it.value] }
    }
}