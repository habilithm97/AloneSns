package com.example.alonesns;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alonesns.Model.MainModel;
import com.example.alonesns.Presenter.MainContract;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<MainModel> items;

    public HomeAdapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = (LayoutInflater.from(viewGroup.getContext())).inflate(R.layout.card_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        MainModel item = items.get(position);
        holder.contentTv.setText(item.getContent());
        holder.dateTv.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItem(List<MainModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentTv, dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contentTv = itemView.findViewById(R.id.contentTv);
            dateTv = itemView.findViewById(R.id.dateTv);
        }
    }
}