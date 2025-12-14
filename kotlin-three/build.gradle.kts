plugins {
    id("wrappersbuild.kotlin-library-conventions")
}

dependencies {
    webMainApi(projects.kotlinJs)
    webMainApi(projects.kotlinWeb)
    webMainApi(projects.kotlinBrowser)

    webMainApi(npm(jspkg.types.three))

    webMainImplementation(libs.coroutines.core)

    webTestImplementation(libs.coroutines.test)
    webTestImplementation(libs.kotlin.test)
}
