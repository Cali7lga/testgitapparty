package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CerimoniaFragment.Tab4FragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CerimoniaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  CerimoniaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Tab4FragmentInteractionListener mListener;

    public CerimoniaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CerimoniaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CerimoniaFragment newInstance(String param1, String param2) {
        CerimoniaFragment fragment = new CerimoniaFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cerimonia, container, false);

        ImageButton botao = (ImageButton) view.findViewById(R.id.imageButton);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.loadMaps();

            }
        });

        ImageButton botao2 = (ImageButton) view.findViewById(R.id.imageButton2);
        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.loadStview();

            }
        });

        ImageButton botao3 = (ImageButton) view.findViewById(R.id.imageButton9);
        botao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.loadFornecedores();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTab4FragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Tab4FragmentInteractionListener) {
            mListener = (Tab4FragmentInteractionListener) context;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface Tab4FragmentInteractionListener {
        // TODO: Update argument type and name
        void onTab4FragmentInteraction(Uri uri);
    }
}
