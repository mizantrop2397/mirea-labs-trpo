package ru.mirea.labs.trpo.sem2.lab2.io.system

import ru.mirea.labs.trpo.sem2.lab2.model.InputInfo
import ru.mirea.labs.trpo.sem2.lab2.model.OutputInfo

class IoSystem {
    static String PATH = "D:\\Development\\Java_Projects\\mirea\\mirea-labs-trpo\\src\\main\\resources\\sem2\\lab2"

    static List<InputInfo> read() {
        File input = ["$PATH/input.txt"]
        def lines = input.readLines()
        lines.subList(1, lines.size()).collect {
            def info = it.split(" ")
            InputInfo.builder()
                    .time(info[0] as int)
                    .src(info[1] as int)
                    .dest(info[2] as int)
                    .build()
        }

    }

    static void write(List<OutputInfo> output) {
        File outputFile = ["$PATH/output.txt"]
        outputFile.withWriter { it << "time level doors\n" }
        output.each { outputFile << "${it.time} ${it.level} ${it.doorsState}\n" }
    }
}
