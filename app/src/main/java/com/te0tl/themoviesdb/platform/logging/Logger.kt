package com.te0tl.themoviesdb.platform.logging

import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.te0tl.themoviesdb.BuildConfig
import timber.log.Timber
import java.lang.StringBuilder

object Logger {

    fun e(exception: Exception, message: String, vararg value: Pair<String, Any>) {
        Timber.e(exception, message, *value)
    }

    fun e(exception: Exception) {
        Timber.e(exception)
    }

    fun i(message: String) {
        Timber.i(message)
    }

    fun i(exception: Exception) {
        Timber.i(exception)
    }

    fun d(message: String) {
        Timber.d(message)
    }

    fun d(exception: Exception) {
        Timber.d(exception)
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
                } catch (e: Exception) {
                }

                rawMessage = message.plus(builder.toString()).plus("\n")
                return message.plus(builder.toString())
            }

        })
    }

}