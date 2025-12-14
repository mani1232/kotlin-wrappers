plugins {
    id("karakum.three-declarations")
}

dependencies {
   commonMainImplementation(npm(jspkg.types.three))
}

val syncThree by tasks.registering(SyncWrappers::class) {
    from(webGeneratedDir)
    into(webMainDir("kotlin-three"))
}
