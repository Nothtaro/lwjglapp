package io.github.nothtaro.lwjglapp

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class LWJGLEngine(var title: String, var width: Int, var height: Int, logic: ISimpleGame): Runnable {
    private lateinit var window: LWJGLWindow
    private var thread: Thread = Thread(this, "LOOP_THREAD")

    fun initialize() {
        //エラーコールバックの初期化
        GLFWErrorCallback.createPrint(System.err).set()

        //GLFWの初期化
        if(!glfwInit())
            throw IllegalStateException("GLFWの初期化に失敗しました")

        //GLFWのパラメータ設定
        glfwDefaultWindowHints()

        //ウィンドウの作成
        window = LWJGLWindow("hello lwjgl", 1280,720)

        //OpenGLコンテキストとGLFWを紐付け
        glfwMakeContextCurrent(window.id)

        //垂直同期の有効化
        glfwSwapInterval(1)

        GL.createCapabilities()
    }

    fun start() {
        thread.start()
    }

    override fun run() {
        try {
            initialize()
            loop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loop() {
        while(!glfwWindowShouldClose(window.id)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
            glfwSwapBuffers(window.id)
            glfwPollEvents()
        }
    }

    private fun dispose() {
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