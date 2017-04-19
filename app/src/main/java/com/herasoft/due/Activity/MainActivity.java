package com.herasoft.due.Activity;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.herasoft.due.Fragments.CaptureFragment;
import com.herasoft.due.Fragments.CerimoniaFragment;
import com.herasoft.due.Fragments.ContatoFragment;
import com.herasoft.due.Fragments.EscreverFragment;
import com.herasoft.due.Fragments.FornecedoresFragment;
import com.herasoft.due.Fragments.FotosFragment;
import com.herasoft.due.Fragments.HelpFragment;
import com.herasoft.due.Fragments.HistoryFragment;
import com.herasoft.due.Fragments.LoadWebFragment;
import com.herasoft.due.Fragments.LoginFragment;
import com.herasoft.due.Fragments.MainFragment;
import com.herasoft.due.Fragments.MapsFragment;
import com.herasoft.due.Fragments.MensagensFragment;
import com.herasoft.due.Fragments.PadrinhosFragment;
import com.herasoft.due.Fragments.PayFragment;
import com.herasoft.due.Fragments.PhotosFragment;
import com.herasoft.due.Fragments.StreetviewFragment;
import com.herasoft.due.Fragments.Tab1Fragment;
import com.herasoft.due.Fragments.Tab2Fragment;
import com.herasoft.due.Fragments.Tab3Fragment;
import com.herasoft.due.Fragments.Tab4Fragment;
import com.herasoft.due.R;
import com.herasoft.due.Fragments.TabBarFragment;

public class MainActivity extends FragmentActivity
        implements MainFragment.MainFragmentInteractionListener,
        LoginFragment.LoginFragmentInteractionListener,
        TabBarFragment.TabBarFragmentInteractionListener,
        Tab1Fragment.Tab1FragmentInteractionListener,
        HistoryFragment.HistoryFragmentInteractionListener,
        PhotosFragment.PhotosFragmentInteractionListener,
        Tab2Fragment.Tab2FragmentInteractionListener,
        PadrinhosFragment.PadrinhosFragmentInteractionListener,
        Tab3Fragment.Tab3FragmentInteractionListener,
        MensagensFragment.MensagensFragmentInteractionListener,
        EscreverFragment.EscreverFragmentInteractionListener,
        FotosFragment.FotosFragmentInteractionListener,
        CaptureFragment.CaptureFragmentInteractionListener,
        CerimoniaFragment.Tab4FragmentInteractionListener,
        MapsFragment.MapsFragmentInteractionListener,
        StreetviewFragment.StviewFragmentInteractionListener,
        FornecedoresFragment.FornecedoresFragmentInteractionListener,
        Tab4Fragment.Tab5FragmentInteractionListener,
        PayFragment.PayFragmentInteractionListener,
        HelpFragment.HelpFragmentInteractionListener,
        ContatoFragment.ContatoFragmentInteractionListener,
        LoadWebFragment.LoadWebFragmentInteractionListener{

    public static MediaPlayer mediaPlayer;
    private int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawable(new ColorDrawable(0xffffffff));

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.FragmentContainer);

        if (fragment == null) {

            fragment = new LoginFragment();
            manager.beginTransaction().add(R.id.FragmentContainer, fragment, "login").commit();

        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Sair");
            alertDialog.setMessage("Você deseja sair do aplicativo?");
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();

        } else {
            getSupportFragmentManager().popBackStack();
        }

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
            if(Tab1Fragment.speaking == 1) {
                startmusic();
            }
        }
    }

    public void startmusic(){
        if(mediaPlayer!=null){
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(length);
                mediaPlayer.start();
            }
        }
    }
    public void stopmusic(){
        if(mediaPlayer!=null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            length = 0;
        }
    }
    public void pausemusic(){
        if(mediaPlayer!=null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                length = mediaPlayer.getCurrentPosition();
            }
        }
    }

    public void loadLogin() {

        LoginFragment login = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, login).commit();

    }

    public void loadMain() {

        MainFragment main = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, main).commit();

    }

    public void loadTabBar() {

        TabBarFragment tabbar = new TabBarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, tabbar).commit();

    }

    public void loadHistory() {

        HistoryFragment history = new HistoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, history).addToBackStack(null).commit();

    }

    public void loadPhotos() {

        PhotosFragment photos = new PhotosFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, photos).addToBackStack(null).commit();

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

    public void loadFotos(){

        FotosFragment fotosFragment = new FotosFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, fotosFragment).addToBackStack(null).commit();

    }

    public void loadCerimonia(){

        CerimoniaFragment cerimoniaFragment = new CerimoniaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, cerimoniaFragment).addToBackStack(null).commit();

    }

    public void loadCapture(){

        CaptureFragment captureFragment = new CaptureFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, captureFragment).addToBackStack(null).commit();

    }

    public void loadPayment(){

        PayFragment payFragment = new PayFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, payFragment).addToBackStack(null).commit();

    }

    public void loadHelp(){

        HelpFragment helpFragment = new HelpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, helpFragment).addToBackStack(null).commit();

    }

    public void loadContato(){

        ContatoFragment contatoFragment = new ContatoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, contatoFragment).addToBackStack(null).commit();

    }

    public void loadWeb(String url){

        LoadWebFragment webFragment = LoadWebFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, webFragment).addToBackStack(null).commit();

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

    @Override
    public void onFotosFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCaptureFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPayFragmentInteraction(Uri uri) {

    }

    @Override
    public void onHelpFragmentInteraction(Uri uri) {

    }

    @Override
    public void onContatoFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLoginFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLoadWebFragmentInteraction(Uri uri) {

    }
}