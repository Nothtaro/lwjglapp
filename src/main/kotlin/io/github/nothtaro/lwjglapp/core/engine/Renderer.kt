package io.github.nothtaro.lwjglapp.core.engine

import io.github.nothtaro.lwjglapp.core.shader.ShaderManager
import io.github.nothtaro.lwjglapp.core.shader.ShaderType
import io.github.nothtaro.lwjglapp.core.util.ResourceLoader
import org.lwjgl.opengl.GL33.*
import org.lwjgl.system.MemoryUtil

class Renderer {
    private lateinit var shaderProgram: ShaderManager

    private val vertices = floatArrayOf(
        0.0f,  0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f
    )

    private var vao = 0
    private var vbo = 0

    fun init() {
        shaderProgram = ShaderManager()
        shaderProgram.createShader(ResourceLoader.load("shader/shader.vert")!!, ShaderType.VERTEX)
        shaderProgram.createShader(ResourceLoader.load("shader/shader.frag")!!, ShaderType.FRAGMENT)
        shaderProgram.link()

        val vertBuffer = MemoryUtil.memAllocFloat(vertices.size)
        vertBuffer.put(vertices).flip()

        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertBuffer, GL_STATIC_DRAW)
        MemoryUtil.memFree(vertBuffer)

        glVertexAttribPointer(0,3, GL_FLOAT,false, 0,0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindVertexArray(0)
    }

    fun render() {
        shaderProgram.bind()

        glBindVertexArray(vao)
        glEnableVertexAttribArray(0)

        glDrawArrays(GL_TRIANGLES, 0, 3)

        glDisableVertexAttribArray(0)
        glBindVertexArray(0)

        shaderProgram.unbind()
    }

    fun dispose() {
        shaderProgram.dispose()

        glDisableVertexAttribArray(0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glDeleteBuffers(vbo)

        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
    }
}