plugins {
    id("wrappersbuild.kotlin-library-conventions")
}

dependencies {
    webMainApi(projects.kotlinJs)
    webMainApi(projects.kotlinTypescript)
    webMainApi(projects.kotlinWeb)

    webMainApi(npm(jspkg.types.three))

    webMainImplementation(libs.coroutines.core)

    webTestImplementation(libs.coroutines.test)
    webTestImplementation(libs.kotlin.test)
}
