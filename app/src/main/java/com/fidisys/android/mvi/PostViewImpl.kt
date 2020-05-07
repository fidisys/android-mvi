package com.fidisys.android.mvi

import android.util.Log
import android.view.View
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.fidisys.android.mvi.shared.view.PostView
import com.fidisys.android.mvi.shared.view.PostView.Event
import com.fidisys.android.mvi.shared.view.PostView.Model
import kotlinx.android.synthetic.main.activity_main.view.*

class PostViewImpl(val root: View) : BaseMviView<Model, Event>(), PostView {

    init {
        root.get_post_button.setOnClickListener {
            dispatch(Event.GetPost(id = 1))
        }
    }

    override fun render(model: Model) {
        Log.d("post received", model.data.toString())
        if (model.data!!.isEmpty()) {
            root.post_description.text = "Nothing to show"
        }else {
            root.post_description.text = model.data.toString()
        }
    }
}