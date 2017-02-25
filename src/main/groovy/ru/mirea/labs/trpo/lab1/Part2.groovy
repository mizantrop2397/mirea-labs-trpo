package ru.mirea.labs.trpo.lab1

import java.util.stream.Collectors

class Part2 {
    final static Long MAX_ARRAY_SIZE = 1_000_000
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
        checkArraySize array
        if (array == null || array.empty)
            writeWrongInputAndExit()
        array
    }

    private static randomInput() {
        new Random().longs(MAX_ARRAY_SIZE - 1, 1, MAX_ARRAY_ELEMENT).toArray() as List
    }

    public static Long findAloneElement(List<Long> array) {
        println "Исходный массив: $array"
        for (int i = 0; i < array.size(); i++) {
            if (array[i] == null)
                continue
            for (int j = 0; j < array.size(); j++) {
                if (array[i] == array[j]) {
                    array.remove j
                    array.remove i
                    break
                }
            }
        }
        def k = array.find { it != null }
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

    private static checkArraySize(List array) {
        def size = array.size()
        if (size > MAX_ARRAY_SIZE || array.empty || size % 2 == 0) {
            println "Некорректная длина массива: $size"
            System.exit(-1)
        }
    }

    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }

}