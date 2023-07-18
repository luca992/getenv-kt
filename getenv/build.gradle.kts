import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.com.vanniktech.maven.publish)
}

group = project.property("GROUP") as String
version = project.property("VERSION_NAME") as String

object Targets {
    val iosTargets = arrayOf("iosArm64", "iosX64", "iosSimulatorArm64")
    val tvosTargets = arrayOf("tvosArm64", "tvosX64", "tvosSimulatorArm64")
    val watchosTargets = arrayOf(
        "watchosArm32", "watchosArm64", "watchosX64", "watchosSimulatorArm64", "watchosDeviceArm64"
    )
    val macosTargets = arrayOf("macosX64", "macosArm64")
    val darwinTargets = iosTargets + tvosTargets + watchosTargets + macosTargets
    val linuxTargets = arrayOf("linuxX64", "linuxArm64")
    val mingwTargets = arrayOf("mingwX64")
    val androidTargets = arrayOf(
        "androidNativeArm32", "androidNativeArm64", "androidNativeX86", "androidNativeX64",
    )
    val nativeTargets = linuxTargets + darwinTargets + mingwTargets
}

kotlin {
    jvm()
    js(IR) {
        browser()
        nodejs()
    }
    @Suppress("OPT_IN_USAGE")
    wasm{
        browser()
        nodejs()
        d8()
    }
    for (target in Targets.nativeTargets) {
        targets.add(presets.getByName(target).createTarget(target))
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val commonTest by getting
        val jvmMain by getting {
            dependsOn(commonMain)
        }
        val jvmTest by getting {
            dependsOn(commonTest)
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }
        Targets.nativeTargets.forEach { target ->
            getByName("${target}Main") {
                dependsOn(nativeMain)
            }
            getByName("${target}Test") {
                dependsOn(nativeTest)
            }
        }
    }
}

plugins.withId("com.vanniktech.maven.publish") {
    mavenPublishing {
        publishToMavenCentral(SonatypeHost.S01)
        signAllPublications()
    }
}
