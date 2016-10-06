package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabBarFragment.TabBarFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabBarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TabBarFragmentInteractionListener mListener;

    public TabBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabBarFragment newInstance(String param1, String param2) {
        TabBarFragment fragment = new TabBarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_bar, container, false);

        FragmentTabHost tabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(),getChildFragmentManager(),R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("", getResources().getDrawable(R.drawable.wedding)),Tab1Fragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("", getResources().getDrawable(R.drawable.hearts)),Tab2Fragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("", getResources().getDrawable(R.drawable.mag)),Tab3Fragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("Tab4").setIndicator("", getResources().getDrawable(R.drawable.date)),Tab4Fragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("Tab5").setIndicator("", getResources().getDrawable(R.drawable.ribbon)),Tab5Fragment.class,null);

        tabHost.setCurrentTab(0);

        MainActivity.mediaPlayer.setVolume(1.0f,1.0f);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTabBarFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TabBarFragmentInteractionListener) {
            mListener = (TabBarFragmentInteractionListener) context;
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface TabBarFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTabBarFragmentInteraction(Uri uri);
    }
}
