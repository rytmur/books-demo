plugins {
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.flywaydb.flyway") version "10.17.0"
	id("org.jooq.jooq-codegen-gradle") version "3.19.10"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jooq:jooq")
	implementation("org.jooq:jooq-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	jooqCodegen("org.postgresql:postgresql")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

flyway {
	url = "jdbc:postgresql://127.0.0.1:5432/booksdb"
	user = "postgres"
	password = "mypassword"
}

jooq {
	version = "3.19.10"

	configuration {
		jdbc {
			driver = "org.postgresql.Driver"
			url = "jdbc:postgresql://127.0.0.1:5432/booksdb"
			user = "postgres"
			password = "mypassword"

			properties {
				property {
					key = "user"
					value = "postgres"
				}
				property {
					key = "password"
					value = "mypassword"
				}
			}
		}
		generator {
			name = "org.jooq.codegen.KotlinGenerator"

			database {
				name = "org.jooq.meta.postgres.PostgresDatabase"
				includes = ".*"
				excludes = """
				     UNUSED_TABLE                # This table (unqualified name) should not be generated
				   | PREFIX_.*                   # Objects with a given prefix should not be generated
				   | SECRET_SCHEMA\.SECRET_TABLE # This table (qualified name) should not be generated
				   | SECRET_ROUTINE              # This routine (unqualified name) ...
			    """
				inputSchema = "public"
			}
			generate {
				// Tell the KotlinGenerator to generate properties in addition to methods for these paths. Default is true.
				isImplicitJoinPathsAsKotlinProperties = true

				// Workaround for Kotlin generating setX() setters instead of setIsX() in byte code for mutable properties called
				// <code>isX</code>. Default is true.
				isKotlinSetterJvmNameAnnotationsOnIsPrefix = true

				// Generate POJOs as data classes, when using the KotlinGenerator. Default is true.
				isPojosAsKotlinDataClasses = true

				// Generate non-nullable types on POJO attributes, where column is not null. Default is false.
				isKotlinNotNullPojoAttributes = true

				// Generate non-nullable types on Record attributes, where column is not null. Default is false.
				isKotlinNotNullRecordAttributes = true

				// Generate non-nullable types on interface attributes, where column is not null. Default is false.
				isKotlinNotNullInterfaceAttributes = true

				// Generate defaulted nullable POJO attributes. Default is true.
				isKotlinDefaultedNullablePojoAttributes = true

				// Generate defaulted nullable Record attributes. Default is true.
				isKotlinDefaultedNullableRecordAttributes = true
			}
			target {
				packageName = "com.example.booksdb.jooq.gen"
				directory = layout.buildDirectory.dir("jooq-gen").get().asFile.absolutePath
			}
		}
	}
}

sourceSets["main"].kotlin {
	srcDirs(layout.buildDirectory.dir("jooq-gen").get().asFile.absolutePath)
}
