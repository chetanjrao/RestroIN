package in.restroin.restroin.models;

public class ReviewsRatingModel {
    private String reviewer, review, image, rating;

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ReviewsRatingModel(String reviewer, String review, String image, String rating) {

        this.reviewer = reviewer;
        this.review = review;
        this.image = image;
        this.rating = rating;
    }
}
