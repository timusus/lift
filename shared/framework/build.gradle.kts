plugins {
    id("lift.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":shared:data"))
                api(project(":shared:database"))
                api(project(":shared:network"))
                api(project(":shared:logging"))
                api(project(":shared:model"))
                api(project(":shared:domain"))
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            linkerOpts.add("-lsqlite3")
            export(project(":shared:data"))
            export(project(":shared:database"))
            export(project(":shared:network"))
            export(project(":shared:logging"))
            export(project(":shared:model"))
            export(project(":shared:domain"))
        }
    }
}