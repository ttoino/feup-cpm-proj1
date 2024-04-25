package pt.up.fe.cpm.tiktek.core.local

import java.security.PrivateKey
import java.security.PublicKey

interface LocalKeysDataSource {
    fun generateKeys()

    fun getPublicKey(): PublicKey

    fun getPrivateKey(): PrivateKey

    fun deleteKeys()
}
