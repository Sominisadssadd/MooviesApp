package com.example.mooviesapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String POSITIVE_REVIEW = "Позитивный";
    private static final String NEUTRAL_REVIEW = "Нейтральный";

    private onHalfReachListener onHalfReachListener;

    public void setOnHalfReachListener(onHalfReachListener onHalfReachListener) {
        this.onHalfReachListener = onHalfReachListener;
    }

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Review currentReview = reviews.get(position);
        int backgroundId;

        try {
            if (currentReview.getType().equals(POSITIVE_REVIEW)) {
                backgroundId = R.color.green;
            } else if (currentReview.getType().equals(NEUTRAL_REVIEW)) {
                backgroundId = R.color.yellow;
            } else {
                backgroundId = R.color.red;
            }
        } catch (Exception e) {
            backgroundId = R.color.yellow;
        }

        int background_of_card = ContextCompat.getColor(holder.itemView.getContext(), backgroundId);
        holder.cardViewContainer.setCardBackgroundColor(background_of_card);
        holder.textViewReviewText.setText(currentReview.getReview());
        holder.textViewNameOfReviewer.setText(currentReview.getAuthor());

        if(position == reviews.size()-5){
            onHalfReachListener.onHalfReachListener();
        }

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    interface onHalfReachListener {
        void onHalfReachListener();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNameOfReviewer;
        public TextView textViewReviewText;
        public CardView cardViewContainer;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameOfReviewer = itemView.findViewById(R.id.textViewNameOfReviewer);
            textViewReviewText = itemView.findViewById(R.id.textViewReviewText);
            cardViewContainer = itemView.findViewById(R.id.cardContainer);
        }
    }
}
