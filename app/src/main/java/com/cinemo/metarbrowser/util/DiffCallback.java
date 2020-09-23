package com.cinemo.metarbrowser.util;

import androidx.recyclerview.widget.DiffUtil;

import com.cinemo.metarbrowser.db.entity.Info;

import java.util.List;

public class DiffCallback extends DiffUtil.Callback {

    private List<Info> oldList;
    private List<Info> newList;

    public DiffCallback(List<Info> oldList, List<Info> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
