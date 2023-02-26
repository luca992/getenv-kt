plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    val libs = libs
    // https://youtrack.jetbrains.com/issue/KTIJ-19369
    alias(libs.plugins.com.vanniktech.maven.publish) apply false
}
