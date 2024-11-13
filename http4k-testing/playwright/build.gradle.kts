import org.http4k.internal.ModuleLicense.Apache2

description = "http4k extensions for testing with Playwright"

val license by project.extra { Apache2 }

plugins {
    id("org.http4k.module")
}


dependencies {
    api(project(":http4k-core"))
    api("com.microsoft.playwright:playwright:_")
    implementation(Testing.junit.jupiter.api)

    testImplementation(testFixtures(project(":http4k-core")))

}
