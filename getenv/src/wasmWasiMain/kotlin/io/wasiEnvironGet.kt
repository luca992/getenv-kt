package io

import kotlin.wasm.WasmImport

@WasmImport("wasi_snapshot_preview2", "environ_sizes_get")
internal external fun wasiEnvironSizesGet(environCountPtr: Int, environBufSizePtr: Int): Int

@WasmImport("wasi_snapshot_preview2", "environ_get")
internal external fun wasiEnvironGet(environPtrs: Int, environBuf: Int): Int
