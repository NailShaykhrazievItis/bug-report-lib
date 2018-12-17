package com.itis.android.githubapp.ui.adapters.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.utils.constants.DEFAULT_DATE_FORMAT
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search_repo.*
import java.text.SimpleDateFormat
import java.util.*

class SearchHolder(
        override val containerView: View,
        private val glideManager: RequestManager
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(repo: Repository) {
        tv_name.text = repo.name
        if (repo.description.isNullOrEmpty()) {
            tv_desc.visibility = View.GONE
        } else {
            tv_desc.text = repo.description
            tv_desc.visibility = View.VISIBLE
        }
        tv_fork.text = repo.forksCount.toString()
        tv_stars.text = repo.stargazersCount.toString()
        tv_language.text = repo.language
        tv_last_update.text =
                SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(repo.updatedAt)
        glideManager.load(repo.owner.avatarUrl)
                .into(iv_avatar)
    }
}
