package com.diegopatrocinio.giphytrends.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.diegopatrocinio.giphytrends.R;
import com.diegopatrocinio.giphytrends.fragments.FavoriteFragment;
import com.diegopatrocinio.giphytrends.fragments.TrendingFragment;

public final class MainPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public MainPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int fragmentId) {
        switch (fragmentId) {
            case 0:
                return new TrendingFragment();
            case 1:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int fragmentId) {
        switch (fragmentId) {
            case 0:
                return mContext.getString(R.string.tab_trending);
            case 1:
                return mContext.getString(R.string.tab_favorite);
            default:
                return null;
        }
    }
}
