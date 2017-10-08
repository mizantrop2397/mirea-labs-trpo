package ru.mirea.labs.trpo.sem1.lab1

import java.util.stream.Collectors

class Part1 {
    final static int MAX_ARRAY_SIZE = 100
    final static int MAX_ARRAY_ELEMENT_MOD = 1000
    final static int MAX_K_MOD = 100

    static void main(String[] args) {
        println "Выберите метод задания массива (m - manual, r - random): "
        def scanner = new Scanner(System.in)
        def mode = scanner.nextLine()
        if (mode == null || (mode != 'm' && mode != 'r'))
            writeWrongInputAndExit()
        switch (mode) {
            case "r": moveArray(*randomInput())
                break
            case "m": moveArray(*manualInput())
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
        def array = arrayOfStrings.stream().map(this.&convertStringToIntChecked).peek(this.&checkArrayElement).collect Collectors.toList()
        checkArraySize array
        if (array == null || array.empty)
            writeWrongInputAndExit()
        println "Введите число K: "
        def kStr = scanner.nextLine()
        if (kStr == null || kStr.empty)
            writeWrongInputAndExit()
        def k = convertStringToIntChecked kStr
        if (k == null)
            writeWrongInputAndExit()
        checkK k
        [array, k]
    }

    private static randomInput() {
        Random r = new Random()
        def array = r.ints(MAX_ARRAY_SIZE, -MAX_ARRAY_ELEMENT_MOD, MAX_ARRAY_ELEMENT_MOD).toArray() as List
        def k = r.ints(1, -MAX_K_MOD, MAX_K_MOD).findFirst().getAsInt()
        [array, k]
    }

    static List moveArray(List array, int k) {
        println "Исходный массив: $array, k : $k"
        if (Math.abs(k) > array.size()) {
            println "K=$k > arraySize=${array.size()}"
            System.exit(-1)
        }
        def newArray = new ArrayList()
        int n = array.size()
        if (k > 0) {
            array.stream().skip(n - k).forEach(newArray.&add)
            array.stream().limit(n - k).forEach(newArray.&add)
        } else {
            k *= -1
            array.stream().skip(k).forEach(newArray.&add)
            array.stream().limit(k).forEach(newArray.&add)
        }
        println "Полученный массив: $newArray"
        newArray
    }

    private static Integer convertStringToIntChecked(String string) {
        def integer
        try {
            integer = Integer.valueOf string
            integer
        } catch (Exception e) {
            println "Неверный ввод объекта: $string"
            System.exit(-1)
        }
    }

    private static checkArrayElement(int element) {
        if (element > MAX_ARRAY_ELEMENT_MOD || element < -MAX_ARRAY_ELEMENT_MOD) {
            println "Элемент массива вне диапазона: $element"
            System.exit(-1)
        }
    }

    private static checkArraySize(List array) {
        def size = array.size()
        if (size > MAX_ARRAY_SIZE) {
            println "Длина массива вне диапазона: ${size}"
            System.exit(-1)
        }
    }

    private static checkK(int k) {
        if (k > MAX_K_MOD || k < -MAX_K_MOD) {
            println "k вне диапазона: $k"
            System.exit(-1)
        }
    }

    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }

}