package ru.mirea.labs.trpo.sem1.lab1

import java.util.stream.Collectors

class Part2 {
    final static Integer MAX_ARRAY_SIZE = 1_000_000
    final static Long MAX_ARRAY_ELEMENT = 1_000_000_000

    static void main(String[] args) {
        println "Выберите метод задания массива (m - manual, r - random): "
        def scanner = new Scanner(System.in)
        def mode = scanner.nextLine()
        if (mode == null || (mode != 'm' && mode != 'r'))
            writeWrongInputAndExit()
        switch (mode) {
            case "r": findAloneElement randomInput()
                break
            case "m": findAloneElement manualInput()
        }
    }

    private static manualInput() {
        println "Введите массив (разделитель - ','): "
        def scanner = new Scanner(System.in)
        def arrayStr = scanner.nextLine()
        if (arrayStr == null || arrayStr.empty)
            println "Неверный ввод"
        def arrayOfStrings = arrayStr.replace(' ', '').split(',') as List
        if (arrayOfStrings == null || arrayOfStrings.empty)
            writeWrongInputAndExit()
        def array = arrayOfStrings.stream().map(this.&convertStringToLongChecked).peek(this.&checkArrayElement).collect Collectors.toList()
        if (array == null || array.empty)
            writeWrongInputAndExit()
        array.toArray() as Long[]
    }

    private static Long[] randomInput() {
        def halfOfArray = new Random().longs((MAX_ARRAY_SIZE / 2) - 1 as Integer, 14, MAX_ARRAY_ELEMENT).toArray() as List
        (halfOfArray + halfOfArray) << 13L
    }

    public static Long findAloneElement(Long[] array) {
        checkArraySize array
        println "Исходный массив: $array"
        def k = Arrays.stream(array).reduce { a, b -> a ^ b }.orElseThrow { new RuntimeException("Ошибка вычисления") }
        println "Одинокий элемент: $k"
        k
    }

    private static Long convertStringToLongChecked(String string) {
        def element
        try {
            element = Long.valueOf string
            element
        } catch (Exception ignored) {
            println "Неверный ввод объекта: $string"
            System.exit(-1)
        }
    }

    private static checkArrayElement(Long element) {
        if (element > MAX_ARRAY_ELEMENT || element < 1) {
            println "Элемент массива вне диапазона: $element"
            System.exit(-1)
        }
    }

    private static checkArraySize(Long[] array) {
        def size = array.length
        if (size > MAX_ARRAY_SIZE || array.length == 0 || size % 2 == 0) {
            println "Некорректная длина массива: $size"
            System.exit(-1)
        }
    }

    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }

}