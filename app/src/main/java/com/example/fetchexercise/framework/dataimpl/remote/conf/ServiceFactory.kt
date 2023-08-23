package com.example.fetchexercise.framework.dataimpl.remote.conf

import android.util.Log
import com.example.core.data.remote.base.ServiceDefinition
import com.example.fetchexercise.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

class ServiceFactory private constructor(
    var isDebug: Boolean,
    var baseURL: String,
    var headers: Headers,
    var isWithLogger: Boolean,
){
    private val LOG_TAG:String = "LT_ServiceFactory"
    private val OK_HTTP_TIMEOUT = 60L

    private val moshi = Moshi.Builder().run {
        Log.d(LOG_TAG,"-> Moshi.Builder() ")
        addLast(KotlinJsonAdapterFactory())
        build()
    }

    fun <T:ServiceDefinition> create(serviceClass: Class<T>): T {
        Log.d(LOG_TAG,"-> create() $serviceClass")
        val retrofitInstance = createRetrofit(isDebug, baseURL, headers)
        return createServiceFromRetrofit(retrofitInstance, serviceClass)
    }

    private fun <T> createServiceFromRetrofit(retrofit: Retrofit, serviceClass: Class<T> ): T {
        if(ServiceDefinition::class.java.isAssignableFrom(serviceClass)) return retrofit.create(serviceClass) as T
        else throw IllegalArgumentException("Unknown serviceClass name")
    }

    private fun createRetrofit(isDebug: Boolean, baseUrl: String, headers: Headers): Retrofit {
        Log.d(LOG_TAG,"-> createRetrofit() ")
        val interceptors = createInterceptors(isDebug, headers)
        val okHttpClient = createOkHttpClient(interceptors,isDebug)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }

    private fun createOkHttpClient(interceptors: List<Interceptor>, isDebug: Boolean): OkHttpClient = OkHttpClient.Builder().run {
        Log.d(LOG_TAG,"-> createOkHttpClient()  -> interceptors" + "${interceptors}}")

        hostnameVerifier(CustomHostNameVerifier(isDebug))
        interceptors.forEach { addInterceptor(it) }
        connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
        build()
    }


    private fun createInterceptors(isDebug: Boolean, headers:Headers): List<Interceptor> = mutableListOf<Interceptor>().apply {
        Log.d(LOG_TAG,"-> createLoggingInterceptors() isDebug: $isDebug, headers: $headers")
        add(createLoggingInterceptor())
        if(isDebug && isWithLogger) add(HeaderInterceptor(headers))
    }

    private fun createLoggingInterceptor(): LoggingInterceptor {
        //LoggingInterceptor - Interceptor for OkHttp3 with pretty logger
        val TAG_REQUEST = "Request"
        val TAG_RESPONSE = "Response"

        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request(TAG_REQUEST)
            .response(TAG_RESPONSE)
            .addHeader("version", BuildConfig.VERSION_NAME)
            .build()
    }

    override fun toString(): String {
        return "ServiceFactory(isDebug=$isDebug, baseURL='$baseURL', headers=$headers, isWithLogger=$isWithLogger, LOG_TAG='$LOG_TAG', moshi=$moshi)"
    }

    private class HeaderInterceptor(val headers: Headers): Interceptor{
        //HeaderInterceptor - For adding headers from HeaderFactory
        private val LOG_TAG:String = "LT_HeaderInterceptor"

        override fun intercept(chain: Interceptor.Chain): Response {

            Log.d(LOG_TAG, "-> intercept() ")
            val newBuilderRequest = chain.request().newBuilder()
            headers.forEach { pair ->
                val key = pair.first
                val value = pair.second
                Log.d(LOG_TAG, "-> intercept()  -> Adding header  name:$key value:$value")
                newBuilderRequest.addHeader(key, value)
            }
            val newRequest = newBuilderRequest.build()
            return chain.proceed(newRequest)
        }

    }
    private class CustomHostNameVerifier(val isDebug: Boolean): HostnameVerifier{

        private val LOG_TAG:String = "LT_CustomHostName"

        override fun verify(hostname: String?, session: SSLSession?): Boolean {
            Log.d(LOG_TAG,"-> verify() ")
            //We verify hostname when the app is in production, otherwise true
            if(!isDebug) return HttpsURLConnection.getDefaultHostnameVerifier().verify(hostname,session)
            return true
        }
    }

    class Builder {
        private val LOG_TAG:String = "LT_ServFactBuilder"

        private var isDebug:Boolean = true
        private var baseUrl:String  = ""
        private var headers:Headers  = HeadersFactory.getBaseHeaders()
        private var isWithLogger: Boolean = false

        fun isDebug(isDebug: Boolean) = apply {
            this.isDebug = isDebug
        }

        fun baseUrl(baseUrl: String) = apply {
            this.baseUrl = baseUrl
        }

        fun headers(headers: Headers) = apply{
            this.headers = headers
        }

        fun isWithLogger(isWithLogger:Boolean) = apply {
            this.isWithLogger = isWithLogger
        }

        fun build() = ServiceFactory(
            isDebug,
            baseUrl,
            headers,
            isWithLogger,
        ).apply {
            Log.d(LOG_TAG, "build() -> with values $this ")
        }
    }
}