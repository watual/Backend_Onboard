plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.donjo"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

//    Valid
    implementation("org.springframework.boot:spring-boot-starter-validation")

//    JDBC
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

//    JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

////    JWT
//    implementation("io.jsonwebtoken:jjwt:0.11.5")
//    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
//    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

//    PasswordEncoder
    implementation("org.springframework.security:spring-security-crypto")
//    Security
//    implementation("org.springframework.boot:spring-boot-starter-security")
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    testImplementation("org.springframework.security:spring-security-test")

//    Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

//    H2 Embedded mode
    runtimeOnly("com.h2database:h2")

//    Spring Web
    implementation("org.springframework.boot:spring-boot-starter-web")

//    Junit
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//    default
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
