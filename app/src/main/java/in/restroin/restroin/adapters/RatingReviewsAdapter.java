package in.restroin.restroin.adapters;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.restroin.restroin.R;
import in.restroin.restroin.models.ReviewsRatingModel;

public class RatingReviewsAdapter extends RecyclerView.Adapter<RatingReviewsAdapter.ViewHolder> {
    private List<ReviewsRatingModel> reviewsRatingModels;

    public RatingReviewsAdapter(List<ReviewsRatingModel> reviewsRatingModels) {
        this.reviewsRatingModels = reviewsRatingModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rating_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Typeface raleway = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "font/raleway.ttf");
        holder.review.setText("\"" + reviewsRatingModels.get(position).getReview() + "\"");
        holder.review.setTypeface(raleway);
        holder.reviewer.setText(reviewsRatingModels.get(position).getReviewer());
        holder.reviewer.setTypeface(raleway);
        holder.reviews_rating.setRating(Float.parseFloat(reviewsRatingModels.get(position).getRating()));
        Uri image = Uri.parse("https://www.restroin.in/developers/api/images/" + reviewsRatingModels.get(position).getImage());
        Picasso.get().load(image).into(holder.profile_image);
    }

    @Override
    public int getItemCount() {
        return reviewsRatingModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView reviewer, review;
        RatingBar reviews_rating;
        public ViewHolder(View itemView) {
            super(itemView);
            profile_image = (CircleImageView) itemView.findViewById(R.id.reviews_profile_image);
            review = (TextView) itemView.findViewById(R.id.review);
            reviewer = (TextView) itemView.findViewById(R.id.reviewer);
            reviews_rating = (RatingBar) itemView.findViewById(R.id.reviews_rating);
        }
    }
}
