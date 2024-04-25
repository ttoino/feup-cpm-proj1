package pt.up.fe.cpm.tiktek.core.data

import java.security.PrivateKey
import java.security.PublicKey

interface KeysRepository {
    val publicKey: PublicKey

    val privateKey: PrivateKey

    fun generateKeys()
}
