plugins {
    id("lift.library")
    alias(libs.plugins.sql.delight)
}

sqldelight {
    databases { // new wrapper
        create("LiftDatabase") {
            schemaOutputDirectory.set(file("src/main/sqldelight/databases"))
            packageName.set("com.simplecityapps.lift.database")
            dialect(libs.sql.delight.dialect)
        }
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.sql.delight.coroutines)
                implementation(libs.sql.delight.primitive.adapters)
                implementation(libs.kotlinx.datetime)
                implementation(project(":shared:common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.sql.delight.driver.android)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.sql.delight.driver.native)
            }
        }
    }
}