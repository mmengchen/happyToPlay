#百度地图混淆规则
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
#Bmob支付功能混淆规则
-keep class c.b.BP -keep class c.b.PListener -keep class c.b.QListener -keepclasseswithmembers class c.b.BP{ ; }
-keepclasseswithmembers class implements c.b.PListener{ ; }
-keepclasseswithmembers class implements c.b.QListener{ *; }