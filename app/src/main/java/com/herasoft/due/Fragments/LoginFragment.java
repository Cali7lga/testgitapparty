package com.herasoft.due.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herasoft.due.Activity.MainActivity;
import com.herasoft.due.Others.FAQAdapter;
import com.herasoft.due.R;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.LoginFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private final int RC_SIGN_IN = 1;
    String provider;

    public static String login_email, login_nome;

    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users");

    ImageButton entrar;
    Button created;
    TextView cadastrar, back, help, redefinir, gmail, face, due;
    EditText c_nome, c_email, c_senha, l_email, l_senha;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LoginFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        l_email = (EditText) view.findViewById(R.id.editText);
        l_senha = (EditText) view.findViewById(R.id.editText9);

        entrar = (ImageButton) view.findViewById(R.id.imageButton3);
        cadastrar = (TextView) view.findViewById(R.id.textView32);
        help = (TextView) view.findViewById(R.id.textView36);
        redefinir = (TextView) view.findViewById(R.id.textView12);
        due = (TextView) view.findViewById(R.id.textView16);

        gmail = (TextView) view.findViewById(R.id.textView42);
        face = (TextView) view.findViewById(R.id.textView43);

        final Typeface tf_m = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Light.ttf");
        final Typeface tf_r = Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenirnext-ultralight.ttf");

        due.setTypeface(tf_r);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signInWithEmailAndPassword(l_email.getText().toString(),l_senha.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(),"Email ou senha incorretos",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        redefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!l_email.getText().toString().equals("")) {
                    mAuth.sendPasswordResetEmail(l_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new MaterialDialog.Builder(getActivity())
                                        .title("Email enviado")
                                        .titleColorRes(R.color.dark_gray)
                                        .typeface(tf_m, tf_m)
                                        .content("Verifique o email que enviamos em sua caixa de entrada para redefinir a sua senha.")
                                        .contentColorRes(R.color.dark_gray)
                                        .positiveText("Ok")
                                        .positiveColorRes(android.R.color.holo_blue_dark)
                                        .show();
                            } else {
                                task.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        new MaterialDialog.Builder(getActivity())
                                                .title("Oops...")
                                                .titleColorRes(R.color.dark_gray)
                                                .typeface(tf_m, tf_m)
                                                .content(e.getLocalizedMessage())
                                                .contentColorRes(R.color.dark_gray)
                                                .positiveText("Ok")
                                                .positiveColorRes(android.R.color.holo_blue_dark)
                                                .show();
                                    }
                                });
                            }
                        }
                    });
                } else{
                    new MaterialDialog.Builder(getActivity())
                            .title("Oops...")
                            .titleColorRes(R.color.dark_gray)
                            .typeface(tf_m, tf_m)
                            .content("Preencha o campo de email para que possamos lhe enviar o email de redefinição de senha.")
                            .contentColorRes(R.color.dark_gray)
                            .positiveText("Ok")
                            .positiveColorRes(android.R.color.holo_blue_dark)
                            .show();
                }
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FAQAdapter adapter = new FAQAdapter(getActivity(),R.array.perguntas,R.array.respostas);

                new MaterialDialog.Builder(getActivity())
                        .title("Dúvidas Frequentes")
                        .titleColorRes(R.color.dark_gray)
                        .titleGravity(GravityEnum.CENTER)
                        .typeface(tf_m,tf_m)
                        .backgroundColorRes(R.color.bg_bege)
                        .adapter(adapter,null)
                        .show();

            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .title("Conta Due")
                        .titleColorRes(R.color.dark_gray)
                        .titleGravity(GravityEnum.CENTER)
                        .typeface(tf_m,tf_m)
                        .customView(R.layout.dialog_cadastrar,true)
                        .build();

                c_nome = (EditText) dialog.getCustomView().findViewById(R.id.editText12);
                c_email = (EditText) dialog.getCustomView().findViewById(R.id.editText13);
                c_senha = (EditText) dialog.getCustomView().findViewById(R.id.editText14);

                c_nome.setTypeface(tf_m);
                c_email.setTypeface(tf_m);
                c_senha.setTypeface(tf_m);

                created = (Button) dialog.getCustomView().findViewById(R.id.button7);
                created.setTypeface(tf_m);
                created.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAuth.createUserWithEmailAndPassword(c_email.getText().toString(),c_senha.getText().toString())
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    dialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    new MaterialDialog.Builder(getActivity())
                                            .title("Oops...")
                                            .titleColorRes(R.color.dark_gray)
                                            .typeface(tf_m, tf_m)
                                            .content(e.getLocalizedMessage())
                                            .contentColorRes(R.color.dark_gray)
                                            .positiveText("Ok")
                                            .positiveColorRes(android.R.color.holo_blue_dark)
                                            .show();
                                }
                            });

                    }
                });

                back = (TextView) dialog.getCustomView().findViewById(R.id.textView34);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.oauth_id_client))
                        .requestEmail()
                        .build();

                if(mGoogleApiClient == null) {
                    mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                            .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                                @Override
                                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                    System.out.println(connectionResult);
                                }
                            })
                            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                            .build();
                }

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, Arrays.asList("email", "public_profile"));
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                mAuth.signInWithCredential(credential);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null) {

                    for (UserInfo usuario: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                        if(!usuario.getProviderId().equals("firebase")){
                            provider = usuario.getProviderId();
                        }
                    }

                    if(provider.equals("password")) {

                        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(user.getUid())) {

                                    login_nome = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                                    login_email = dataSnapshot.child(user.getUid()).child("email").getValue(String.class);

                                } else{

                                    mRef.child(user.getUid()).child("name").setValue(c_nome.getText().toString());
                                    mRef.child(user.getUid()).child("email").setValue(c_email.getText().toString());
                                    login_nome = c_nome.getText().toString();
                                    login_email = c_email.getText().toString();

                                }

                                MainActivity mainactivity = (MainActivity) getActivity();
                                mainactivity.loadMain();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else{

                        login_nome = user.getDisplayName();
                        login_email = user.getEmail();
                        MainActivity mainactivity = (MainActivity) getActivity();
                        mainactivity.loadMain();

                    }

                }
            }
        };

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()) {

                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential);

            }
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLoginFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragmentInteractionListener) {
            mListener = (LoginFragmentInteractionListener) context;
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
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
    public interface LoginFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLoginFragmentInteraction(Uri uri);
    }
}
