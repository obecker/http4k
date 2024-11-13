import org.http4k.internal.ModuleLicense.Apache2

description = "A set of Strikt matchers for common http4k types"

val license by project.extra { Apache2 }

plugins {
    id("org.http4k.module")
}


dependencies {
    api(project(":http4k-core"))
    implementation(project(":http4k-format-core"))
    api(Testing.strikt.core)
    testImplementation(project(":http4k-format-jackson"))
}
