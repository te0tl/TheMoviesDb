package com.era.themoviedb.model.data.service

import android.content.Context
import com.era.themoviedb.framework.extension.android.hasNetworkConnection
import com.orhanobut.logger.Logger
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object Retrofit : KoinComponent {
    private const val baseUrl = "https://api.themoviedb.org/3/"
    const val baseUrlImages = "https://image.tmdb.org/t/p/"
    private val appContext by inject<Context>()
    private val cache = Cache(appContext.cacheDir, (5 * 1024 * 1024).toLong())
    const val apiKey = "b2765287e51c23623756041dfb69eba0"

    val retrofitMovies: Retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                Logger.d("request to ${request.url()}")

                request = if (appContext.hasNetworkConnection() == true)
                    /*
                    *  If there is Internet, get the cache that was stored 5 seconds ago.
                    *  If the cache is older than 5 seconds, then discard it,
                    *  and indicate an error in fetching the response.
                    *  The 'max-age' attribute is responsible for this behavior.
                    */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    /*
                    *  If there is no Internet, get the cache that was stored 7 days ago.
                    *  If the cache is older than 7 days, then discard it,
                    *  and indicate an error in fetching the response.
                    *  The 'max-stale' attribute is responsible for this behavior.
                    *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                    */
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

                val response = chain.proceed(request)

                //Logger.d("raw response ${response.body()?.string()}")
                //Logger.d("response from ${request.url()}")

                response
            }
            .build()
    }
}


