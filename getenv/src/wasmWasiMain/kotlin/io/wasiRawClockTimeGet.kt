package io

import kotlin.wasm.WasmImport

@WasmImport("wasi_snapshot_preview1", "environ_get")
private external fun wasiEnvironGet(environ: Int, precision: Long,  environ_buf: Int): Int