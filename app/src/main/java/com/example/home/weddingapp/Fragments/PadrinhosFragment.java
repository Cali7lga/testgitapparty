package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ImageView;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.R;
import com.example.home.weddingapp.Others.MyLinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PadrinhosFragment.PadrinhosFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PadrinhosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PadrinhosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PadrinhosFragmentInteractionListener mListener;

    public PadrinhosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PadrinhosFragment.
     */
    // TODO: Rename and change types and number of parameters
    /**public static PadrinhosFragment newInstance(String param1, String param2) {
        PadrinhosFragment fragment = new PadrinhosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }**/

    public static Fragment newInstance(MainActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, PadrinhosFragment.class.getName(), b);
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
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout) inflater.inflate(R.layout.fragment_padrinhos, container, false);

        int pos = this.getArguments().getInt("pos");
        TextView tv = (TextView) l.findViewById(R.id.text);
        ImageView image = (ImageView) l.findViewById(R.id.content);
        switch(pos) {
            case 0:
                tv.setText("Legenda 0");
                image.setImageResource(R.drawable.pteste1);
                break;
            case 1:
                tv.setText("Legenda 1");
                image.setImageResource(R.drawable.pteste2);
                break;
            case 2:
                tv.setText("Legenda 2");
                image.setImageResource(R.drawable.pteste3);
                break;
            case 3:
                tv.setText("Legenda 3");
                image.setImageResource(R.drawable.pteste4);
                break;
            case 4:
                tv.setText("Legenda 4");
                image.setImageResource(R.drawable.pteste5);
                break;
            case 5:
                tv.setText("Legenda 5");
                image.setImageResource(R.drawable.pteste6);
                break;
            case 6:
                tv.setText("Legenda 6");
                image.setImageResource(R.drawable.pteste7);
                break;

        }

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return l;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPadrinhosFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PadrinhosFragmentInteractionListener) {
            mListener = (PadrinhosFragmentInteractionListener) context;
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
    public interface PadrinhosFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPadrinhosFragmentInteraction(Uri uri);
    }
}
