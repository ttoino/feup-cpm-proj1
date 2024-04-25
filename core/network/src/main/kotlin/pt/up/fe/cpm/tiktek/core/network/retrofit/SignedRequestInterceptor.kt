package pt.up.fe.cpm.tiktek.core.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.security.PrivateKey
import java.security.SecureRandom
import java.security.Signature
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class SignedRequestInterceptor
    @Inject
    constructor(
        private val sign: Signature,
        private val random: SecureRandom,
    ) : Interceptor {
        @OptIn(ExperimentalEncodingApi::class)
        override fun intercept(chain: Interceptor.Chain): Response =
            chain.proceed(
                chain.request().newBuilder().run {
                    val key = chain.request().tag(PrivateKey::class.java)

                    if (key != null) {
                        val salt = ByteArray(64)
                        random.nextBytes(salt)
                        val bodyArray =
                            chain.request().body()?.run {
                                val buffer = Buffer()
                                writeTo(buffer)
                                buffer.readByteArray()
                            } ?: ByteArray(0)
                        val content = salt + bodyArray

                        val signature =
                            sign.run {
                                initSign(key)
                                update(content)
                                sign()
                            }

                        addHeader("X-Salt", Base64.encode(salt))
                        addHeader("X-Signature", Base64.encode(signature))
                    }

                    build()
                },
            )
    }
