package com.example.home.weddingapp.Others;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.Fragments.PadrinhosFragment;
import com.example.home.weddingapp.Fragments.Tab2Fragment;
import com.example.home.weddingapp.R;

/**
 * Created by Home on 15/09/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter implements ViewPager.PageTransformer {

    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    private MyLinearLayout cur = null;
    private MyLinearLayout next = null;
    private MainActivity context;
    private float scale;

    public PagerAdapter(MainActivity context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == Tab2Fragment.FIRST_PAGE)
            scale = BIG_SCALE;
        else
            scale = SMALL_SCALE;

        position = position % Tab2Fragment.PAGES;

        return PadrinhosFragment.newInstance(context, position, scale);
    }

    @Override
    public void transformPage(View page, float position) {

        MyLinearLayout myLinearLayout = (MyLinearLayout) page.findViewById(R.id.root);
        float scale = BIG_SCALE;
        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }
        if (scale < 0) scale = 0;
        myLinearLayout.setScaleBoth(scale);

    }

    @Override
    public int getCount() {
        return Tab2Fragment.PAGES * Tab2Fragment.LOOPS;
    }
}

