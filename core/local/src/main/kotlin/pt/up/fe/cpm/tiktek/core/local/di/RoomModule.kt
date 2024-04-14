package pt.up.fe.cpm.tiktek.core.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pt.up.fe.cpm.tiktek.core.local.room.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {
    @Provides
    @Singleton
    internal fun provideDatabase(
        @ApplicationContext context: Context,
    ): Database =
        Room.databaseBuilder(
            context,
            Database::class.java,
            "tiktek",
        ).build()
}
