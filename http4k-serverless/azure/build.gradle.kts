import org.http4k.internal.ModuleLicense.Apache2

description = "Http4k Serverless support for Azure Functions"

val license by project.extra { Apache2 }

plugins {
    id("org.http4k.module")
}


dependencies {
    api(project(":http4k-serverless-core"))
    api("com.microsoft.azure.functions:azure-functions-java-library:_")
    testImplementation(testFixtures(project(":http4k-core")))
}


