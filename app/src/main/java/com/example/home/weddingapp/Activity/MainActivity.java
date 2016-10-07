package com.example.home.weddingapp.Activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.home.weddingapp.Fragments.EscreverFragment;
import com.example.home.weddingapp.Fragments.FornecedoresFragment;
import com.example.home.weddingapp.Fragments.HistoryFragment;
import com.example.home.weddingapp.Fragments.MainFragment;
import com.example.home.weddingapp.Fragments.MapsFragment;
import com.example.home.weddingapp.Fragments.MensagensFragment;
import com.example.home.weddingapp.Fragments.PadrinhosFragment;
import com.example.home.weddingapp.Fragments.PhotosFragment;
import com.example.home.weddingapp.Fragments.StreetviewFragment;
import com.example.home.weddingapp.Fragments.Tab2Fragment;
import com.example.home.weddingapp.Fragments.Tab3Fragment;
import com.example.home.weddingapp.Fragments.Tab4Fragment;
import com.example.home.weddingapp.Fragments.Tab5Fragment;
import com.example.home.weddingapp.Fragments.VideoFragment;
import com.example.home.weddingapp.R;
import com.example.home.weddingapp.Fragments.Tab1Fragment;
import com.example.home.weddingapp.Fragments.TabBarFragment;

public class MainActivity extends FragmentActivity
        implements MainFragment.MainFragmentInteractionListener,
        TabBarFragment.TabBarFragmentInteractionListener,
        Tab1Fragment.Tab1FragmentInteractionListener,
        VideoFragment.VideoFragmentInteractionListener,
        HistoryFragment.HistoryFragmentInteractionListener,
        PhotosFragment.PhotosFragmentInteractionListener,
        Tab2Fragment.Tab2FragmentInteractionListener,
        PadrinhosFragment.PadrinhosFragmentInteractionListener,
        Tab3Fragment.Tab3FragmentInteractionListener,
        MensagensFragment.MensagensFragmentInteractionListener,
        EscreverFragment.EscreverFragmentInteractionListener,
        Tab4Fragment.Tab4FragmentInteractionListener,
        MapsFragment.MapsFragmentInteractionListener,
        StreetviewFragment.StviewFragmentInteractionListener,
        FornecedoresFragment.FornecedoresFragmentInteractionListener,
        Tab5Fragment.Tab5FragmentInteractionListener {

    public static MediaPlayer mediaPlayer;
    public static ImageButton speaker, mute;
    private int length = 0;

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

        speaker = (ImageButton) findViewById(R.id.button3);
        mute = (ImageButton) findViewById(R.id.button4);

        mediaPlayer = MediaPlayer.create(this,R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(1.0f,1.0f);

        if(mediaPlayer.isPlaying()) {
            speaker.setVisibility(View.VISIBLE);
            mute.setVisibility(View.INVISIBLE);
        }
        else{
            speaker.setVisibility(View.INVISIBLE);
            mute.setVisibility(View.VISIBLE);
        }

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausemusic();
                speaker.setVisibility(View.INVISIBLE);
                mute.setVisibility(View.VISIBLE);
            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startmusic();
                mute.setVisibility(View.INVISIBLE);
                speaker.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        pausemusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment f = this.getSupportFragmentManager().findFragmentById(R.id.FragmentContainer);
        if(f instanceof MainFragment) {

        }
        else{
            if(speaker.getVisibility() == View.VISIBLE) {
                startmusic();
            }
        }
    }

    public void startmusic(){
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }
    public void stopmusic(){
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
            length = 0;
    }
    public void pausemusic(){
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
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

    public void loadMensagens(){

        MensagensFragment msgsFragment = new MensagensFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, msgsFragment).addToBackStack(null).commit();

    }

    public void loadEscrever(){

        EscreverFragment escreverFragment = new EscreverFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, escreverFragment).addToBackStack(null).commit();

    }

    public void backfragment(){
        getSupportFragmentManager().popBackStack();
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
    public void onVideoFragmentInteraction(Uri uri) {

    }

    @Override
    public void onHistoryFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPhotosFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTab2FragmentInteraction(Uri uri) {

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

    @Override
    public void onMensagensFragmentInteraction(Uri uri) {

    }

    @Override
    public void onEscreverFragmentInteraction(Uri uri) {

    }

}