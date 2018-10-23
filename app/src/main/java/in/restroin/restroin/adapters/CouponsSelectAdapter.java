package in.restroin.restroin.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import in.restroin.restroin.R;
import in.restroin.restroin.models.CouponModel;
import in.restroin.restroin.utils.SmoothCheckBox;

public class CouponsSelectAdapter extends RecyclerView.Adapter<CouponsSelectAdapter.ViewHolder> {
    private ArrayList<CouponModel> couponModels;
    private int checkedCouponPosition;
    private Context context;
    private int checkedCoupon;

    public CouponsSelectAdapter(ArrayList<CouponModel> couponModels, Context context) {
        this.couponModels = couponModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_coupons_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Typeface raleway = Typeface.createFromAsset(context.getAssets(), "font/raleway.ttf");
        holder.coupon_name.setTypeface(raleway);
        holder.coupon_name.setText(couponModels.get(position).getCoupon_code());
        holder.coupon_description.setTypeface(raleway);
        holder.checkBox.setChecked(false);
        holder.coupon_description.setText(couponModels.get(position).getDescription());
        if (checkedCouponPosition == position){
            holder.checkBox.animate();
            holder.changingBackgroundView.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
            holder.checkBox.setChecked(true);
        } else {
            holder.changingBackgroundView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            holder.checkBox.animate();
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        int size;
        if (couponModels != null){
            size =  couponModels.size();
        } else {
            size = 0;
        }

        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView coupon_name, coupon_description;
        View changingBackgroundView;
        SmoothCheckBox checkBox;
        RelativeLayout coupon_layout;
        public ViewHolder(final View itemView) {
            super(itemView);
            checkedCoupon = checkedCouponPosition;
            coupon_name = (TextView) itemView.findViewById(R.id.coupon_name);
            coupon_description = (TextView) itemView.findViewById(R.id.coupon_description);
            changingBackgroundView = (View) itemView.findViewById(R.id.changing_backgroundView);
            checkBox = (SmoothCheckBox) itemView.findViewById(R.id.coupon_checkbox);
            coupon_layout = (RelativeLayout) itemView.findViewById(R.id.coupon_layout);
            coupon_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.animate();
                    checkedCouponPosition = getAdapterPosition();
                    checkedCoupon = checkedCouponPosition;
                    notifyDataSetChanged();
                }
            });
        }
    }

    public int getCheckedCoupon() {
        checkedCoupon = checkedCouponPosition;
        return this.checkedCoupon;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
