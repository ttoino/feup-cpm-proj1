package pt.up.fe.cpm.tiktek.core.data.local

import pt.up.fe.cpm.tiktek.core.data.KeysRepository
import pt.up.fe.cpm.tiktek.core.data.work.Deletable
import pt.up.fe.cpm.tiktek.core.local.LocalKeysDataSource
import java.security.PrivateKey
import java.security.PublicKey
import javax.inject.Inject

class LocalKeysRepository
    @Inject
    constructor(
        private val dataSource: LocalKeysDataSource,
    ) : KeysRepository, Deletable {
        override val publicKey: PublicKey
            get() = dataSource.getPublicKey()

        override val privateKey: PrivateKey
            get() = dataSource.getPrivateKey()

        override fun generateKeys() {
            dataSource.generateKeys()
        }

        override suspend fun delete() {
            dataSource.deleteKeys()
        }
    }
