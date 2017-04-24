package com.herasoft.due.Fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.herasoft.due.Activity.MainActivity;
import com.herasoft.due.Others.FileInfoContato;
import com.herasoft.due.R;
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

    EditText noivo, noiva, data, local, email;
    Button enviar;
    CheckBox checkBox;
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

        noivo = (EditText) view.findViewById(R.id.editText16);
        noiva = (EditText) view.findViewById(R.id.editText17);
        data = (EditText) view.findViewById(R.id.editText15);
        local = (EditText) view.findViewById(R.id.editText11);
        email = (EditText) view.findViewById(R.id.editText10);
        enviar = (Button) view.findViewById(R.id.button3);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox1);

        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Light.ttf");
        final Typeface tf_light = Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenirnext-ultralight.ttf");

        noivo.setTypeface(tf);
        noivo.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        noiva.setTypeface(tf);
        noiva.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        data.setTypeface(tf);
        data.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        local.setTypeface(tf);
        local.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        email.setTypeface(tf);
        email.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        email.setText(LoginFragment.login_email);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    email.setText(LoginFragment.login_email);
                    email.setEnabled(false);
                } else{
                    email.setText(null);
                    email.setEnabled(true);
                }
            }
        });

        enviar.setTypeface(tf);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (noivo.getText().toString().equals("")
                        || noiva.getText().toString().equals("")
                        || data.getText().toString().equals("")
                        || local.getText().toString().equals("")
                        || email.getText().toString().equals("")) {

                    new MaterialDialog.Builder(getActivity())
                            .title("Oops...")
                            .titleColorRes(R.color.dark_gray)
                            .typeface(tf, tf_light)
                            .content("Por favor, verifique se preencheu todos os campos corretamente e tente novamente.")
                            .contentColorRes(R.color.dark_gray)
                            .positiveText("Ok")
                            .positiveColorRes(android.R.color.holo_blue_dark)
                            .show();

                } else {

                    FileInfoContato fileinfo = new FileInfoContato();
                    fileinfo.setNoivo(noivo.getText().toString());
                    fileinfo.setNoiva(noiva.getText().toString());
                    fileinfo.setData(data.getText().toString());
                    fileinfo.setLocal(local.getText().toString());
                    fileinfo.setEmail(email.getText().toString());

                    mRef.child("Contato").push().setValue(fileinfo).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Tudo certo!")
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
                    });

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
