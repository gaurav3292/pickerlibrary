# pickerlibrary

to use this library 
add         maven { url 'https://jitpack.io' }
to roor build.gradle under all repositories
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
        
    }
}
the add below dependency on app gradle 

implementation 'com.github.gaurav3292:pickerlibrary:1.0'
