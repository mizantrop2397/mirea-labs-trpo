package ru.mirea.labs.trpo.sem2.lab2.interceptor

import ru.mirea.labs.trpo.sem2.lab2.model.state.State

interface StateInterceptor {
    void stateChanged(State currentState, State newState)
}
