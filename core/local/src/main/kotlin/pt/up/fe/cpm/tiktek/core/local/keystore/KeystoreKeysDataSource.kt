package pt.up.fe.cpm.tiktek.core.local.keystore

import pt.up.fe.cpm.tiktek.core.local.LocalKeysDataSource
import pt.up.fe.cpm.tiktek.core.local.di.UserKeyAlias
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.KeyStore.PrivateKeyEntry
import java.security.PrivateKey
import java.security.PublicKey
import javax.inject.Inject

class KeystoreKeysDataSource
    @Inject
    constructor(
        @UserKeyAlias private val userKeyAlias: String,
        private val keyStore: KeyStore,
        private val keyPairGenerator: KeyPairGenerator,
    ) : LocalKeysDataSource {
        override fun generateKeys() {
            keyPairGenerator.generateKeyPair()
        }

        override fun getPublicKey(): PublicKey = (keyStore.getEntry(userKeyAlias, null) as PrivateKeyEntry).certificate.publicKey

        override fun getPrivateKey(): PrivateKey = (keyStore.getEntry(userKeyAlias, null) as PrivateKeyEntry).privateKey

        override fun deleteKeys() {
            keyStore.deleteEntry(userKeyAlias)
        }
    }
