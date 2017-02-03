# Robotium-Examples
Sample code for Android automation using Robotium 

This is a sample test project for the notepad application provided in Robotium site.
This can be downloaded from 
http://dl.bintray.com/robotium/generic/ExampleTestProject_AndroidStudio.zip

To use this we tests have to first set up the above project in Android Studio. 

After this add the below mentioned robotium dependency to the app gradle file. 

androidTestCompile 'com.jayway.android.robotium:robotium-solo:5.6.0'

After adding this your app build/gradel looks likes this.

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:23.0.1'
    androidTestCompile 'com.jayway.android.robotium:robotium-solo:5.6.0'
    androidTestCompile 'com.android.support.test:rules:0.4.1'
    androidTestCompile 'junit:junit:4.12'
}