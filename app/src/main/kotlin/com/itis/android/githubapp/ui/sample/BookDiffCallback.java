package com.itis.android.githubapp.ui.sample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by Nail Shaykhraziev on 06.10.2018.
 */
public class BookDiffCallback extends DiffUtil.ItemCallback<Book> {

    @Override
    public boolean areItemsTheSame(@NonNull Book oldBook, @NonNull Book newBook) {
        return oldBook.getId() == newBook.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Book oldBook, @NonNull Book newBook) {
        return oldBook.equals(newBook);
    }

    @Nullable
    @Override
    public Object getChangePayload(@NonNull Book oldItem, @NonNull Book newItem) {
        return super.getChangePayload(oldItem, newItem);
    }
}
