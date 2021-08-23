package io.github.nothtaro.lwjglapp.core.shader

import org.lwjgl.opengl.GL33.*

class ShaderManager {
    private var programID = 0
    private var vertexShaderID = 0
    private var fragmentShaderID = 0

    init {
        programID = glCreateProgram()
        if(programID == 0) {
            throw Exception("プログラムシェーダーの作成に失敗しました")
        }
    }

    fun createShader(shaderCode: String, type: ShaderType) {
        if(type == ShaderType.VERTEX) {
            vertexShaderID = createShader(shaderCode, GL_VERTEX_SHADER)
        } else if(type == ShaderType.FRAGMENT) {
            fragmentShaderID = createShader(shaderCode, GL_FRAGMENT_SHADER)
        }
    }

    private fun createShader(shaderCode: String, type:Int): Int {
        val shaderID = glCreateShader(type)
        if(shaderID == 0) {
            throw Exception("シェーダーの作成に失敗しました")
        }

        glShaderSource(shaderID, shaderCode)
        glCompileShader(shaderID)

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
            throw Exception("シェーダーのコンパイルに失敗しました")
        }

        glAttachShader(programID, shaderID)

        return shaderID
    }

    fun link() {
        glLinkProgram(programID)
        if(glGetProgrami(programID, GL_LINK_STATUS) == 0) {
            throw Exception("シェーダーのリンクに失敗しました")
        }

        if(vertexShaderID != 0) {
            glDetachShader(programID, vertexShaderID)
        }
        if(fragmentShaderID != 0) {
            glDetachShader(programID, fragmentShaderID)
        }

        glValidateProgram(programID)
        if(glGetProgrami(programID, GL_VALIDATE_STATUS) == 0) {
            error("シェーダーの確認中に問題が発生しています ${glGetProgramInfoLog(programID)}")
        }
    }

    fun bind() {
        glUseProgram(programID)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun dispose() {
        unbind()
        if(programID != 0) {
            glDeleteProgram(programID)
        }
    }
}