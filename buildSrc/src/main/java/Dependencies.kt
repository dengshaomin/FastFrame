import org.gradle.api.JavaVersion

const val versionMajor = 1
const val versionMinor = 0
const val versionFix = 0

object AndroidConfig {
  const val targetSdkVersion = 30
  const val compileSdkVersion = 30
  const val minSdkVersion = 21
  const val buildToolsVersion = "30.0.2"
  const val applicationId = "com.code.demo"
  const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionFix
  const val versionName = "${versionMajor}.${versionMinor}.${versionFix}"
  val javaVersion = JavaVersion.VERSION_1_8
}

object GradleConfig {
  const val agp = "com.android.tools.build:gradle:4.1.0"
}

object Kotlin {
  const val version = "1.4.10"
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
  const val stdlib_jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
  const val gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
  const val core = "androidx.core:core-ktx:1.3.2"
}

object SupportConfig {
  const val appcompat_v7 = "androidx.appcompat:appcompat:1.2.0"
  const val constraint_layout = "androidx.constraintlayout:constraintlayout:2.0.4"

}

object Fresco {
  const val fresco = "com.facebook.fresco:fresco:0.14.1"

  //在 API < 14 上的机器支持 WebP 时，需要添加以下依赖
  const val animated_base_support = "com.facebook.fresco:animated-base-support:0.14.1"

  //支持GIF动图，需要添加以下依赖
  const val animated_gif = "com.facebook.fresco:animated-gif:0.14.1"

  //支持WebP，需要添加以下依赖
  const val webpsupport = "com.facebook.fresco:webpsupport:0.14.1"

  //支持WebP动图，需要添加以下依赖
  const val animated_webp = "com.facebook.fresco:animated-webp:0.14.1"

  //网络实现层想使用okhttp3，需要添加以下依赖
  const val imagepipeline_okhttp3 = "com.facebook.fresco:imagepipeline-okhttp3:0.14.1"
}

object Cc {
  const val register = "com.billy.android:cc-register:1.1.2"
  const val component = "com.billy.android:cc:2.1.6"
}

object Xinge {
  const val open = true
  const val XG_ACCESS_ID = "1500003187"
  const val XG_ACCESS_KEY = "AU5BRW66S5BL"
}

object Jpush {
  const val open = true
  const val JPUSH_APPKEY = "ea087a8371a0a5e0a97fabf1"
}

object Hwpush {
  const val open = true
  const val agcp = "com.huawei.agconnect:agcp:1.4.1.300"
}

object Bugly {
  const val APPID = "acd4fe4373"
  const val APPKEY = "312a6068-02f9-4198-944e-aebe5c02eefe"

  //升级功能
  const val UPDATE = true
  const val HOTFIX = true
}

object Immersionbar {
  // 基础依赖包，必须要依赖
  const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0"

  // fragment快速实现（可选）
  const val components = "com.gyf.immersionbar:immersionbar-components:3.0.0"

  // kotlin扩展（可选）
  const val ktx = "com.gyf.immersionbar:immersionbar-ktx:3.0.0"
}

object Others {
//  const val rxretrofit = "com.github.dengshaomin:RxRetrofit-master:1.0.8"
  const val eventbus = "org.greenrobot:eventbus:3.2.0"
  const val fastjson = "com.alibaba:fastjson:1.2.74"
  const val advrecyclerview = "com.h6ah4i.android.widget.advrecyclerview:advrecyclerview:1.0.0"
  const val banner = "com.youth.banner:banner:1.4.10"
  const val JPTabBar = "com.jpeng:JPTabBar:1.3.3"
  const val FlycoTabLayout_Lib = "com.flyco.tablayout:FlycoTabLayout_Lib:2.1.2@aar"
  const val statusbarutil = "com.jaeger.statusbarutil:library:1.5.1"
  const val multidex = "androidx.multidex:multidex:2.0.0"
  const val luban = "top.zibin:Luban:1.1.8"
  const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.5"
  const val retrofit  = "com.squareup.retrofit2:retrofit:2.6.2"

}