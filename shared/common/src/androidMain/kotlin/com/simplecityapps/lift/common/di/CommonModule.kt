package com.simplecityapps.lift.common.di

import com.simplecityapps.lift.common.DateSerializer
import com.simplecityapps.lift.common.utils.Dispatcher
import com.simplecityapps.lift.common.utils.LiftDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    fun provideDateSerializer(
        @Dispatcher(LiftDispatchers.Default) dispatcher: CoroutineDispatcher): DateSerializer {
        return DateSerializer(dispatcher)
    }
}