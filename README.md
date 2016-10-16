#乐玩APP 
######注：项目仍处于开发阶段，本项目仅供学习使用，未经本人授权，请勿进行商业用途
    一款基于MVP(Model-View-Presenter)设计模式开发的Adroid APP项目，一款基于Android 操作系统的
    集游玩和社交功能的APP
    
***
###功能测试

- 登陆 、注册、 活动发布、点赞功能、取消点赞(数据显示正常)、个人信息显示、头像处理没有问题
- 活动的详情显示没有问题、发布评论没有问题
   
***  
###已经实现的功能
- 2016/10/15 [第二次提交]添加参加活动的功能,对免费和付费活动的支付功能进行完善

- 2016/10/15 [第一次提交]添加活动的评论显示功能(修改数据库表与表之间的关系实现数据显示)、评论显示
             功能支持上拉刷新和下拉加载

- 2016/10/15 添加活动的评论的功能,对一些模块进行可执行性测试,完善头像显示存在的bug

- 2016/10/14 完善更多页面的查询操作并实现数据的显示功能

- 2016/10/13 添加微信分享功能(分享到朋友圈和分享到好友列表),添加活动详细信息页面布局、对Fragment进行优化

- 2016/10/12 添加信息页面的布局显示(仿QQ消息和电话切换效果)、完善设置跳转后个人信息页面的数据显示、添加支付功能测试(具体支付情况,暂未设置),添加发布信息的提示界面
      (该界面将进行分享功能的实现)
 
- 2016/10/11 添加双击退出APP的信息提示、添加更多界面的布局、完成城市列表的获取和显示功能、添加个人信息界面及相关代码的优化 

- 2016/10/10 添加点赞功能，添加基于百度地图的定位功能，完善活动信息显示页面(实现距离显示、用
户信息的优化)、Readme文档的重构

- 2016/10/08 添加主页活动动态显示功能（数据由Bmob云端服务器获取）

- 2016/10/07 添加图片的批量上传、活动的发布、基于BaiduMap的POI检索等功能

- 2016/09/29 构建基本MVP框架，添加登陆、注册、加载头像功能 

***
###需要实现的功能

- 自动更新功能

- 消息推送 功能

- 第三方登陆功能(暂时个人开发者不能使用)

- 活动的评论的显示功能-->已经实现

- 活动的评论功能-->已经实现

- 活动的支付功能(微信支付和支付宝支付功能)-->已经实现

- 暂未实现从图库添加图片的功能、选择地点的百度地图跳转的功能-->已经实现

- 更多页面的功能的实现-->已经实现

- 信息页面功能的实现-->已经实现

***
###需要进行优化的地方
- 当点击评论下拉刷新，会出现问题，需要更改

- 信息界面无法滑动到个人中心(Bug),后续将进行修复
 
- 发布信息页面，单击按钮有延迟、进度条显示过快

- 程序中所有的图片加载，将统一使用ImageLoader

- 发布活动中需要对照片进行图片压缩
    
- 在动画开始时，进行较多耗时操作，，进入主页面速度变慢，，，，

- 在获取网络状态时，应该判断是否可以上网，并不是是否有开启wifi
    
- ListView 在频繁下拉活动时应该使用ImageLoader防止发生OOM异常

- 当未设置头像市会出现空指针异常的情况
    解决方案：给图片设置一个默认的URI(图片地址由服务器维护)，当用户未进行设置图片时,
    显示
  
- 启动页面，使用sharedprefrences将数据保存到本地
    解决方案：更改为直接使用BmobUser 类的getcurrentUser方法，判断是否为空，不为空则为登陆过
- 更多页面的热门查询和模糊查询暂未实现(原因:模糊查询为付费用户,热门查询暂未找到合适的方法)
    解决方案：热门查询(默认显示点赞活动大于5的数据信息)
  
- 出现跳转到3次主页（完成修改，之前放入循环中）--
    问题原因：将跳转功能放入了循环中
    解决方案：将跳转功能放到循环结束之后
    
- 存在一些小问题，契约接口类，可以根据相同点，抽取公共方法
    
     1. fragment contract 应该统一个基础的接口
               
     2. 分析：重复方法 1.p层 加载List 、 GridVIew 的方法   可以统一为一
            loadingViw(View view)
     3. 独立拥有的方法：
    
- 加载更多数据应该是接上面的数据进行显示-->已经修改
***
###开发过程中遇到的问题(可能导致程序无法运行的bug)及解决方案

- 
  
- 拍照时无法进行图片的裁剪（不断加载）
 
       解决步骤如下：<br>
    1. Activity跳转时图库时的Intent如下 
           `Intent takePhotoIntent = new Intent( "android.media.action.IMAGE_CAPTURE");
            takePhotoIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    imageUri);`
    2. 在onActivityResult()方法中调用P层进行处理，相关代码如下

        `//获取图片的路径
       String uri = Environment.getExternalStorageDirectory() + "/icoImage.jpg";
        if(!allSelectedPicture.contains(uri)){
            allSelectedPicture.add(uri);
        }`

- 头像的处理未进行非空判断,没有判断从网络上是否加载到图片

    解决 :使用ImageLoader框架，当图片加载失败的时候，显示失败图片
   
        //设置图片加载器的参数
        DisplayImageOptions options = ImageLoaderutils.myGetOpt 
        (R.drawable.loading, R.drawable.logding_error);
        //获取图片加载器对象
        ImageLoader loader = ImageLoaderutils.getInstance(MyApplitation.context);
        //展示图片
        loader.displayImage(uri, mFragPersonIvHead, options);
- 从图库中找到图片，暂时未进行上传，出现问题

       解决步骤如下<br>
    1. Activity跳转时的Intent为 

          ` Intent intent = new Intent(
                              Intent.ACTION_PICK,android.provider.MediaStore.
                Images.Media.EXTERNAL_CONTENT_URI);`

    2. 在onActivityResult()方法中调用P层进行处理，相关代码如下

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImage,
                      filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            //将资源显示到k控件上
            if(!allSelectedPicture.contains(picturePath)){
                  allSelectedPicture.add(picturePath);
             }
            cursor.close();//释放资源`


- 暂未对用户数据进行网络加载，仅仅使用了缓存对用户数据进行变更

      解决 :使用Bmob云进行用户信息的加载，并且缓存到内存中

- 警告对话框没有取消，可能原因  在切换Activity时重新创建的，或者是上次未进行关闭的

      解决: 在BaseActivity 中添加dismissAlertDialog()方法

- 验证短信验证码正确与否出现相关问题暂未处理

      解决：查询Bmob官方SDK，进行代码优化

- 主页面暂未实现点击按钮切换颜色

      解决 ：使用selector 

- 出现XlistView 显示用户相关信息是，为空，出现空指针异常

      解决 ：在适配器中进行当前活动的发布者信息的加载
    
  
***   
###项目中使用的主要技术及框架
- 框架
      1. universal-image-loader

      2. ButterKnife 
      
      3. okhttp
      
- 技术：

##License

Copyright 2016 [mmengchen](https://github.com/mmengchen "mmengchen")

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.