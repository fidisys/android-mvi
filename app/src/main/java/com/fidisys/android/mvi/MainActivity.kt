package com.fidisys.android.mvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.mvikotlin.androidxlifecycleinterop.asMviLifecycle
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.fidisys.android.mvi.shared.controllers.PostController
import com.fidisys.android.mvi.shared.controllers.PostCoroutinesController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var controllers: PostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postControllerDependencies = object : PostController.Dependencies {
            override val storeFactory: StoreFactory
                get() = storeFactoryInstance
            override val lifecycle: Lifecycle
                get() = this@MainActivity.lifecycle.asMviLifecycle()
        }

        controllers = PostCoroutinesController(dependencies = postControllerDependencies)

        controllers.onViewCreated(
            PostViewImpl(root = root_view),
            viewLifecycle = this@MainActivity.lifecycle.asMviLifecycle()
        )
    }
}
