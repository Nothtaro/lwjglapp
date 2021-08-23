package io.github.nothtaro.lwjglapp

import io.github.nothtaro.lwjglapp.core.engine.BasicEngine

class LWJGLAppLauncher {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val game = SimpleGame()
            val engine = BasicEngine("hello lwjgl!", 1280,720, game)
            engine.start()
        }
    }
}