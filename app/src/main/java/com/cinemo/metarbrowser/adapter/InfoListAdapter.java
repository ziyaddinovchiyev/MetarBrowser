package com.cinemo.metarbrowser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cinemo.metarbrowser.R;
import com.cinemo.metarbrowser.db.entity.Info;
import com.cinemo.metarbrowser.util.ClickListener;
import com.cinemo.metarbrowser.util.DiffCallback;

import java.util.ArrayList;
import java.util.List;

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.ViewHolder> {

    private List<Info> data = new ArrayList<>();
    private ClickListener clickListener;

    public InfoListAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Info data = this.data.get(position);
        holder.id.setText(data.getId());
        holder.date.setText(data.getLastUpdated());
        holder.raw.setText(data.getRaw());
        holder.decode.setText(data.getDecode());
        if (data.isExpanded) {
            holder.decode.setVisibility(View.VISIBLE);
            holder.arrow.setImageResource(R.drawable.ic_up);
        } else {
            holder.decode.setVisibility(View.GONE);
            holder.arrow.setImageResource(R.drawable.ic_down);
        }
        holder.itemView.setOnClickListener(view -> {
            if (!data.isExpanded) {
                holder.decode.setVisibility(View.VISIBLE);
                holder.arrow.setImageResource(R.drawable.ic_up);
                data.isExpanded = true;
            } else {
                holder.decode.setVisibility(View.GONE);
                holder.arrow.setImageResource(R.drawable.ic_down);
                data.isExpanded = false;
            }
            notifyItemChanged(position);
        });

        holder.forceUpdate.setOnClickListener(view -> {
            clickListener.onItemClick(position, data);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<Info> update) {
        DiffCallback diffCallback = new DiffCallback(data, update);
        DiffUtil.DiffResult util = DiffUtil.calculateDiff(diffCallback);
        data.clear();
        data.addAll(update);
        util.dispatchUpdatesTo(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, raw, decode, date;
        ImageView arrow, forceUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            date = itemView.findViewById(R.id.date);
            raw = itemView.findViewById(R.id.raw);
            decode = itemView.findViewById(R.id.decode);
            arrow = itemView.findViewById(R.id.arrow);
            forceUpdate = itemView.findViewById(R.id.forceUpdate);
        }
    }
}
