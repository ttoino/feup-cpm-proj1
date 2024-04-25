package pt.up.fe.cpm.tiktek.core.model

import dev.nesk.akkurate.annotations.Validate
import kotlinx.datetime.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.lang.IllegalArgumentException
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
data class AuthResponse(val token: String)

@Validate
@Serializable
data class LoginRequest(
    @Serializable(PublicKeySerializer::class)
    val key: PublicKey,
    val email: String,
    val password: String,
)

@Validate
@Serializable
data class RegisterRequest(
    @Serializable(PublicKeySerializer::class)
    val key: PublicKey,
    val name: String,
    val nif: String,
    val birthdate: LocalDate,
    val email: String,
    val password: String,
    val nameCc: String,
    val numberCc: String,
    val expirationDateCc: String,
    val cvvCc: String,
)

@Validate
@Serializable
data class PartialRegisterRequest(
    val name: String,
    val nif: String,
    val birthdate: LocalDate,
    val email: String,
)

@Serializable
@SerialName("Key")
private data class KeySurrogate(
    val format: String,
    val algorithm: String,
    val encoded: String,
)

@OptIn(ExperimentalEncodingApi::class)
object PublicKeySerializer : KSerializer<PublicKey> {
    override val descriptor: SerialDescriptor = KeySurrogate.serializer().descriptor

    override fun serialize(
        encoder: Encoder,
        value: PublicKey,
    ) {
        val surrogate =
            KeySurrogate(
                value.format,
                value.algorithm,
                Base64.encode(value.encoded),
            )
        encoder.encodeSerializableValue(KeySurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): PublicKey {
        val surrogate = decoder.decodeSerializableValue(KeySurrogate.serializer())
        val encoded = Base64.decode(surrogate.encoded)
        val keySpec =
            when (surrogate.format) {
                "X.509" -> X509EncodedKeySpec(encoded, surrogate.algorithm)
                "PKCS#8" -> PKCS8EncodedKeySpec(encoded, surrogate.algorithm)
                else -> throw IllegalArgumentException()
            }

        val factory = KeyFactory.getInstance(surrogate.algorithm)
        return factory.generatePublic(keySpec)
    }
}
