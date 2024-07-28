import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    java
    idea
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    id("com.google.cloud.tools.jib")
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(17)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allprojects {
    group = "order.processing.system"
    version = "1.0"

    repositories {
        mavenCentral()
    }

    val springframeworkBoot: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {

            dependency("org.springframework.boot:spring-boot-starter-web:$springframeworkBoot")
            dependency("org.springframework.boot:spring-boot-starter-validation:$springframeworkBoot")
            dependency("org.springframework.boot:spring-boot-configuration-processor:$springframeworkBoot")
            dependency("org.springframework.boot:spring-boot-starter-data-jpa:$springframeworkBoot")
            dependency("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.2")
            dependency("org.springframework.retry:spring-retry:2.0.2")
            dependency("org.apache.kafka:kafka-clients:3.7.1")

            dependency("org.liquibase:liquibase-core:4.28.0")
            dependency("org.postgresql:postgresql:42.7.3")

            dependency("com.fasterxml.jackson.core:jackson-databind:2.15.2")
            dependency("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
            dependency("org.slf4j:slf4j-api:2.0.5")
            dependency("ch.qos.logback:logback-classic:1.4.6")
            dependency("org.projectlombok:lombok:1.18.26")
            dependency("org.projectlombok:lombok:1.18.26")
            dependency("org.mapstruct:mapstruct:1.5.3.Final")
            dependency("org.mapstruct:mapstruct-processor:1.5.3.Final")
            dependency("org.springframework.boot:spring-boot-gradle-plugin:3.1.0")

            dependency("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.13.0")


            dependency("org.junit.jupiter:junit-jupiter-api:5.8.1")
            dependency("org.junit.jupiter:junit-jupiter-engine:5.8.1")


            dependency("org.hibernate.orm:hibernate-core:6.5.2.Final")
            dependency("javax.cache:cache-api:1.1.1")
            dependency("org.hibernate.orm:hibernate-jcache:6.5.2.Final")
            dependency("org.ehcache:ehcache:3.10.8")
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showExceptions = true
        reports {
            junitXml.required.set(true)
            html.required.set(true)
        }
    }
}
