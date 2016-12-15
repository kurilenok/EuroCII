package org.numisoft.eurocoinsii.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.numisoft.eurocoinsii.R;
import org.numisoft.eurocoinsii.adapters.MyAdapter;
import org.numisoft.eurocoinsii.models.Coin;
import org.numisoft.eurocoinsii.models.Theme;
import org.numisoft.eurocoinsii.utils.UpdateHelper;

/**
 * Created by kukolka on 22.08.16.
 */
@SuppressLint("ValidFragment")
public class BasicFragment extends Fragment implements MyAdapter.OnDataClickListener {

    String country;
    int year;
    View view;
    RecyclerView rvMain;
    MyAdapter myAdapter;
    int clicked;
    RecyclerView.LayoutManager layoutManager;

    public BasicFragment() {
    }

    public BasicFragment(String country, int year) {
        this.country = country;
        this.year = year;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.all_fragment, container, false);
        setRetainInstance(true);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        layoutManager = new GridLayoutManager(view.getContext(), (int) (dpWidth / 160));

        rvMain = (RecyclerView) view.findViewById(R.id.rvMain);
        rvMain.setLayoutManager(layoutManager);

        setNewAdapter();

        return view;
    }

    private void setNewAdapter() {
        myAdapter = new MyAdapter(view.getContext(), this);
        rvMain.setAdapter(myAdapter);
    }

    public void doSomething(Coin coin) {
        myAdapter.getCoins().set(clicked, coin);
        myAdapter.notifyItemChanged(clicked);
        myAdapter.notifyDataSetChanged();
        UpdateHelper.setNeedsUpdate(true);
    }

    @Override
    public void onDataClick(Coin coin, int position) {
        this.clicked = position;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        PopupFragment popup = PopupFragment.getInstance(coin);
        popup.setTargetFragment(this, 0);
        popup.show(manager, "1");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            if (isVisibleToUser & UpdateHelper.isNeedsUpdate()) {
            try {
                setNewAdapter();
                UpdateHelper.setNeedsUpdate(false);
            } catch (Exception e) {
            }
        }
//        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        layoutManager = new GridLayoutManager(view.getContext(), (int) (dpWidth / 160));
        rvMain.setLayoutManager(layoutManager);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
