plugins {
    id("lift.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":shared:data:repository"))
            api(project(":shared:data:database"))
            api(project(":shared:data:firestore"))
            api(project(":shared:logging"))
            api(project(":shared:domain"))
            api(project(":shared:common"))
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            linkerOpts.add("-lsqlite3")
            export(project(":shared:data:repository"))
            export(project(":shared:data:database"))
            export(project(":shared:data:firestore"))
            export(project(":shared:logging"))
            export(project(":shared:domain"))
            export(project(":shared:common"))
        }
    }
}