package io

public actual fun getenv(name: String): String? = System.getenv(name)

