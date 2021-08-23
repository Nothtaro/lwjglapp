package io.github.nothtaro.lwjglapp.core.util

object ResourceLoader {
    fun load(path: String): String? {
        return javaClass.classLoader.getResource(path)?.readText()
    }
}