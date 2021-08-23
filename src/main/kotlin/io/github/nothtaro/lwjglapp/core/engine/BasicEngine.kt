package io.github.nothtaro.lwjglapp.core.engine

import io.github.nothtaro.lwjglapp.core.util.Timer
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL33.*

class BasicEngine(private val title: String, private val width: Int, private val height: Int, private val logic: ISimpleGame): Runnable {
    private lateinit var window: Window
    private var thread: Thread = Thread(this, "LOOP_THREAD")

    private fun initialize() {
        //エラーコールバックの初期化
        GLFWErrorCallback.createPrint(System.err).set()

        //GLFWの初期化
        if(!glfwInit())
            throw IllegalStateException("GLFWの初期化に失敗しました")

        //GLFWのパラメータ設定
        glfwDefaultWindowHints()

        //ウィンドウの作成
        window = Window(title, width, height)

        //OpenGLコンテキストとGLFWを紐付け
        glfwMakeContextCurrent(window.id)

        //垂直同期の有効化
        glfwSwapInterval(1)

        GL.createCapabilities()

        logic.init()
    }

    fun start() {
        thread.start()
    }

    override fun run() {
        try {
            initialize()
            loop()
            dispose()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loop() {
        val timer = Timer()

        while(!glfwWindowShouldClose(window.id)) {
            timer.update()

            input()

            while(timer.steps >= timer.spu) {
                update(timer.elapsedTime)
                timer.steps = timer.steps.minus(timer.spu)
            }

            render()
            timer.sync(timer.getTime())
        }
    }

    private fun input() {
        glfwPollEvents()
        logic.input(window)
    }

    private fun update(interval: Double) {
        logic.update(interval.toFloat())
    }

    private fun render() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glfwSwapBuffers(window.id)
        logic.render(window)
    }

    private fun dispose() {
        logic.dispose()
        //コールバックの開放
        glfwFreeCallbacks(window.id)
        //ウィンドウの開放
        glfwDestroyWindow(window.id)
        //GLFWの強制終了
        glfwTerminate()
        //エラーコールバックの開放
        glfwSetErrorCallback(null)?.free()
    }
}