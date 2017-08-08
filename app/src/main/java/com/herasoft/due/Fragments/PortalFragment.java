package com.herasoft.due.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herasoft.due.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PortalFragment.PortalFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PortalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PortalFragment extends Fragment {

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    TextView eventName, onlineCheck;
    Button editName, startDue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PortalFragmentInteractionListener mListener;

    public PortalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PortalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PortalFragment newInstance(String param1, String param2) {
        PortalFragment fragment = new PortalFragment();
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
        View view = inflater.inflate(R.layout.fragment_portal, container, false);

        eventName = (TextView) view.findViewById(R.id.textView38);
        onlineCheck = (TextView) view.findViewById(R.id.textView39);
        editName = (Button) view.findViewById(R.id.button13);
        startDue = (Button) view.findViewById(R.id.button14);

        if(TextUtils.isEmpty(LoginFragment.login_versionName)){
            onlineCheck.setText("Iniciar versão Due");
            new MaterialDialog.Builder(getActivity())
                    .title("Nome da versão")
                    .content("Escolha um nome para a sua versão Due adquirida")
                    .positiveText("Criar")
                    .input("Digite o nome", "", false, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
                            if(input.toString().equals("")){
                                Toast.makeText(getActivity(),"Por favor, digite um nome para o seu evento antes de continuar!",Toast.LENGTH_LONG).show();
                            }else {
                                LoginFragment.login_versionName = input.toString();
                                LoginFragment.login_firstName = input.toString();
                                eventName.setText(input.toString());

                                mRef.child("Users").child(LoginFragment.login_user).child("eventName").setValue(input.toString());
                                mRef.child("Users").child(LoginFragment.login_user).child("firstEvent").setValue(input.toString());
                                mRef.child("Users").child(LoginFragment.login_user).child("nameSet").setValue(true);

                                mRef.child("Codes").child(input.toString()).child("eventName").setValue(input.toString());
                                mRef.child("Codes").child(input.toString()).child("user").setValue(LoginFragment.login_user);

                                dialog.dismiss();
                            }
                        }
                    })
                    .autoDismiss(false)
                    .cancelable(false)
                    .show();
        }else{
            eventName.setText(LoginFragment.login_versionName);
            mRef.child("Codes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(LoginFragment.login_firstName).hasChild("dateStart")){
                        onlineCheck.setText("Versão online até "+dataSnapshot.child(LoginFragment.login_firstName).child("dateEnd").getValue(String.class));
                        startDue.setVisibility(View.INVISIBLE);
                    }else{
                        onlineCheck.setText("Iniciar versão Due");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("Nome da versão")
                        .content("Como vai se chamar seu evento a partir de agora?")
                        .positiveText("Alterar")
                        .neutralText("Cancelar")
                        .input("Novo nome aqui", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                mRef.child("Users").child(LoginFragment.login_user).child("eventName").setValue(input.toString());
                                mRef.child("Codes").child(LoginFragment.login_firstName).child("eventName").setValue(input.toString());
                                eventName.setText(input.toString());
                            }
                        })
                        .show();
            }
        });

        startDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int dia=c.get(Calendar.DAY_OF_MONTH);
                int mes=c.get(Calendar.MONTH);
                int ano=c.get(Calendar.YEAR);
                String todayDate = (dia+"."+(mes+1)+"."+ano);

                for(int t=1;t<=3;t++){
                    mes++;
                    if(mes>12){
                        mes=1;
                        ano++;
                    }
                }

                String expirateDate = (dia+"."+(mes+1)+"."+ano);

                mRef.child("Codes").child(LoginFragment.login_firstName).child("dateStart").setValue(todayDate);
                mRef.child("Codes").child(LoginFragment.login_firstName).child("dateEnd").setValue(expirateDate);
                onlineCheck.setText("Versão online até "+expirateDate);
                startDue.setVisibility(View.INVISIBLE);

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPortalFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PortalFragmentInteractionListener) {
            mListener = (PortalFragmentInteractionListener) context;
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
    public interface PortalFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPortalFragmentInteraction(Uri uri);
    }
}
