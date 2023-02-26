package io

import kotlinx.cinterop.toKString
import platform.posix.getenv

actual fun getenv(name: String): String? = getenv(name)?.toKString()

