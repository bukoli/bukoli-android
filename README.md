# Bukoli Android SDK

[![GitHub license](https://img.shields.io/github/license/dcendents/android-maven-gradle-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![](https://img.shields.io/badge/platform-android-green.svg)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
![](https://img.shields.io/badge/Gradle-v2.2.1-red.svg)
[ ![Maven-Central](https://api.bintray.com/packages/oguzhandongul/maven/bukoli-sdk/images/download.svg) ](https://bintray.com/oguzhandongul/maven/bukoli-sdk/_latestVersion)


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
![maven-central](https://img.shields.io/maven-central/v/me.drakeet.materialdialog/library.svg) 
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
    compile 'com.mobillium.bukoliandroidsdk:bukoliandroidsdk:1.0.0'
}
```


## Usage


### Api Key

You need api key to integrate sdk to your application. You can get it from [Bukoli](http://www.bukoli.com)



### Initialize

```java
import com.mobillium.bukoliandroidsdk.Bukoli;

Bukoli.sdkInitialize(getApplicationContext(), "your api key");
```

### Customization

```java
import com.mobillium.bukoliandroidsdk.Bukoli;

Bukoli.getInstance()
                .setBrandName("Marka") // Brand Name for info dialog
                .setBrandName2("Marka'dan") // Ablative Brand Name for info dialog
                .setButtonTextColor(0xFFFFFFFF) // Text Colors on button
                .setButtonBackgroundColor(0xFFF8BA1B) // Button background color
                .setShowPhoneDialog(false); // If you want to ask user phone number for Bukoli point
                
```

### Bukoli Select Point

First parameter is Activity context.
Second parameter is presenter callback for Point selection.

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