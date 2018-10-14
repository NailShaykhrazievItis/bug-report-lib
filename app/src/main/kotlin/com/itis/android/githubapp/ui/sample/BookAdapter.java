package com.itis.android.githubapp.ui.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.githubapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Nail Shaykhraziev on 04.10.2018.
 */
public class BookAdapter extends ListAdapter<Book, BookAdapter.BookHolder> {

    protected BookAdapter(@NonNull DiffUtil.ItemCallback<Book> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new BookHolder(inflater.inflate(R.layout.activity_main, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder bookHolder, int i) {
        bookHolder.bind(getItem(i));
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void submitList(@Nullable List<Book> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    class BookHolder extends RecyclerView.ViewHolder {

        public BookHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Book book) {

        }
    }
}
