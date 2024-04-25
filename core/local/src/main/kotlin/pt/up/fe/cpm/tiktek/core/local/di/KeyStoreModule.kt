package pt.up.fe.cpm.tiktek.core.local.di

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class UserKeyAlias

@Module
@InstallIn(SingletonComponent::class)
internal class KeyStoreModule {
    @Provides
    @UserKeyAlias
    internal fun provideUserKeyAlias() = "userKey"

    @Provides
    @Singleton
    internal fun provideKeyStore(): KeyStore =
        KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

    @Provides
    @Singleton
    internal fun provideKeyPairGenerator(
        @UserKeyAlias userKeyAlias: String,
    ): KeyPairGenerator =
        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore").apply {
            initialize(
                KeyGenParameterSpec.Builder(
                    userKeyAlias,
                    KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY,
                )
                    .setKeySize(4096)
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                    .build(),
            )
        }
}
