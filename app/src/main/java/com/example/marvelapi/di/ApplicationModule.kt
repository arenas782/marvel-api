package com.example.marvelapi.di


import android.content.Context
import androidx.databinding.ktx.BuildConfig
import androidx.room.Room
import com.example.marvelapi.data.api.CharactersService
import com.example.marvelapi.data.repository.CharactersRepository
import com.example.marvelapi.data.room.AppDatabase
import com.example.marvelapi.data.room.CharacterDao
import com.example.marvelapi.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {




    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit): CharactersService = retrofit.create(CharactersService::class.java)



    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context : Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDao(appDatabase: AppDatabase) : CharacterDao{
        return appDatabase.characterDao()
    }

    @Singleton
    @Provides
    fun provideMainRepository(db: AppDatabase, CharactersService: CharactersService) : CharactersRepository{
        return CharactersRepository(db,CharactersService)
    }


    @Provides
    fun provideBaseUrl() = Constants.BASE_URL
}