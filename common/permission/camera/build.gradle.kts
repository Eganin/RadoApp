plugins {
    id("multiplatform-setup")
    id("android-setup")
}

kotlin{
    sourceSets{
        commonMain{
            dependencies{

            }
        }

        androidMain{
            dependencies{
                implementation(Dependencies.Android.ExifInteface.core)
                implementation(Dependencies.Compose.AndroidXAppCompat.androidxAppCompat)
            }
        }
    }
}