package ru.mirea.labs.trpo.sem2.lab2.json

import static sun.plugin2.util.PojoUtil.toJson

trait Jsonable {
    String toString() {
        toJson(this)
    }
}