package com.cinemo.metarbrowser.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.cinemo.metarbrowser.db.entity.Info;

public class DiffCallbackItem extends DiffUtil.ItemCallback<Info> {

    @Override
    public boolean areItemsTheSame(@NonNull Info oldItem, @NonNull Info newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Info oldItem, @NonNull Info newItem) {
        return oldItem.equals(newItem);
    }
}
