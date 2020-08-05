package com.te0tl.themoviesdb.platform.logging

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.te0tl.themoviesdb.BuildConfig
import timber.log.Timber

object Logger {

    fun e(exception: Throwable, message: String, vararg value: Pair<String, Any>) {
        Timber.e(exception, message, *value)
    }

    fun e(exception: Throwable) {
        Timber.e(exception)
    }

    fun i(message: String, vararg objects: Any) {
        Timber.i(message, objects)
    }

    fun i(exception: Throwable) {
        Timber.i(exception)
    }

    @SuppressLint("TimberArgCount")
    fun d(message: String, vararg objects: Any) {
        Timber.d(message, objects)
    }

    fun setupLogger(context: Context) {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .methodOffset(5)
            .showThreadInfo(false)
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        if (BuildConfig.DEBUG) {
            setupDebugTrees(context)
        } else {
            setupReleaseTrees(context)
        }

    }

    private fun setupReleaseTrees(context: Context) {
        TODO("Not yet implemented")
    }

    private fun setupDebugTrees(context: Context) {

        Timber.plant(object : Timber.Tree() {
            private var rawMessage = ""

            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }

            override fun formatMessage(message: String, args: Array<out Any>): String {
                rawMessage = message
                val builder = StringBuilder()

                try {
                    args.forEach {
                        val pair = it as Pair<String, *>
                        builder.append("\n${pair.first} ${pair.second}")
                    }
                } catch (e: Throwable) {
                }

                rawMessage = message.plus(builder.toString()).plus("\n")
                return message.plus(builder.toString())
            }

            @SuppressLint("TimberArgCount")
            override fun d(message: String?, vararg args: Any?) {
                super.d("${threadInfo()} $message", *args)
            }

            private fun threadInfo(): String {
                return "Thread: ${Thread.currentThread().name} ${Thread.currentThread().id} \n" +
                        "${getCustomStackTrace(Thread.currentThread().stackTrace)}\n"
            }


            private fun getCustomStackTrace(stackTraceElement: Array<StackTraceElement>): String {
                val stringBuilder = StringBuilder()
                stackTraceElement.forEach {
                    if ((it.className.contains(BuildConfig.APPLICATION_ID)
                                || it.className.contains("com.te0tl.commons"))
                        && !it.className.contains("platform.logging.")
                    ) {

                        stringBuilder.append(it.toString()
                            .replace(BuildConfig.APPLICATION_ID + ".", "")
                            .replace("com.te0tl.commons.", ""))
                        stringBuilder.append("\n")
                    }
                }

                return stringBuilder.toString()
            }
        })
    }

}