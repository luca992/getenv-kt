package io

actual fun getenv(name: String): String? = System.getenv(name)

