package com.herasoft.due.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.herasoft.due.Activity.MainActivity;
import com.herasoft.due.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.MainFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment{

    public static String codigo;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    Button entrar, solicite, btn_codigo;
    EditText code;
    TextView nome, demo;
    LinearLayout menu;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Codes");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mAuth = FirebaseAuth.getInstance();

        menu = (LinearLayout) view.findViewById(R.id.linear_menu);
        nome = (TextView) view.findViewById(R.id.textView9);
        demo = (TextView) view.findViewById(R.id.textView33);
        solicite = (Button) view.findViewById(R.id.button);
        entrar = (Button) view.findViewById(R.id.button2);

        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Light.ttf");
        final Typeface tf_light = Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenirnext-ultralight.ttf");

        String s = "Olá, " + LoginFragment.login_nome;
        nome.setText(s);
        nome.setTypeface(tf_light);

        entrar.setTypeface(tf);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .title("Evento Due")
                        .titleColorRes(R.color.dark_gray)
                        .titleGravity(GravityEnum.CENTER)
                        .typeface(tf,tf_light)
                        .customView(R.layout.dialog_codigo,true)
                        .positiveText("Fechar")
                        .positiveColorRes(android.R.color.holo_blue_dark)
                        .backgroundColorRes(R.color.bg_bege)
                        .build();

                code = (EditText) dialog.findViewById(R.id.editText18);
                btn_codigo = (Button) dialog.findViewById(R.id.button4);
                btn_codigo.setTypeface(tf);
                btn_codigo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(code.getText().toString().equals("")){
                            new MaterialDialog.Builder(getActivity())
                                    .title("Oops...")
                                    .titleColorRes(R.color.dark_gray)
                                    .typeface(tf, tf_light)
                                    .content("Digite o código do evento que deseja acessar.")
                                    .contentColorRes(R.color.dark_gray)
                                    .positiveText("Ok")
                                    .positiveColorRes(android.R.color.holo_blue_dark)
                                    .show();
                        } else{
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.hasChild(code.getText().toString())){

                                        codigo = code.getText().toString();
                                        Uri uri = Uri.parse(dataSnapshot.child(codigo).child("video").child("musica").getValue(String.class));
                                        loadMusic(uri);
                                        MainActivity mainactivity = (MainActivity) getActivity();
                                        mainactivity.loadTabBar();
                                        dialog.dismiss();

                                    } else{
                                        new MaterialDialog.Builder(getActivity())
                                                .title("Oops...")
                                                .titleColorRes(R.color.dark_gray)
                                                .typeface(tf, tf_light)
                                                .content("Você digitou um código inexistente. Por favor, tente novamente.")
                                                .contentColorRes(R.color.dark_gray)
                                                .positiveText("Ok")
                                                .positiveColorRes(android.R.color.holo_blue_dark)
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        code.setText(null);
                                                    }
                                                })
                                                .show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                });

                dialog.show();

            }
        });

        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                codigo = "000";
                mRef.child(codigo).child("video").child("musica").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Uri uri = Uri.parse(dataSnapshot.getValue(String.class));
                        loadMusic(uri);
                        MainActivity mainactivity = (MainActivity) getActivity();
                        mainactivity.loadTabBar();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        solicite.setTypeface(tf);
        solicite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.loadContato();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("O que deseja fazer?")
                        .titleGravity(GravityEnum.CENTER)
                        .titleColorRes(R.color.dark_gray)
                        .typeface(tf,tf_light)
                        .items(R.array.menu)
                        .itemsColorRes(android.R.color.holo_blue_dark)
                        .itemsGravity(GravityEnum.CENTER)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {

                                MainActivity mainactivity = (MainActivity) getActivity();

                                switch (position){
                                    case 0:

                                        mAuth.signOut();
                                        LoginManager.getInstance().logOut();
                                        dialog.dismiss();
                                        mainactivity.loadLogin();

                                        break;
                                    case 1:

                                        mainactivity.loadWeb("https://www.herasoft.com.br/terms.html");

                                        break;
                                    case 2:

                                        mainactivity.loadWeb("https://www.herasoft.com.br/privacy.html");

                                        break;
                                }
                            }
                        })
                        .show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMainFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainFragmentInteractionListener) {
            mListener = (MainFragmentInteractionListener) context;
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

    @Override
    public void onPause() {
        super.onPause();

        MainActivity mainactivity = (MainActivity) getActivity();
        mainactivity.startmusic();
        if(MainActivity.mediaPlayer!=null) {
            MainActivity.mediaPlayer.setVolume(0, 0);
        }

    }

    public void loadMusic (Uri newUri){
        try {
            MainActivity.mediaPlayer = new MediaPlayer();
            MainActivity.mediaPlayer.setDataSource(getActivity(), newUri);
            MainActivity.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            MainActivity.mediaPlayer.setLooping(true);
            MainActivity.mediaPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
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
    public interface MainFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMainFragmentInteraction(Uri uri);
    }
}
