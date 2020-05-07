package com.fidisys.android.mvi.shared.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.fidisys.android.mvi.shared.network.PostApi
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class PostStoreFactory(
    storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val api : PostApi
) : PostStoreAbstractFactory(storeFactory) {

    override fun createExecutor(): Executor<PostStore.Intent,
            Unit, PostStore.State, Result, PostStore.Label> =
        ExecutorImpl()

    private inner class ExecutorImpl :
        SuspendExecutor<PostStore.Intent, Unit, PostStore.State, Result, PostStore.Label>(
            mainContext = mainContext
        ) {
        override suspend fun executeIntent(
            intent: PostStore.Intent,
            getState: () -> PostStore.State
        ) =
            when (intent) {
                is PostStore.Intent.GetPost -> handleGetPost()
            }.let { }


        private suspend fun handleGetPost() {
            val result = withContext(ioContext) {
                return@withContext api.getPost()
            }
            dispatch(Result.Loaded(result))
            publish(PostStore.Label.Dispatched(result.id.toString()))
        }
    }
}