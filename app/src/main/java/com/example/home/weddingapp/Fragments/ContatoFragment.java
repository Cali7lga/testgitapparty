package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.Others.FileInfoContato;
import com.example.home.weddingapp.Others.FileInfoMsg;
import com.example.home.weddingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContatoFragment.ContatoFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContatoFragment extends Fragment {

    EditText email, msg;
    Button enviar;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ContatoFragmentInteractionListener mListener;

    public ContatoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContatoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContatoFragment newInstance(String param1, String param2) {
        ContatoFragment fragment = new ContatoFragment();
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
        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        email = (EditText) view.findViewById(R.id.editText10);
        msg = (EditText) view.findViewById(R.id.editText11);
        enviar = (Button) view.findViewById(R.id.button3);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Light.ttf");
        email.setTypeface(tf);
        msg.setTypeface(tf);
        enviar.setTypeface(tf);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().equals("") || msg.getText().toString().equals("")) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Ops!");
                    alertDialog.setMessage("Por favor, verifique se preencheu ambos os campos");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();

                } else {

                    FileInfoContato fileinfo = new FileInfoContato();
                    fileinfo.setMensagem(msg.getText().toString());
                    fileinfo.setEmail(email.getText().toString());

                    mRef.child("Contato").push().setValue(fileinfo);

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Sucesso!")
                            .setContentText("Muito obrigado por entrar em contato conosco! Responderemos via email o mais breve possível!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();
                                    MainActivity mainActivity = (MainActivity) getActivity();
                                    mainActivity.backfragment();

                                }
                            })
                            .show();

                }

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onContatoFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContatoFragmentInteractionListener) {
            mListener = (ContatoFragmentInteractionListener) context;
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
    public interface ContatoFragmentInteractionListener {
        // TODO: Update argument type and name
        void onContatoFragmentInteraction(Uri uri);
    }
}
