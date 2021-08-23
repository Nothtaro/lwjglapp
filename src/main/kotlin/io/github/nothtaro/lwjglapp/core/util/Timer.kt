package io.github.nothtaro.lwjglapp.core.util

import org.lwjgl.glfw.GLFW

class Timer {
    var spu = 1.0 / 30.0
    private var previousTime = getTime()
    var elapsedTime = 0.0
    private var loopBeginTime = 0.0
    var steps = 0.0

    fun update() {
        loopBeginTime = getTime()
        elapsedTime = loopBeginTime - previousTime

        this.previousTime = loopBeginTime
        this.steps += elapsedTime
    }

    fun getTime(): Double {
        return GLFW.glfwGetTime()
    }

    fun sync(currentTime: Double) {
        val loopSlot = 1f / 50
        val endTime = currentTime + loopSlot

        while (getTime() < endTime) {
            Thread.sleep(1)
        }
    }
}