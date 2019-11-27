# CodeFram
打包注意事项：
1.更换启动背景图
2.将ndk配置只支持：armeabi
3.修改maindexlist.keep,保证LoadMultidexActivity路径与包名正确
4.混淆文件增加数据keep网络请求数据结构目录，防止被混淆
