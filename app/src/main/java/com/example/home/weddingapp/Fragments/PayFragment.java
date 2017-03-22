package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.Others.DatabaseTask;
import com.example.home.weddingapp.R;
import com.stripe.*;
import com.stripe.android.*;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PayFragment.PayFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayFragment extends Fragment {

    EditText edt_nome, edt_numero, edt_mes, edt_ano, edt_cvc, edt_valor;
    Button button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PayFragmentInteractionListener mListener;

    public PayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayFragment newInstance(String param1, String param2) {
        PayFragment fragment = new PayFragment();
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
        View view = inflater.inflate(R.layout.fragment_pay, container, false);

        edt_nome = (EditText) view.findViewById(R.id.editText4);
        edt_numero = (EditText) view.findViewById(R.id.editText5);
        edt_mes = (EditText) view.findViewById(R.id.editText6);
        edt_ano = (EditText) view.findViewById(R.id.editText7);
        edt_cvc = (EditText) view.findViewById(R.id.editText8);
        edt_valor = (EditText) view.findViewById(R.id.editText9);

        button = (Button) view.findViewById(R.id.button6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Antes de finalizar...")
                        .setContentText("Deseja confirmar a operação?")
                        .setCancelText("Cancelar")
                        .setConfirmText("Sim, continuar")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.setTitleText("Operação cancelada")
                                        .setContentText("Nada tema, sua fortuna está intacta")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                try {

                                    String cardNumber = edt_numero.getText().toString();
                                    int cardExpMonth = Integer.parseInt(edt_mes.getText().toString());
                                    int cardExpYear = Integer.parseInt(edt_ano.getText().toString());
                                    String cardCVC = edt_cvc.getText().toString();
                                    final String valor = edt_valor.getText().toString();
                                    final String desc = ("Doação feita por "+edt_nome.getText().toString());

                                    Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);
                                    if (!card.validateCard()) {
                                        System.out.println("Error valid card");
                                    } else {

                                        try {
                                            System.out.println("Valid card");
                                            Stripe stripe = new Stripe(getContext(), "pk_live_68BdK9ZwAWbirEQYYSTH7Wpg");
                                            stripe.createToken(card, new TokenCallback() {
                                                @Override
                                                public void onError(Exception error) {
                                                    System.out.println("Erro token callback: " + error.toString());
                                                }

                                                @Override
                                                public void onSuccess(Token token) {

                                                    System.out.println("Sucesso token callback");

                                                    DatabaseTask databaseTask = new DatabaseTask("SENDTOKEN", token, valor, desc);
                                                    databaseTask.execute();

                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    sweetAlertDialog.setTitleText("Sucesso!")
                                            .setContentText("Muito obrigado por contribuir com a construção do nosso novo lar!")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                    sweetAlertDialog.dismiss();
                                                    MainActivity mainActivity = (MainActivity) getActivity();
                                                    mainActivity.backfragment();

                                                }
                                            })
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                } catch(Exception e){
                                    Toast.makeText(getActivity(),"ERRO! Preencha corretamente os campos acima.",Toast.LENGTH_SHORT).show();
                                    sweetAlertDialog.dismiss();
                                }
                            }
                        })
                        .show();

            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPayFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PayFragmentInteractionListener) {
            mListener = (PayFragmentInteractionListener) context;
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
    public interface PayFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPayFragmentInteraction(Uri uri);
    }

}
