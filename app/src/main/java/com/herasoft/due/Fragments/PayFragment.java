package com.herasoft.due.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.herasoft.due.Activity.MainActivity;
import com.herasoft.due.Others.DatabaseTask;
import com.herasoft.due.R;
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

    Spinner spinner;
    EditText edt_nome, edt_numero, edt_mes, edt_ano, edt_cvc;
    Button button;
    ImageButton help;

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

        help = (ImageButton) view.findViewById(R.id.imageButton10);
        button = (Button) view.findViewById(R.id.button6);

        spinner = (Spinner) view.findViewById(R.id.spinner);

        String[] items = new String[]{"50.00", "100.00", "150.00", "200.00", "250.00", "300.00", "350.00", "400.00", "450.00", "500.00", "600.00", "700.00", "800.00", "900.00", "1000.00", "1500.00", "2000.00"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        edt_numero.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // Remove spacing char
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainactivity = (MainActivity) getActivity();
                mainactivity.loadHelp();

            }
        });

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
                            public void onClick(final SweetAlertDialog sweetAlertDialog) {

                                try {

                                    String cardNumber = edt_numero.getText().toString();
                                    int cardExpMonth = Integer.parseInt(edt_mes.getText().toString());
                                    int cardExpYear = Integer.parseInt(edt_ano.getText().toString());
                                    String cardCVC = edt_cvc.getText().toString();
                                    final String valor = spinner.getSelectedItem().toString();
                                    final String desc = ("Doação feita por "+edt_nome.getText().toString());

                                    Card card = new Card(cardNumber, cardExpMonth, cardExpYear, cardCVC);
                                    if (!card.validateCard()) {
                                        sweetAlertDialog.setTitleText("Erro")
                                                .setContentText("Cartão inválido :(")
                                                .setConfirmText("OK")
                                                .showCancelButton(false)
                                                .setCancelClickListener(null)
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    } else {

                                        try {

                                            Stripe stripe = new Stripe(getContext(), "pk_live_68BdK9ZwAWbirEQYYSTH7Wpg");
                                            stripe.createToken(card, new TokenCallback() {
                                                @Override
                                                public void onError(Exception error) {
                                                    System.out.println("Erro token callback: " + error.toString());
                                                }

                                                @Override
                                                public void onSuccess(Token token) {

                                                    System.out.println("Sucesso token callback");

                                                    DatabaseTask databaseTask = new DatabaseTask("SENDTOKEN", token, valor, desc,LoginFragment.login_email);
                                                    databaseTask.execute();

                                                    sweetAlertDialog.setTitleText("Muito obrigado! S2")
                                                            .setContentText("Você receberá a confirmação do seu presente por email!")
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

                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

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
