#WheelView
![License](https://img.shields.io/badge/license-Apache2.0-blue.svg)
![SDK](https://img.shields.io/badge/sdk-16-orange.svg)
![maven-central](https://img.shields.io/maven-central/v/com.f1reking.wheelview/wheelview.svg)  
  
  
由于一个项目需要，我从网上找了一个类似，并改写了其中的代码开源出来。

滚轮选择器效果

# 效果预览
![](http://7xplt3.com1.z0.glb.clouddn.com/v4.gif)

# How to Use
### build.gradle
```java
dependencies {
  compile 'com.f1reking.wheelview:wheelview:0.1'
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
Apache 2.0

