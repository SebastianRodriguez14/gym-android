package com.tecfit.gym_android.fragments.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Comment
import org.w3c.dom.Text

class CommentViewHolder (val view:View):RecyclerView.ViewHolder(view) {

    val cm_image = view.findViewById<ImageView>(R.id.item_comment_image)
    val cm_username = view.findViewById<TextView>(R.id.item_comment_name)
    val cm_content = view.findViewById<TextView>(R.id.item_comment_description)

    fun render(comment:Comment){

        if (comment.user.image == null){
            cm_image.setImageResource(R.drawable.profile_user_image_default)
        } else {
            Glide.with(view).load(comment.user.image!!.url).into(cm_image)
        }

        cm_username.text = "${comment.user.name} ${comment.user.lastname}"
        cm_content.text = comment.content
    }

}