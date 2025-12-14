import karakum.vscode.generateKotlinDeclarations

plugins {
    id("declarations")
}

tasks.named("generateDeclarations") {
    doLast {
        val sourceDir = webGeneratedDir

        delete(sourceDir)

        val definitionFile = nodeModules.resolve("@types/three/index.d.ts")

        generateKotlinDeclarations(
            definitionsFile = definitionFile,
            sourceDir = sourceDir.asFile,
        )
    }
}
