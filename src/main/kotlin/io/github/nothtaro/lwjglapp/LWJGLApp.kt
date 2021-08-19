package io.github.nothtaro.lwjglapp

class LWJGLApp(private val game : BasicGame)  {
    fun run() {
        this.init()
    }

    private fun init() {
        game.init()
    }
}

interface BasicGame {
    fun init()
    fun update()
    fun render()
    fun input()
    fun dispose()
}

fun main() {
    val testGame = object : BasicGame {
        override fun init() {
        }

        override fun update() {
        }

        override fun render() {
        }

        override fun input() {
        }

        override fun dispose() {
        }
    }

    LWJGLApp(testGame).run()
}