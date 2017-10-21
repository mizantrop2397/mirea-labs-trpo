package ru.mirea.labs.trpo.sem2.lab2.core

import ru.mirea.labs.trpo.sem2.lab2.interceptor.StateInterceptor
import ru.mirea.labs.trpo.sem2.lab2.model.state.InputState
import ru.mirea.labs.trpo.sem2.lab2.model.state.State

class Automat {
    private Deque<State> states
    private StateInterceptor stateInterceptor

    Automat(StateInterceptor interceptor) {
        states = new LinkedList<>()
        this.stateInterceptor = interceptor
    }

    void process(List<InputState> inputStates) {
        inputStates?.each {
            it.with {
                if (destLevel == srcLevel) return

                if (!states.empty && srcLevel == states.peekFirst().destLevel) {
                    println "Out from stack: ${states.pop()}"
                }
                def state = new State(currentLevel: srcLevel, destLevel: destLevel)
                if (!states.empty && destLevel > states.peekFirst().destLevel) {
                    states.addLast(state)
                    println "Into tail stack: ${state}"
                    return
                }
                states.addFirst(state)
                println "Into head stack: ${state}"
            }
        }
        while (!states.empty) {
            println "Out from stack: ${states.pop()}"
        }
    }

    public static void main(String[] args) {
        new Automat({ State currentState, State newState -> }).process(
                [
                        new InputState(srcLevel: 1, destLevel: 2),
                        new InputState(srcLevel: 3, destLevel: 4),
                        new InputState(srcLevel: 5, destLevel: 6),
                        new InputState(srcLevel: 7, destLevel: 8),
                        new InputState(srcLevel: 9, destLevel: 10),
                        new InputState(srcLevel: 11, destLevel: 12),
                ]
        )
    }

}
