package io.github.nothtaro.lwjglapp

interface ISimpleGame {
    fun init() {}
    fun update(interval: Float)
    fun render(window: LWJGLWindow)
    fun input(window: LWJGLWindow)
    fun dispose() {}
}