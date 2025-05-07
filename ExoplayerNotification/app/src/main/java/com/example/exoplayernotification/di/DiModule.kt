package com.example.exoplayernotification.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.exoplayernotification.data.MediaController.MediaController
import com.example.exoplayernotification.data.RepImpl.AudioRepoImpl
import com.example.exoplayernotification.domain.Repository.AudioRepository
import com.example.exoplayernotification.domain.UseCases.GetAllSongUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Singleton
    @Provides
    fun provideAudioRepo(@ApplicationContext context: Context): AudioRepository {
        return AudioRepoImpl(context = context)
    }

    @Singleton
    @Provides
    fun GetAllSongUseCaseObj(audioRepository: AudioRepository): GetAllSongUseCase {
        return GetAllSongUseCase(getAllSongRepository = audioRepository)
    }

    @Singleton
    @Provides
    fun returnExoplayerObj(@ApplicationContext context: Context): ExoPlayer{
        return ExoPlayer.Builder(context).build()
    }

    @Singleton
    @Provides
    fun mediaControllerObj(@ApplicationContext context: Context,exoPlayer: ExoPlayer): MediaController{
        return MediaController(exoPlayer =exoPlayer, context = context )
    }
}