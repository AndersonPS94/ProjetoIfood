package com.example.core

sealed class UIStatus<out T> {
    class Sucesso<T>(val dados: T): UIStatus<T>()
    class Erro(val erro: String): UIStatus<Nothing>()
}