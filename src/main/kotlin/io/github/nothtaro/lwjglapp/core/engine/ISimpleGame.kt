package io.github.nothtaro.lwjglapp.core.engine

interface ISimpleGame {
    fun init() {}
    fun update(interval: Float)
    fun render(window: Window)
    fun input(window: Window)
    fun dispose() {}
}