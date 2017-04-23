package ru.mirea.labs.trpo.lab3

import spock.lang.Specification
import spock.lang.Unroll


class Lab3Spec extends Specification {
    @Unroll
    "Should check sequence: '#sequence' for permutation: '#permutation'"() {
        expect:
        Lab3.isPermutation(sequence as Long[]) == isPermutation

        where:
        sequence             | isPermutation
        [1L, 2L, 5L, 4L, 3L] | true
        [4L, 3L, 2L, 1L, 5L] | true
        [4L, 4L, 2L, 1L, 5L] | false
    }

    @Unroll
    "Should get next permutation: '#permutation' from sequence: '#sequence'"() {
        when:
        def nextPerm = Lab3.nextPermutation(sequence as Long[])

        then:
        Arrays.equals nextPerm, permutation.toArray()
        println nextPerm

        where:
        sequence             | permutation
        [1L, 2L, 5L, 4L, 3L] | [1L, 3L, 2L, 4L, 5L]
        [4L, 3L, 2L, 1L, 5L] | [4L, 3L, 2L, 5L, 1L]
    }

    @Unroll
    "Should generate random permutation of length: '#n'"() {
        when:
        def nextPerm = Lab3.generateRandomPermutation(n)

        then:
        Lab3.isPermutation(nextPerm as Long[])

        where:
        n << [10, 100, 500]
    }

    @Unroll
    "Should generate m: '#m' random permutations of length: '#n' t: '#t' times"() {
        when:
        def elementsCount = 1..n as Integer[][]
        for (int i = 0; i < elementsCount.length; i++) {
            elementsCount[i] = 0..n
        }
        for (int i = 0; i < elementsCount.length; i++) {
            for (int j = 0; j < elementsCount[i].size(); j++) {
                elementsCount[i][j] = 0
            }
        }
        def permutations = []
        t.times {
            def generatedPermutations = []
            m.times {
                generatedPermutations << Lab3.generateRandomPermutation(n)
            }
            def generatedPermutation = generatedPermutations[Lab3.uniform(m)]
            for (int i = 0; i < elementsCount.length; i++) {
                for (int j = 0; j < elementsCount[i].length; j++) {
                    if (j == generatedPermutation[i])
                        elementsCount[i][j]++
                }
            }

            permutations << generatedPermutation
        }

        then:
        permutations.each {
            Lab3.isPermutation(it as Long[])
        }
        println "Полученная статистика: "
        print "   "
        for (int i = 1; i <= n; i++) {
            printf "%10d", i
        }
        println()
        for (int i = 0; i < elementsCount.length; i++) {
            print "[$i]:"
            for (int j = 1; j < elementsCount[i].length; j++) {
                printf "%10d", elementsCount[i][j]
            }
            println()
        }

        where:
        n   | m   | t
        10  | 100 | 100
        100 | 100 | 100
        500 | 10  | 10
    }
}
