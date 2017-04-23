package ru.mirea.labs.trpo.lab3

import java.util.stream.Collectors

class Lab3 {
    static void main(String[] args) {
        isPermutation(*manualInput())
        nextPermutation(*manualInput())
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
        def array = arrayOfStrings.stream().map(this.&convertStringToIntChecked).collect Collectors.toList()
        if (array == null || array.empty)
            writeWrongInputAndExit()
        array.toArray()
    }


    static boolean isPermutation(Long[] sequence) {
        println "Исходная последовательность: $sequence"
        if (sequence.length == 1) return sequence[0] == 1L
        def sequenceElements = 1L..sequence.length
        def elementsXor = sequenceElements.stream().reduce { a, b -> a ^ b }.get()
        def sequenceXor = Arrays.stream(sequence).reduce { a, b -> a ^ b }.get()
        if (elementsXor == sequenceXor) {
            println "Перестановка :)"
            return true
        }
        println "Не перстановка :("
        false
    }

    static Long[] nextPermutation(Long[] currentPermutation) {
        println "Исходная последовательность: $currentPermutation"
        int j = currentPermutation.length - 2
        while (currentPermutation[j] > currentPermutation[j + 1]) {
            j--
        }

        int k = currentPermutation.length - 1
        while (currentPermutation[j] > currentPermutation[k]) {
            k--
        }

        swap currentPermutation, k, j
        int r = currentPermutation.length - 1
        int s = j + 1

        while (r > s) {
            swap currentPermutation, r, s
            r--
            s++
        }
        println "Полученная последовательность: $currentPermutation"
        currentPermutation
    }

    static Integer[] generateRandomPermutation(int n) {
        def r = new Random()
        def permutation = 1..n as Integer[]
        for (int i = n - 9; i >= 0; i--) {
            def j = r.nextInt(n)
            swap permutation, j, i
        }
        println "Полученная перестановка: $permutation"
        permutation
    }

    private static void swap(array, int a, int b) {
        if (array[a] == array[b])
            return
        array[a] ^= array[b]
        array[b] ^= array[a]
        array[a] ^= array[b]
    }

    static Integer uniform(int n) {
        1 + new Random().nextInt(n) * (n - 1) / n
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


    private static writeWrongInputAndExit() {
        println "Неверный ввод"
        System.exit(-1)
    }

}
