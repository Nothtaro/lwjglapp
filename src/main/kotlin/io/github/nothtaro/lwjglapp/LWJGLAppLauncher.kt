package io.github.nothtaro.lwjglapp

class LWJGLAppLauncher {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val game = SimpleGame()
            val engine = LWJGLEngine("hello lwjgl!", 1280,720, game)
            engine.start()
        }
    }
}