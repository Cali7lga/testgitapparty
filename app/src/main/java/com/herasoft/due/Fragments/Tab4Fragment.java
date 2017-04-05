package com.herasoft.due.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.herasoft.due.Activity.MainActivity;
import com.herasoft.due.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab4Fragment.Tab5FragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab4Fragment extends Fragment {

    Button presente;
    TextView texto, ass;
    ImageView imageView;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Codes").child(MainFragment.codigo).child("presentes");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Tab5FragmentInteractionListener mListener;

    public Tab4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab4Fragment newInstance(String param1, String param2) {
        Tab4Fragment fragment = new Tab4Fragment();
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
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);

        presente = (Button) view.findViewById(R.id.button5);

        texto = (TextView) view.findViewById(R.id.txt_texto);
        ass = (TextView) view.findViewById(R.id.txt_ass);

        imageView = (ImageView) view.findViewById(R.id.imageView12);

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                texto.setText(dataSnapshot.child("agradecimento").getValue(String.class));
                ass.setText(dataSnapshot.child("ass").getValue(String.class));

                String url = dataSnapshot.child("icone").getValue(String.class);
                Uri uri = Uri.parse(url);
                Glide.with(getActivity()).load(uri).into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Light.ttf");
        presente.setTypeface(tf);
        presente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.loadPayment();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTab5FragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Tab5FragmentInteractionListener) {
            mListener = (Tab5FragmentInteractionListener) context;
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
    public interface Tab5FragmentInteractionListener {
        // TODO: Update argument type and name
        void onTab5FragmentInteraction(Uri uri);
    }
}
