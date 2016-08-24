#WaitingDots
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.tajchert/waitingdots/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/pl.tajchert/waitingdots)

![Loading animation](https://raw.githubusercontent.com/tajchert/WaitingDots/master/images/dotsLoadingAnimation.gif)

Small library that provides... bouncing dots. This feature is used in number of messaging apps (such as Hangouts or Messenger), and lately in Android TV (for example when connecting to Wifi).


Gradle depedency:
Add Jitpack in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Add the dependency itself:
```gradle
    compile 'com.github.tajchert:WaitingDots:0.3.0'
```

Code to make it work:
```xml
...
xmlns:dots="http://schemas.android.com/apk/res-auto"
...
<pl.tajchert.sample.DotsTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/dots"
    android:textSize="45sp"
    android:textColor="@android:color/text_color"
    dots:autoplay="false"
    dots:period="1000"/>
```
All aditional parameters are optional.

List of useful methods:
```java
dots.stop();
dots.start();

dots.hide();
dots.show();

dots.hideAndStop();
dots.showAndPlay();

dots.isHide();
dots.isPlaying();
```

###Thanks goes to:

<img src="images/polideaLogo.jpg" width="150" height="150"/>

[Polidea](https://www.polidea.com/) - time, atmosphere and motivation to create outstanding things.

[Zielony](https://github.com/ZieIony) - many tips and initial commit.

[Krzysztof Bielicki](https://github.com/krzysiekbielicki) - nice pull request that optimised performance and added text Appearance support (0.1.0 -> 0.2.0).
