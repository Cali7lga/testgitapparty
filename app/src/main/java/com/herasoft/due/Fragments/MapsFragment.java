package com.herasoft.due.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herasoft.due.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Codes").child(MainFragment.codigo).child("evento").child("localizacao");

    private GoogleMap mMap;

    private MapsFragmentInteractionListener mListener;

    public MapsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Google Maps");
        alertDialog.setMessage("Para traçar uma rota até o local, clique no " +
                "indicador vermelho e acesse o menu no canto inferior direito");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                double longitude = dataSnapshot.child("longitude").getValue(Double.class);

                LatLng local = new LatLng(latitude, longitude);
                float zoom = 14;
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.addMarker(new MarkerOptions().position(local).title(dataSnapshot.child("nomelocal").getValue(String.class))).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local,zoom));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MapsFragmentInteractionListener) {
            mListener = (MapsFragmentInteractionListener) context;
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

    public interface MapsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMapsFragmentInteraction(Uri uri);
    }

}