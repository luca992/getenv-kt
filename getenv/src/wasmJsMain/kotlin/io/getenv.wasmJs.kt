package io

//language=JavaScript
public actual fun getenv(name: String): String? {
    js(
        code = """
            return process.env[name];
               """
    )
}
