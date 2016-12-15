package org.numisoft.eurocoinsii.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.numisoft.eurocoinsii.R;
import org.numisoft.eurocoinsii.fragments.BasicFragment;
import org.numisoft.eurocoinsii.models.Coin;
import org.numisoft.eurocoinsii.models.CoinDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kukolka on 14.08.16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Coin> coins = new ArrayList<>();
    private String country;
    private int year;

    public interface OnDataClickListener {
        void onDataClick(Coin coin, int position);
    }

    private OnDataClickListener mOnDataClickListener;

    public MyAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.mOnDataClickListener = (OnDataClickListener) fragment;
        country = ((BasicFragment) fragment).getCountry() == null ?
                "Germany" : ((BasicFragment) fragment).getCountry();
        year = ((BasicFragment) fragment).getYear() == 0 ?
                2000 : ((BasicFragment) fragment).getYear();


        coins = new CoinDao(context).getAllCoins(country, year);

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {

        int imageResource = context.getResources().getIdentifier(
                coins.get(position).getImageId(), "drawable", context.getPackageName());

        holder.ivCoin.setImageResource(imageResource);
        holder.tvName.setText(coins.get(position).getName());
        holder.tvYear.setText(Integer.toString(coins.get(position).getYear()));
        holder.tvMark.setText(coins.get(position).getMark());
        holder.tvUnc.setText(Integer.toString(coins.get(position).getUnc()));
        holder.tvAUnc.setText("AU: " + Integer.toString(coins.get(position).getAUnc()));
        holder.tvFine.setText("F: " + Integer.toString(coins.get(position).getFine()));
        holder.tvGood.setText("G: " + Integer.toString(coins.get(position).getGood()));

        if (coins.get(position).getUnc() + coins.get(position).getAUnc()
                + coins.get(position).getFine() + coins.get(position).getGood() > 0)
            holder.rlHolder.setBackground(context.getDrawable(R.drawable.background));
        else holder.rlHolder.setBackground(context.getDrawable(R.drawable.background_zero));

        if (coins.get(position).getUnc() == 0) holder.tvUnc.setTextSize(0);
        else holder.tvUnc.setTextSize(14);

        if (coins.get(position).getAUnc() == 0) holder.tvAUnc.setTextSize(0);
        else holder.tvAUnc.setTextSize(10);

        if (coins.get(position).getFine() == 0) holder.tvFine.setTextSize(0);
        else holder.tvFine.setTextSize(10);

        if (coins.get(position).getGood() == 0) holder.tvGood.setTextSize(0);
        else holder.tvGood.setTextSize(10);

        holder.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDataClickListener.onDataClick(coins.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivCoin;
        TextView tvName;
        TextView tvYear;
        TextView tvMark;
        TextView tvUnc, tvAUnc, tvFine, tvGood;
        RelativeLayout rlHolder;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCoin = (ImageView) itemView.findViewById(R.id.ivCoin);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            tvMark = (TextView) itemView.findViewById(R.id.tvMark);
            tvUnc = (TextView) itemView.findViewById(R.id.tvUnc);
            tvAUnc = (TextView) itemView.findViewById(R.id.tvAUnc);
            tvFine = (TextView) itemView.findViewById(R.id.tvFine);
            tvGood = (TextView) itemView.findViewById(R.id.tvGood);
            rlHolder = (RelativeLayout) itemView.findViewById(R.id.rlHolder);
//            rlHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
