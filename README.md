> 由于一个项目需要，我从网上找了一个类似，并改写了其中的代码开源出来。

滚轮选择器效果

# 效果预览
![](http://7xplt3.com1.z0.glb.clouddn.com/v4.gif)

# 使用方法
### build.gradle文件
```java
dependencies {
  compile 'com.f1reking.wheelview:wheelview:0.1'
}
```
### xml引用
```js
      <com.f1reking.wheelview.WheelView
        android:id="@+id/wheelview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        app:highColor="@color/colorPrimary"
        />
```
### Java代码
```
        wheelView = (WheelView) findViewById(R.id.wheelview); 
        wheelView.setOffset(1); //显示的前后个数（默认为1）
        wheelView.setItems(dataList); //加载list
        wheelView.setSeletion(0);// 启动后显示的位置
```


# 个人博客
[http://f1reking.com/](http://f1reking.com/)

# 作者微博
[F1ReKing](http://weibo.com/jaly6829197/)

# License
Apache 2.0

