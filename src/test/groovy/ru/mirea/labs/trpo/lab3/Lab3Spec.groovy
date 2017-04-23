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
        def perms = []
        t.times {
            def generatedPerms = []
            m.times {
                generatedPerms << Lab3.generateRandomPermutation(n)
            }
            def generatedPerm = generatedPerms[Lab3.uniform(m)]
            for (int i = 0; i < elementsCount.size(); i++) {
                for (int j = 0; j < elementsCount[i].size(); j++) {
                    if (j == generatedPerm[i])
                        elementsCount[i][j]++
                }
            }

            perms << generatedPerm
        }

        then:
        perms.each {
            Lab3.isPermutation(it as Long[])
        }
        println "Полученная статистика: "
        print "   "
        for (int i = 1; i <= n; i++) {
            print "$i | "
        }
        println()
        for (int i = 0; i < elementsCount.length; i++) {
            print "$i: "
            for (int j = 1; j < elementsCount[i].length; j++) {
                print "${elementsCount[i][j]} | "
            }
            println()
        }

        where:
        n   | m   | t
        10  | 100 | 100
        100 | 100 | 100
    }
}
