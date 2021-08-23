package io.github.nothtaro.lwjglapp

import io.github.nothtaro.lwjglapp.core.engine.Window
import io.github.nothtaro.lwjglapp.core.engine.ISimpleGame
import io.github.nothtaro.lwjglapp.core.engine.Renderer

class SimpleGame: ISimpleGame {
    private val renderer = Renderer()

    override fun init() {
        renderer.init()
    }

    override fun update(interval: Float) {
    }

    override fun render(window: Window) {
        renderer.render()
    }

    override fun input(window: Window) {
    }

    override fun dispose() {
        renderer.dispose()
    }
}