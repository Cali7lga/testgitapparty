package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.Others.FileInfo;
import com.example.home.weddingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EscreverFragment.EscreverFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EscreverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EscreverFragment extends Fragment {

    private EditText editText1;
    private EditText editText2;
    private Button botao;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EscreverFragmentInteractionListener mListener;

    public EscreverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EscreverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EscreverFragment newInstance(String param1, String param2) {
        EscreverFragment fragment = new EscreverFragment();
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
        View view = inflater.inflate(R.layout.fragment_escrever, container, false);

        editText1 = (EditText) view.findViewById(R.id.editText2);
        editText2 = (EditText) view.findViewById(R.id.editText3);
        botao = (Button) view.findViewById(R.id.button8);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileInfo fileinfo = new FileInfo();
                fileinfo.setMensagem(editText1.getText().toString());
                fileinfo.setName(editText2.getText().toString());

                mRef.child("messages").push().setValue(fileinfo);

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Mensagem Enviada");
                alertDialog.setMessage("Muito obrigado por nos deixar uma mensagem!");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.backfragment();

                    }
                });
                alertDialog.show();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onEscreverFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EscreverFragmentInteractionListener) {
            mListener = (EscreverFragmentInteractionListener) context;
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
    public interface EscreverFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEscreverFragmentInteraction(Uri uri);
    }
}
