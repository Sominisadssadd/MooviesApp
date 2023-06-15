package com.example.mooviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolderTrailers> {

    private List<Trailer> trailers = new ArrayList<>();

    private onTrailerClickListener onTrailerClickListener;

    public void setOnTrailerClickListener(onTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderTrailers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false);

        return new ViewHolderTrailers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTrailers holder, int position) {

        Trailer currentTrailer = trailers.get(position);
        holder.textViewNameOfTrailers.setText(currentTrailer.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTrailerClickListener.onTrailerClick(currentTrailer);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public static class ViewHolderTrailers extends RecyclerView.ViewHolder {

        private TextView textViewNameOfTrailers;

        public ViewHolderTrailers(@NonNull View itemView) {
            super(itemView);
            textViewNameOfTrailers = itemView.findViewById(R.id.textViewTrailerName);
        }
    }

    interface onTrailerClickListener {
        void onTrailerClick(Trailer trailer);
    }

}
