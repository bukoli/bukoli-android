# Bukoli Android SDK

[![GitHub license](https://img.shields.io/github/license/dcendents/android-maven-gradle-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![](https://img.shields.io/badge/platform-android-green.svg)
![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
![](https://img.shields.io/badge/Gradle-v2.2.1-red.svg)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.dcendents/android-maven-gradle-plugin.svg)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22android-maven-gradle-plugin%22)


![Screenshots](https://github.com/bukoli/bukoli-android/blob/master/art/readmescreenshots.png)

You can sign up for a Bukoli account at http://www.bukoli.com.

## Requirements

- Android  16+
- Android Studio 1.0.0+
- JDK 1.8+

## Dependencies

- [GSON v2.4](https://github.com/google/gson)
- [Glide v3.7.0](https://github.com/bumptech/glide)
- [Volley v1.0.19](https://developer.android.com/training/volley/index.html)

## Example

To run the example project, clone or download the repo, and open the project on Android Studio

## Setup
You can use Gradle or Maven to add the library as aar dependency to your build.
### Maven
[![Maven Central](https://img.shields.io/maven-central/v/com.github.dcendents/android-maven-gradle-plugin.svg)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22android-maven-gradle-plugin%22)
```groovy
<dependency>
  <groupId>com.mobillium.bukoliandroidsdk</groupId>
  <artifactId>bukoliandroidsdk</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

### Gradle
![](https://img.shields.io/badge/Gradle-v2.2.1-red.svg)
```groovy
dependencies {
    compile 'com.mobillium.bukoliandroidsdk:bukoliandroidsdk:1.+'
}
```

## Usage


### Api Key

You need api key to integrate sdk to your application. You can get it from [Bukoli](http://www.bukoli.com)


### Google Maps Api Key

You need an API key for Google Maps. Define your API key as meta-data into your project's manifest.xml file. This key will be used in SDK.

 [Google Maps Developer Documentation](https://developers.google.com/maps/documentation/android-api/)
```xml
 <application
        ...

        <!--
            The API key for Google Maps-based APIs is defined as a string resource.
            (See the file "res/values/google_maps_api.xml").
            Note that the API key is linked to the encryption key used to sign the APK.
            You need a different API key for each encryption key, including the release key that is used to
            sign the APK for publishing.
            You can define the keys for the debug and release targets in src/debug/ and src/release/.
        -->
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="@string/google_maps_key" />

    </application>
```

### Initialize

```java
import com.mobillium.bukoliandroidsdk.Bukoli;

Bukoli.sdkInitialize(getApplicationContext(), "your api key");
```

### Enable Debug
To enable debugging logs, you need to call setDebugEnabled(boolean) method.
```java
import com.mobillium.bukoliandroidsdk.Bukoli;

Bukoli.getInstance().setDebugEnabled(true); // or false for disable
```


### Customization

```java
import com.mobillium.bukoliandroidsdk.Bukoli;

Bukoli.getInstance()
                .setBrandName("Marka") // Brand Name for info dialog
                .setBrandName2("Marka'dan") // Ablative Brand Name for info dialog
                .setButtonTextColor(0xFFFFFFFF) // Text Colors on button
                .setButtonBackgroundColor(0xFFF8BA1B) // Button background color
                .setDarkThemeColor(0xFF3E3E3E) // Theme Dark color such as close, map center, target etc.
                .setShowPhoneDialog(false); // If you want to ask user phone number for Bukoli point
                
```

### Bukoli Select Point

First parameter is Activity context.
Second parameter is presenter callback for Point selection.

The selected point object and the phone number which is entered by user returns with onSuccess() method.
If the user cancels the process without selecting any point, onCancel() method will be called.
If Bukoli.getInstance().setShowPhoneDialog(true); used and process cancelled after selecting any point without entering the phone number, onError() method will be called and selected point will be returned.

```java
import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.callback.SelectPointCallBack;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;

Bukoli.getInstance().showPointSelection(this, new SelectPointCallBack() {
            @Override
            public void onSuccess(BukoliPoint bukoliPoint, String phoneNumber) {
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(MainActivity.this, bukoliPoint.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, bukoliPoint.getName() + "  " + phoneNumber, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Selection Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(BukoliPoint bukoliPoint) {
                Toast.makeText(MainActivity.this, "Phone Number Not Set", Toast.LENGTH_SHORT).show();
            }
        });
```

### Bukoli Info

First parameter is Activity context.
Second parameter is presenter callback for Info Dialog.

```java
import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.callback.InfoCallback;

Bukoli.getInstance().showInfoDialog(MainActivity.this, new InfoCallback() {
            @Override
            public void onDismiss() {
                Log.d("BUKOLI", "Dialog dismissed");
            }

            @Override
            public void onDisplay() {
                Log.d("BUKOLI", "Dialog displayed");

            }
        });
```


License
====================

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.