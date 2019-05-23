package com.itis.testhelper.ui.settings.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.testhelper.R
import com.itis.testhelper.model.Repository
import com.itis.testhelper.utils.DEFAULT_DATE_FORMAT
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repositry.*
import java.text.SimpleDateFormat
import java.util.*

class ReposAdapter(
        private val repoClickLambda: (Repository) -> Unit
) : ListAdapter<Repository, ReposAdapter.RepoHolder>(object : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem == newItem
}
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repositry, parent, false)
        return RepoHolder(view)
    }

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.bind(getItem(position), repoClickLambda)
    }

    override fun submitList(list: List<Repository>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    class RepoHolder(
            override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(repo: Repository, repoClickLambda: (Repository) -> Unit) {
            tv_name.text = repo.name
            if (repo.description.isNullOrEmpty()) {
                tv_desc.visibility = View.GONE
            } else {
                tv_desc.text = repo.description
                tv_desc.visibility = View.VISIBLE
            }
            if (repo.language.isNullOrBlank()) {
                tv_language.visibility = View.GONE
            } else {
                tv_language.text = repo.language
                tv_language.visibility = View.VISIBLE
            }
            tv_last_update.text = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault()).format(repo.updatedAt)
            containerView.setOnClickListener {
                repoClickLambda(repo)
            }
        }
    }
}
