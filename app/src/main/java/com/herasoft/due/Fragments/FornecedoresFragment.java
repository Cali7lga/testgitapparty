package com.herasoft.due.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.herasoft.due.Others.FileInfoFornecedor;
import com.herasoft.due.Others.FileInfoMsg;
import com.herasoft.due.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FornecedoresFragment.FornecedoresFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FornecedoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FornecedoresFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    FirebaseRecyclerAdapter<FileInfoFornecedor,FornecedorViewHolder> mAdapter;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Codes").child(MainFragment.codigo).child("fornecedores");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FornecedoresFragmentInteractionListener mListener;

    public FornecedoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FornecedoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FornecedoresFragment newInstance(String param1, String param2) {
        FornecedoresFragment fragment = new FornecedoresFragment();
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
        final View view = inflater.inflate(R.layout.fragment_fornecedores, container, false);

        final PackageManager packageManager = getActivity().getPackageManager();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_rv3);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<FileInfoFornecedor, FornecedorViewHolder>(
                FileInfoFornecedor.class,
                R.layout.fragment_fornecedoritem,
                FornecedorViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(FornecedorViewHolder viewHolder, FileInfoFornecedor model, int position) {

                String f_nome = model.getNome();
                String f_titulo = model.getTitulo();
                final String f_face = model.getFacebook();
                final String f_insta = model.getInstagram();

                viewHolder.tituloText.setText(f_titulo);
                viewHolder.nameText.setText(f_nome);
                viewHolder.ic_face.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent openface = newFacebookIntent(packageManager,f_face);
                        startActivity(openface);
                    }
                });
                viewHolder.ic_insta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newInstagramIntent(f_insta);
                    }
                });

            }
        };

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFornecedoresFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FornecedoresFragmentInteractionListener) {
            mListener = (FornecedoresFragmentInteractionListener) context;
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

    public static Intent newFacebookIntent(PackageManager pm, String url) {

        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public void newInstagramIntent (String url) {

        Uri uri = Uri.parse("http://instagram.com/_u/"+url);
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);
        insta.setPackage("com.instagram.android");
        try {
            startActivity(insta);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/"+url)));
        }

    }

    public static class FornecedorViewHolder extends RecyclerView.ViewHolder {
        TextView tituloText;
        TextView nameText;
        ImageButton ic_insta;
        ImageButton ic_face;

        public FornecedorViewHolder(View itemView) {
            super(itemView);

            tituloText = (TextView) itemView.findViewById(R.id.txt_titulo);
            nameText = (TextView) itemView.findViewById(R.id.txt_nome);
            ic_face = (ImageButton) itemView.findViewById(R.id.imageButton16);
            ic_insta = (ImageButton) itemView.findViewById(R.id.imageButton15);

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
    public interface FornecedoresFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFornecedoresFragmentInteraction(Uri uri);
    }
}
