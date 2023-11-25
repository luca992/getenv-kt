package io

actual fun getenv(name: String): String? {
    return process.env[name] as String?
}