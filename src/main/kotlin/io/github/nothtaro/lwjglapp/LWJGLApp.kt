package io.github.nothtaro.lwjglapp

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

class LWJGLApp(private val game : BasicGame)  {
    private var window = 0L

    fun run() {
        this.init()
    }

    private fun init() {
        //エラーコールバックの初期化
        GLFWErrorCallback.createPrint(System.err).set()
        //GLFWの初期化
        if(!glfwInit())
            throw IllegalStateException("GLFWの初期化に失敗しました")
        //GLFWのパラメータ設定
        glfwDefaultWindowHints()
        //GLFWウィンドウの作成
        window = glfwCreateWindow(1280, 720, "hello lwjgl!", NULL, NULL)
        if(window == NULL)
            throw RuntimeException("GLFWウィンドウの作成に失敗しました")
        //終了キーコールバックの登録
        glfwSetKeyCallback(window) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window,true)
        }
        //OpenGLコンテキストとGLFWを紐付け
        glfwMakeContextCurrent(window)
        //垂直同期の有効化
        glfwSwapInterval(1)
        //インターフェースからの初期化メソッド
        game.init()

        GL.createCapabilities()
        //ループメソッド
        loop()

        //終了後の処理
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    private fun loop() {
        while(!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
            glfwSwapBuffers(window)
            glfwPollEvents()

            game.input()
            game.update()
            game.render()
        }
    }
}

fun main() {
    val testGame = object : BasicGame {
        override fun init() {
        }

        override fun update() {
        }

        override fun render() {
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        }

        override fun input() {
        }

        override fun dispose() {
        }
    }

    LWJGLApp(testGame).run()
}

interface BasicGame {
    fun init()
    fun update()
    fun render()
    fun input()
    fun dispose()
}