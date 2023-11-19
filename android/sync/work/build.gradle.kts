plugins {
    id("lift.android.library")
    id("lift.android.hilt")
}

android {
    namespace = "com.simplecityapps.lift.sync"
}

dependencies {
    implementation(projects.shared.common)
    implementation(projects.shared.domain)
    implementation(projects.shared.logging)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    implementation(libs.kotlinx.coroutines.android)

    kapt(libs.hilt.ext.compiler)

//    testImplementation(projects.core.testing)

//    androidTestImplementation(projects.core.testing)
//    androidTestImplementation(libs.androidx.work.testing)
}
