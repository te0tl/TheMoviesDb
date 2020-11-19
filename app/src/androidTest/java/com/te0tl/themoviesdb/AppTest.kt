//package com.softeeth.themoviesdb
//
//import android.app.Application
//import android.content.Context
//import androidx.multidex.MultiDexApplication
//
//class AppTest: MultiDexApplication() {
//
//    override fun onCreate() {
//        super.onCreate()
//        println("AppTest onCreate")
///*        startKoin {
//            modules(listOf(
//                    module {
//                        viewModel  { SplashViewModel(mockk(), mockk(),mockk(),mockk()) }
//                    }
//            ))
//        }*/
//    }
//}
//
//class AppTestRunner: AndroidJUnitRunner() {
//
//    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
//        return super.newApplication(cl, AppTest::class.java.name, context)
//    }
//
//}