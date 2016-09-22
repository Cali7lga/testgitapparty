package com.example.home.weddingapp.Activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.home.weddingapp.Fragments.FornecedoresFragment;
import com.example.home.weddingapp.Fragments.HistoryFragment;
import com.example.home.weddingapp.Fragments.MainFragment;
import com.example.home.weddingapp.Fragments.MapsFragment;
import com.example.home.weddingapp.Fragments.PadrinhosFragment;
import com.example.home.weddingapp.Fragments.PhotosFragment;
import com.example.home.weddingapp.Fragments.StreetviewFragment;
import com.example.home.weddingapp.Fragments.Tab3Fragment;
import com.example.home.weddingapp.Fragments.Tab4Fragment;
import com.example.home.weddingapp.Fragments.Tab5Fragment;
import com.example.home.weddingapp.R;
import com.example.home.weddingapp.Fragments.Tab1Fragment;
import com.example.home.weddingapp.Fragments.Tab2Fragment;
import com.example.home.weddingapp.Fragments.TabBarFragment;

public class MainActivity extends FragmentActivity
        implements MainFragment.MainFragmentInteractionListener,
        TabBarFragment.TabBarFragmentInteractionListener,
        Tab1Fragment.Tab1FragmentInteractionListener,
        Tab2Fragment.Tab2FragmentInteractionListener,
        HistoryFragment.HistoryFragmentInteractionListener,
        PhotosFragment.PhotosFragmentInteractionListener,
        Tab3Fragment.Tab3FragmentInteractionListener,
        PadrinhosFragment.PadrinhosFragmentInteractionListener,
        Tab4Fragment.Tab4FragmentInteractionListener,
        MapsFragment.MapsFragmentInteractionListener,
        StreetviewFragment.StviewFragmentInteractionListener,
        FornecedoresFragment.FornecedoresFragmentInteractionListener,
        Tab5Fragment.Tab5FragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.FragmentContainer);

        if (fragment == null) {

            fragment = new MainFragment();
            manager.beginTransaction().add(R.id.FragmentContainer, fragment).commit();

        }
    }


    public void loadTabBar() {

        TabBarFragment tabbar = new TabBarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, tabbar).addToBackStack(null).commit();
    }

    public void loadMaps() {

        MapsFragment mapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, mapsFragment).addToBackStack(null).commit();

    }

    public void loadStview() {

        StreetviewFragment stvFragment = new StreetviewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, stvFragment).addToBackStack(null).commit();

    }

    public void loadFornecedores() {

        FornecedoresFragment fFragment = new FornecedoresFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, fFragment).addToBackStack(null).commit();

    }


    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTabBarFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTab1FragmentInteraction(Uri uri) {

    }

    @Override
    public void onTab2FragmentInteraction(Uri uri) {

    }

    @Override
    public void onHistoryFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPhotosFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTab3FragmentInteraction(Uri uri) {

    }

    @Override
    public void onTab4FragmentInteraction(Uri uri) {

    }

    @Override
    public void onTab5FragmentInteraction(Uri uri) {

    }

    @Override
    public void onPadrinhosFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMapsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStviewFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFornecedoresFragmentInteraction(Uri uri) {

    }
}