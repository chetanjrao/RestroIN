package in.restroin.restroin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Rating;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import in.restroin.restroin.adapters.AmenitiesGridAdapter;
import in.restroin.restroin.adapters.CouponsSelectAdapter;
import in.restroin.restroin.adapters.CuisinesGridAdapter;
import in.restroin.restroin.adapters.CusinesGridAdapter;
import in.restroin.restroin.adapters.DishesAdapter;
import in.restroin.restroin.adapters.MenuImagesAdapter;
import in.restroin.restroin.adapters.RatingReviewsAdapter;
import in.restroin.restroin.adapters.RestaurantImageAdapter;
import in.restroin.restroin.interfaces.RestaurantClient;
import in.restroin.restroin.interfaces.RestroINAuthClient;
import in.restroin.restroin.models.CouponModel;
import in.restroin.restroin.models.MessageModel;
import in.restroin.restroin.models.RestaurantDishesModel;
import in.restroin.restroin.models.RestaurantModel;
import in.restroin.restroin.utils.DishesDeserializer;
import in.restroin.restroin.utils.MyGridView;
import in.restroin.restroin.utils.MySpannable;
import in.restroin.restroin.utils.SaveSharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class RestaurantViewActivity extends FragmentActivity implements OnMapReadyCallback, View.OnTouchListener {
    private GoogleMap mMap;
    private PopupWindow popupWindow;

    private final static String RETROFIT_TAG = "RETROFIT_LOG";
    private static final int[] COLORS = {
            rgb("#c70000"), rgb("#e23100"), rgb("#ff9500"), rgb("#88ff00"), rgb("#26ca02")
    };


    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://www.restroin.in/")
            .addConverterFactory(GsonConverterFactory.create());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.restaurant_full_view);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ProgressBar imagesProgressBar = (ProgressBar) findViewById(R.id.images_progress_bar);
        relativeLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        imagesProgressBar.setVisibility(View.VISIBLE);
        //final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /********************** Maps *****************************/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.restaurant_map);
        mapFragment.getMapAsync(this);

        /********************* Maps end *************************/

    }

    public void showDatesRecycler(final RecyclerView recyclerView, Context context){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final String id = getIntent().getStringExtra("restaurant_id");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(RestaurantViewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView images_recycler = (RecyclerView) findViewById(R.id.restaurant_images);
        final MyGridView myGridView = (MyGridView) findViewById(R.id.features_gridView);
        images_recycler.setLayoutManager(layoutManager);
        final Retrofit restaurant_retrofit = builder.build();
        Typeface raleway = Typeface.createFromAsset(getAssets(),
                "font/raleway.ttf");
        final TextView restaurant_name = (TextView) findViewById(R.id.restaurant_name);
        restaurant_name.setTypeface(raleway);
        final TextView restaurant_city = (TextView) findViewById(R.id.restaurant_city);
        restaurant_city.setTypeface(raleway);
        final TextView restaurant_address = (TextView) findViewById(R.id.address_of_restaurant);
        restaurant_address.setTypeface(raleway);
        final TextView restaurant_timing = (TextView) findViewById(R.id.timing_of_restaurant);
        restaurant_timing.setTypeface(raleway);
        final RatingBar restaurant_rating = (RatingBar) findViewById(R.id.restaurant_rating);
        final TextView restaurant_about = (TextView) findViewById(R.id.about_restaurant);
        restaurant_about.setTypeface(raleway);
        final TextView cost_for_two = (TextView) findViewById(R.id.cost_for_two);
        cost_for_two.setTypeface(raleway);
        final TextView menu_images_count = (TextView) findViewById(R.id.menu_images_count);
        menu_images_count.setTypeface(raleway);
        final TextView restaurant_image_count = (TextView) findViewById(R.id.restaurant_images_count);
        restaurant_image_count.setTypeface(raleway);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(images_recycler);
        final MyGridView cuisines_grid_view = (MyGridView) findViewById(R.id.cusines_gridView_special);
        RestaurantClient client = restaurant_retrofit.create(RestaurantClient.class);
        final ImageView menu_images_header = (ImageView) findViewById(R.id.main_image_of_menu);
        final ImageView restaurant_image_header = (ImageView) findViewById(R.id.main_image_of_restaurant);
        Call<RestaurantModel> call = client.getRestaurantData(id);
        final RecyclerView popular_dishes_recycler_view = (RecyclerView) findViewById(R.id.popular_dishes_recycler);
        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popular_dishes_recycler_view.setLayoutManager(linearLayoutManager1);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurantViewActivity.this, LinearLayoutManager.VERTICAL, false);
        final RecyclerView specialCouponsRecyclerView = (RecyclerView) findViewById(R.id.special_coupons_recyclerView);
        specialCouponsRecyclerView.setNestedScrollingEnabled(false);
        specialCouponsRecyclerView.setLayoutManager(linearLayoutManager);
        final RecyclerView ratings_reviews_recycler_view = (RecyclerView) findViewById(R.id.ratings_reviews_recycler);
        final LinearLayoutManager ratingLayoutManager = new LinearLayoutManager(RestaurantViewActivity.this, LinearLayoutManager.VERTICAL, false);
        call.enqueue(new Callback<RestaurantModel>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantModel> call,@NonNull final Response<RestaurantModel> response) {
                if(response.isSuccessful()){
                    List<String> restaurant_images = response.body().getFront_image();
                    List<String> restaurant_features = response.body().getRestaurant_features_amenities();
                    restaurant_name.setText(response.body().getRestaurant_name());
                    restaurant_city.setText(response.body().getCity_id() + ", " + response.body().getRestaurant_region() + " Bangalore");
                    restaurant_address.setText(" " +response.body().getRestaurant_address());
                    cost_for_two.setText(" \u20B9 " +response.body().getPrice_for_two() + "/- (for 2 people approx.)");
                    List<String> menu_image = response.body().getMenu_image();
                    menu_images_count.setText(menu_image.size() + " images");
                    restaurant_image_count.setText(restaurant_images.size() + " images");
                    final ArrayList<CouponModel> special_coupons = response.body().getRestaurant_coupon_selected();
                    final CouponsSelectAdapter couponsSelectAdapter = new CouponsSelectAdapter(special_coupons, RestaurantViewActivity.this);
                    if(response.body().getPopular_dishes() != null && response.body().getPopular_dishes().size() > 0){
                        DishesAdapter dishesAdapter = new DishesAdapter(response.body().getPopular_dishes(), RestaurantViewActivity.this);
                        popular_dishes_recycler_view.setAdapter(dishesAdapter);
                    } else {
                        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.popular_food_dishes_layout);
                        relativeLayout.setVisibility(View.GONE);
                    }//couponsSelectLayout
                    if(response.body().getRestaurant_coupon_selected() != null && response.body().getRestaurant_coupon_selected().size() > 0){
                        CouponsSelectAdapter couponsSelectAdapter_ = new CouponsSelectAdapter(special_coupons, RestaurantViewActivity.this);
                        specialCouponsRecyclerView.setAdapter(couponsSelectAdapter_);
                    } else {
                        CardView card = (CardView) findViewById(R.id.couponsSelectLayout);
                        card.setVisibility(View.GONE);
                    }
                    if(response.body().getReviews() != null && response.body().getReviews().size() > 0){
                        RatingReviewsAdapter adapter_reviews = new RatingReviewsAdapter(response.body().getReviews());
                        ratings_reviews_recycler_view.setLayoutManager(ratingLayoutManager);
                        ratings_reviews_recycler_view.setAdapter(adapter_reviews);
                    } else {
                        RelativeLayout main_rating_layout = (RelativeLayout) findViewById(R.id.customer_reviews_layout);
                        main_rating_layout.setVisibility(View.GONE);
                    }
                    if (restaurant_images.size() > 0){
                        Uri restaurant_header_image = Uri.parse("https://www.restroin.in/" + restaurant_images.get(0));
                        Picasso.get().load(restaurant_header_image).into(restaurant_image_header);
                    } if(menu_image.size() > 0) {
                        Uri menu_header_image = Uri.parse("https://www.restroin.in/" + menu_image.get(0));
                        Picasso.get().load(menu_header_image).into(menu_images_header);
                    }
                    Button button = (Button) findViewById(R.id.book_now_button);
                    Typeface raleway = Typeface.createFromAsset(getAssets(),
                            "font/raleway.ttf");
                    button.setTypeface(raleway);
                    LatLng restaurant_location = new LatLng(Double.valueOf(response.body().getRestaurant_lat()), Double.valueOf(response.body().getRestaurant_lng()));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(10);
                    mMap.addMarker(new MarkerOptions().position(restaurant_location).title("Location of: " + response.body().getRestaurant_name()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant_location));
                    mMap.animateCamera(cameraUpdate);
                    CuisinesGridAdapter cuisines_adapter = new CuisinesGridAdapter(response.body().getRestaurant_cuisines_available(), RestaurantViewActivity.this);
                    cuisines_grid_view.setAdapter(cuisines_adapter);
                    restaurant_about.setText(response.body().getRestaurant_about());
                    makeTextViewResizable(restaurant_about, 3, "View More..", true);
                    restaurant_rating.setRating(Float.parseFloat(response.body().getRestaurant_rating()));
                    restaurant_timing.setText(" " + response.body().getRestaurant_opening_time() + " - " + response.body().getRestaurant_closing_time());
                    AmenitiesGridAdapter amenitiesGridAdapter = new AmenitiesGridAdapter(restaurant_features,RestaurantViewActivity.this);
                    myGridView.setAdapter(amenitiesGridAdapter);
                    RestaurantImageAdapter adapter = new RestaurantImageAdapter(restaurant_images);
                    images_recycler.setAdapter(adapter);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RestaurantViewActivity.this, BookingActivity.class);
                            intent.putExtra("restaurant_id", id);
                            String couponSelected = special_coupons.get(couponsSelectAdapter.getCheckedCoupon()).getCoupon_code();
                            intent.putExtra("couponSelected", couponSelected);
                            intent.putExtra("restaurant_closing_time", response.body().getRestaurant_closing_time());
                            intent.putExtra("restaurant_opening_time", response.body().getRestaurant_opening_time());
                            startActivity(intent);
                        }
                    });
                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.restaurant_full_view);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    ProgressBar imagesProgressBar = (ProgressBar) findViewById(R.id.images_progress_bar);
                    relativeLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    imagesProgressBar.setVisibility(View.GONE);
                    final View popupContent = getLayoutInflater().inflate(R.layout.layout_reviews_rating_popup, null);
                    popupWindow = new PopupWindow();
                    popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setContentView(popupContent);
                    popupWindow.setTouchInterceptor(RestaurantViewActivity.this);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setAnimationStyle(R.style.PopupAnimation);
                    final ProgressBar popUpProgressBar = (ProgressBar) popupContent.findViewById(R.id.reviewsProgressBar);
                    popUpProgressBar.setVisibility(View.GONE);
                    final RelativeLayout reviewsRelativeLayout = (RelativeLayout) popupContent.findViewById(R.id.review_layout_full);
                    reviewsRelativeLayout.setVisibility(View.VISIBLE);
                    popupContent.findViewById(R.id.popup_exit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    Button write_review_button = (Button) findViewById(R.id.write_review_button);
                    write_review_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(new SaveSharedPreferences().getAccess_token(RestaurantViewActivity.this) != null){
                                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                                popUpProgressBar.setVisibility(View.GONE);
                                reviewsRelativeLayout.setVisibility(View.VISIBLE);
                                Button review_submit_button = (Button) popupContent.findViewById(R.id.review_submit_button);
                                review_submit_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextInputEditText review = (TextInputEditText) popupContent.findViewById(R.id.review_edit_text);
                                        RatingBar review_rating = (RatingBar) popupContent.findViewById(R.id.rating_bar);
                                        if(!TextUtils.isEmpty(review.getText().toString())){
                                            if(review_rating.getRating() != 0){
                                                popUpProgressBar.setVisibility(View.VISIBLE);
                                                reviewsRelativeLayout.setVisibility(View.GONE);
                                                RestroINAuthClient inAuthClient = restaurant_retrofit.create(RestroINAuthClient.class);
                                                SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
                                                Call<MessageModel> reviewModel = inAuthClient.submitReview("Bearer " + saveSharedPreferences.getAccess_token(RestaurantViewActivity.this), id, saveSharedPreferences.getUser_id(RestaurantViewActivity.this), String.valueOf(review_rating.getRating()), review.getText().toString());
                                                reviewModel.enqueue(new Callback<MessageModel>() {
                                                    @Override
                                                    public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                                                        if (Integer.parseInt(response.body().getStatus()) == 200){
                                                            Toast.makeText(RestaurantViewActivity.this, "Thank You for your Review", Toast.LENGTH_SHORT).show();
                                                            popupWindow.dismiss();
                                                        } else {
                                                            Toast.makeText(RestaurantViewActivity.this, "Something Went Wrong. Try again", Toast.LENGTH_SHORT).show();
                                                            popUpProgressBar.setVisibility(View.GONE);
                                                            reviewsRelativeLayout.setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<MessageModel> call, Throwable t) {
                                                        Toast.makeText(RestaurantViewActivity.this, "", Toast.LENGTH_SHORT).show();
                                                        popUpProgressBar.setVisibility(View.GONE);
                                                        reviewsRelativeLayout.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(RestaurantViewActivity.this, "Please provide some rating", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(RestaurantViewActivity.this, "Please write a review", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Intent goToLogin = new Intent(RestaurantViewActivity.this, LoginActivity.class);
                                startActivity(goToLogin);
                            }
                        }
                    });
                } else {
                    Toast.makeText(RestaurantViewActivity.this, "Something Went Wrong: " + response.message(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantModel> call,@NonNull Throwable t) {
                Toast.makeText(RestaurantViewActivity.this, "Something Went Wrong. Check your internet connection", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. View More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public void checkAccessTokenStatus(){
        SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
        String access_token = saveSharedPreferences.getAccess_token(RestaurantViewActivity.this);
        if(access_token == null){
            Intent goToLogin = new Intent(RestaurantViewActivity.this, LoginActivity.class);
            startActivity(goToLogin);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
