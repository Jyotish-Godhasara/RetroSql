package com.example.retrosql.retrofit

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.net.ssl.SSLSocketFactory

class SSLSocketFactory {
    companion object {
        fun certificat(): Array<TrustManager> {
            val trustAllCerts = arrayOf<TrustManager>(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            return trustAllCerts
        }

        fun getFactory(): SSLSocketFactory? {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, certificat(), SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.socketFactory
        }
    }
}