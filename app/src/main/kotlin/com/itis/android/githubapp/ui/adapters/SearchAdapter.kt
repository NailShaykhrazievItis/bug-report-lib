package com.itis.android.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.itis.android.githubapp.R
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.ui.adapters.holder.SearchHolder

class SearchAdapter(private val glideManager: RequestManager) : ListAdapter<Repository, SearchHolder>(
        object : DiffUtil.ItemCallback<Repository>() {

            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean = oldItem == newItem
        }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_repo, parent, false)
        return SearchHolder(view, glideManager)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
