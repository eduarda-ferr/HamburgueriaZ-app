android {
    ...
    signingConfigs {
        release {
            storeFile file("../hamburgueriaz-release-key.jks") // ajuste o caminho se necessário
            storePassword "suaSenhaAqui"
            keyAlias "hamburgueriazKey"
            keyPassword "MariaEduarda343FB@"
        }
    }

    buildTypes {
        release {
            minifyEnabled false
            shrinkResources false
            signingConfig signingConfigs.release
        }
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}