#WheelView
![License](https://img.shields.io/badge/license-Apache2.0-blue.svg)
![SDK](https://img.shields.io/badge/sdk-16-orange.svg)
[ ![Download](https://api.bintray.com/packages/f1reking/maven/wheelview/images/download.svg) ](https://bintray.com/f1reking/maven/wheelview/_latestVersion) 
  
  
由于一个项目需要，我从网上找了一个类似，并改写了其中的代码开源出来。

滚轮选择器效果

# 效果预览
![](http://7xplt3.com1.z0.glb.clouddn.com/v4.gif)

# How to Use
### build.gradle
```java
dependencies {
  compile 'com.f1reking.library:wheelview:1.1'
}
```
### xml
```js
      <com.f1reking.wheelview.WheelView
        android:id="@+id/wheelview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        app:highColor="@color/colorPrimary"
        />
```

Remember put this for custom attribute usage
```
  xmlns:app="http://schemas.android.com/apk/res-auto"
```


### Java Code
```java
        wheelView = (WheelView) findViewById(R.id.wheelview); 
        wheelView.setOffset(1); //显示的前后个数（默认为1）
        wheelView.setItems(dataList); //加载list
        wheelView.setSeletion(0);// 启动后显示的位置
```


# Blog
[http://f1reking.com/](http://f1reking.com/)

# Weibo
[F1ReKing](http://weibo.com/jaly6829197/)

# License
```
Copyright 2016 F1ReKing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
