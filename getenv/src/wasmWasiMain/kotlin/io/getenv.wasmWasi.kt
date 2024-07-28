package io

import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

@OptIn(UnsafeWasmMemoryApi::class)
public actual fun getenv(name: String): String? {
    withScopedMemoryAllocator { allocator ->
        val environCountPtr = allocator.allocate(4)
        val environBufSizePtr = allocator.allocate(4)
        val resultSizes = wasiEnvironSizesGet(environCountPtr.address.toInt(), environBufSizePtr.address.toInt())
        if (resultSizes != 0) error("wasi error code: $resultSizes")

        val environCount = environCountPtr.loadInt()
        val environBufSize = environBufSizePtr.loadInt()

        val environPtrs = allocator.allocate(environCount * 4)
        val environBuf = allocator.allocate(environBufSize)

        val resultEnviron = wasiEnvironGet(environPtrs.address.toInt(), environBuf.address.toInt())
        if (resultEnviron != 0) error("wasi error code: $resultEnviron")

        // Parse the environ buffer and split into environment variables
        val envVars = mutableListOf<String>()
        var currentVar = StringBuilder()

        for (i in 0 until environBufSize) {
            val byte = (environBuf + i).loadByte()
            if (byte.toInt() == 0) {
                envVars.add(currentVar.toString())
                currentVar = StringBuilder()
            } else {
                currentVar.append(byte.toInt().toChar())
            }
        }

        // Search for the requested environment variable by name
        for (envVar in envVars) {
            val (key, value) = envVar.split('=', limit = 2)
            if (key == name) {
                return value
            }
        }
    }
    return null
}
