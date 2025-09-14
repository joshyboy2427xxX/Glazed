plugins {
    id("fabric-loom") version "1.11-SNAPSHOT"
}

base {
    archivesName = properties["archives_base_name"] as String
    version = properties["mod_version"] as String
    group = properties["maven_group"] as String
}



repositories {
    maven {
        name = "meteor-maven"
        url = uri("https://maven.meteordevelopment.com/")
    }
    maven {
        name = "meteor-maven-snapshots"
        url = uri("https://maninmyvan.github.io/meteor-archive/maven/releases")
    }

    maven {
        url = uri("https://jitpack.io")
    }



    dependencies {
        // Fabric
        minecraft("com.mojang:minecraft:${properties["minecraft_version"] as String}")
        mappings("net.fabricmc:yarn:${properties["yarn_mappings"] as String}:v2")
        modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"] as String}")

        // Meteor
        modImplementation("meteordevelopment:meteor-client:0.5.7-1.21.8")


        // Baritone
        modImplementation("cabaletta:baritone:1.21.8")

        implementation("com.google.code.gson:gson:2.10.1")



    }



    tasks {
        processResources {
            val propertyMap = mapOf(
                "version" to project.version,
                "mc_version" to project.property("minecraft_version"),
            )

            inputs.properties(propertyMap)

            filteringCharset = "UTF-8"

            filesMatching("fabric.mod.json") {
                expand(propertyMap)
            }
        }

        jar {
            val licenseSuffix = project.base.archivesName.get()
            from("LICENSE") {
                rename { "${it}_${licenseSuffix}" }
            }
        }

        java {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release = 21
        }
    }
}

