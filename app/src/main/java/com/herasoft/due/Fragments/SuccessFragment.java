package com.herasoft.due.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.herasoft.due.Activity.MainActivity;
import com.herasoft.due.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuccessFragment.SuccessFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuccessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuccessFragment extends Fragment {

    Button back;

    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SuccessFragmentInteractionListener mListener;

    public SuccessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuccessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuccessFragment newInstance(String param1, String param2) {
        SuccessFragment fragment = new SuccessFragment();
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

        View view = inflater.inflate(R.layout.fragment_success, container, false);

//        mAuth = FirebaseAuth.getInstance();

        back = (Button) view.findViewById(R.id.button10);
        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Light.ttf");
        back.setTypeface(tf);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.backfragment();
            }
        });

        mRef.child(LoginFragment.login_user).child("hasDue").setValue(true);
        mRef.child(LoginFragment.login_user).child("nameSet").setValue(false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSuccessFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SuccessFragmentInteractionListener) {
            mListener = (SuccessFragmentInteractionListener) context;
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
    public interface SuccessFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSuccessFragmentInteraction(Uri uri);
    }
}
