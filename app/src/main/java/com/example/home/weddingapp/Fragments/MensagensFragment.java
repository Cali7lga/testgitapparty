package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.Others.FileInfoMsg;
import com.example.home.weddingapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MensagensFragment.MensagensFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MensagensFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MensagensFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("Codes").child("999");

    ImageButton imageButton;

    FirebaseRecyclerAdapter<FileInfoMsg,MessageViewHolder> mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MensagensFragmentInteractionListener mListener;

    public MensagensFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MensagensFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MensagensFragment newInstance(String param1, String param2) {
        MensagensFragment fragment = new MensagensFragment();
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
        View view = inflater.inflate(R.layout.fragment_mensagens, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_rv);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<FileInfoMsg, MessageViewHolder>(
                FileInfoMsg.class,
                R.layout.fragment_msgitem,
                MessageViewHolder.class,
                mRef.child("mensagens")
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, final FileInfoMsg model, final int position) {

                String msg = model.getMensagem();
                String nm = model.getNome();

                viewHolder.messageText.setText(msg);
                viewHolder.nameText.setText(nm);

                viewHolder.messageText.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        final EditText input = new EditText(getActivity());

                        final AlertDialog aDialog = new AlertDialog.Builder(getActivity()).create();
                        aDialog.setTitle("Área Reservada");
                        aDialog.setMessage("Área destinada aos noivos. Se deseja remover a mensagem, digite a senha:");
                        aDialog.setView(input);
                        aDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Remover", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(input.getText().toString().equals("Remove")){

                                    mAdapter.getRef(position).removeValue();
                                    Toast.makeText(getActivity(), "Mensagem deletada com sucesso", Toast.LENGTH_SHORT).show();

                                }
                                else{

                                    Toast.makeText(getActivity(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                                    aDialog.dismiss();

                                }

                            }
                        });
                        aDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                aDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.dark_gray));
                            }
                        });
                        aDialog.show();

                        return true;
                    }
                });
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        imageButton = (ImageButton) view.findViewById(R.id.imageButton13);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadEscrever();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMensagensFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MensagensFragmentInteractionListener) {
            mListener = (MensagensFragmentInteractionListener) context;
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

    private static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.textView26);
            nameText = (TextView)itemView.findViewById(R.id.textView27);

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
    public interface MensagensFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMensagensFragmentInteraction(Uri uri);
    }
}
