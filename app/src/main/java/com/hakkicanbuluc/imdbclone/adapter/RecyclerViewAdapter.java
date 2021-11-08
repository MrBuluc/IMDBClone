package com.hakkicanbuluc.imdbclone.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.model.OpenModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private ArrayList<OpenModel> openModels;

    private String[] colors = {"#a3ff00","#ff00aa","#b4a7d6","#a4c2f4","#8ee5ee","#cd950c",
            "#bf00ff","#f47932"};

    private static ClickListener clickListener;

    public RecyclerViewAdapter(ArrayList<OpenModel> openModels) {
        this.openModels = openModels;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(openModels.get(position), colors, position);
    }

    @Override
    public int getItemCount() {
        return openModels.size();
    }

    public void setClickListener(ClickListener clickListener) {
        RecyclerViewAdapter.clickListener = clickListener;
    }

    public class RowHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView textTitle, textYear;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(OpenModel openModel, String[] colors, Integer index) {
            itemView.setBackgroundColor(Color.parseColor(colors[index % colors.length]));
            textTitle = itemView.findViewById(R.id.text_title);
            textYear = itemView.findViewById(R.id.text_year);
            textTitle.setText(openModel.getTitle());
            textYear.setText(openModel.getReleased());
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getBindingAdapterPosition(), view);
        }
    }

    public interface ClickListener {
        void onItemClick(int index, View view);
    }
}
