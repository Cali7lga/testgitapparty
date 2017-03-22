package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home.weddingapp.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class StreetviewFragment extends Fragment implements OnStreetViewPanoramaReadyCallback {

    private StreetViewPanorama stview;

    private StviewFragmentInteractionListener mListener;

    public StreetviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_streetview, container, false);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =  (SupportStreetViewPanoramaFragment) getChildFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        return view;
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

        stview = streetViewPanorama;

        LatLng local = new LatLng(-12.976903, -38.516542);

        stview.setPosition(local);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StviewFragmentInteractionListener) {
            mListener = (StviewFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface StviewFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStviewFragmentInteraction(Uri uri);
    }

}
