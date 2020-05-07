package com.fidisys.android.mvi.shared.store

import Post
import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.core.utils.JvmSerializable
import com.badoo.reaktive.utils.ensureNeverFrozen
import emptyPost

abstract class PostStoreAbstractFactory(private val storeFactory: StoreFactory) {

    fun create(): PostStore =
        object : PostStore, Store<PostStore.Intent, PostStore.State, PostStore.Label>
        by storeFactory.create(
            name = "GetPostStore",
            initialState = PostStore.State(emptyPost(), false),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::createExecutor,
            reducer = ReducerImpl
        ) {
            init {
                ensureNeverFrozen()
            }
        }

    protected abstract fun createExecutor(): Executor<PostStore.Intent, Unit, PostStore.State, Result, PostStore.Label>

    protected sealed class Result : JvmSerializable {
        data class Loaded(val data: Post) : Result()
        object Finished : Result()
    }

    private object ReducerImpl : Reducer<PostStore.State, Result> {
        override fun PostStore.State.reduce(result: Result): PostStore.State =
            when (result) {
                is Result.Loaded -> copy(data = result.data, isFinished = true)
                is Result.Finished -> copy(isFinished = true)
            }
    }
}