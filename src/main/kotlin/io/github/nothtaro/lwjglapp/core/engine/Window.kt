package io.github.nothtaro.lwjglapp.core.engine

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil

class Window(title: String, width: Int, height: Int) {
    var id = 0L
    //var vsync = false

    init {
        //GLFWウィンドウの作成
        id = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if(id == MemoryUtil.NULL) {
            throw RuntimeException("GLFWウィンドウの作成に失敗しました")
        } else {
            println("GLFWウィンドウを作成しました id: $id")
        }

        glfwSetKeyCallback(this.id) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window,true)
        }
    }

   /*
   fun isKeyPressed(keyCode: Int): Boolean {
        return glfwGetKey(this.id, keyCode) == GLFW_PRESS
   }
   */
}