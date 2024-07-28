rootProject.name = "mini-market"
include("order_service")

pluginManagement {
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val jib: String by settings

    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.google.cloud.tools.jib") version jib
    }
}
include("notification_service")
