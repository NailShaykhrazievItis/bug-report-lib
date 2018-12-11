package com.itis.android.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.itis.android.githubapp.R
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.ui.adapters.holder.RepoHolder

class ReposAdapter : ListAdapter<Repository, RepoHolder>(object : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem == newItem
}
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return RepoHolder(view)
    }

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
