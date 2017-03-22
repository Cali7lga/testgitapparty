package com.example.home.weddingapp.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.home.weddingapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FornecedoresFragment.FornecedoresFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FornecedoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FornecedoresFragment extends Fragment {
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

        ImageButton btn_faceHera = (ImageButton) view.findViewById(R.id.imageButton16);
        ImageButton btn_faceBeth = (ImageButton) view.findViewById(R.id.imageButton3);
        ImageButton btn_face3img = (ImageButton) view.findViewById(R.id.imageButton5);
        ImageButton btn_faceDeni = (ImageButton) view.findViewById(R.id.imageButton7);
        ImageButton btn_faceSidn = (ImageButton) view.findViewById(R.id.imageButton17);
        ImageButton btn_faceHarm = (ImageButton) view.findViewById(R.id.imageButton30);
        ImageButton btn_faceCBar = (ImageButton) view.findViewById(R.id.imageButton32);
        ImageButton btn_facePark = (ImageButton) view.findViewById(R.id.imageButton34);
        ImageButton btn_faceFoto = (ImageButton) view.findViewById(R.id.imageButton36);
        ImageButton btn_faceDrea = (ImageButton) view.findViewById(R.id.imageButton38);
        ImageButton btn_faceFati = (ImageButton) view.findViewById(R.id.imageButton40);
        ImageButton btn_faceTani = (ImageButton) view.findViewById(R.id.imageButton42);
        ImageButton btn_faceLuci = (ImageButton) view.findViewById(R.id.imageButton44);

        ImageButton btn_instaHera = (ImageButton) view.findViewById(R.id.imageButton15);
        ImageButton btn_instaBeth = (ImageButton) view.findViewById(R.id.imageButton4);
        ImageButton btn_insta3img = (ImageButton) view.findViewById(R.id.imageButton6);
        ImageButton btn_instaDeni = (ImageButton) view.findViewById(R.id.imageButton8);
        ImageButton btn_instaSidn = (ImageButton) view.findViewById(R.id.imageButton18);
        ImageButton btn_instaHarm = (ImageButton) view.findViewById(R.id.imageButton31);
        ImageButton btn_instaCBar = (ImageButton) view.findViewById(R.id.imageButton33);
        ImageButton btn_instaPark = (ImageButton) view.findViewById(R.id.imageButton35);
        ImageButton btn_instaFoto = (ImageButton) view.findViewById(R.id.imageButton37);
        ImageButton btn_instaDrea = (ImageButton) view.findViewById(R.id.imageButton39);
        ImageButton btn_instaFati = (ImageButton) view.findViewById(R.id.imageButton41);
        ImageButton btn_instaTani = (ImageButton) view.findViewById(R.id.imageButton43);
        ImageButton btn_instaLuci = (ImageButton) view.findViewById(R.id.imageButton45);

        btn_faceHera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/heratechnologies/?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceBeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/bethbahienseeventos/?fref=ts");
                startActivity(openface);

            }
        });
        btn_face3img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/3imagens/?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceDeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/deniselins.salvador?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceSidn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/sidney.h.rodriguez?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceHarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/Harmonizi-118915164884644/");
                startActivity(openface);

            }
        });
        btn_faceCBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/Concept-Bar-117047165004007/");
                startActivity(openface);

            }
        });
        btn_facePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/vallet.park.3?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/digitalmasterfoto/?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceDrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/dream.rasteirinha/?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceFati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/fatima.bemcasados?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceTani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/T%C3%A2nia-Belos-Bolos-537163883070941/?fref=ts");
                startActivity(openface);

            }
        });
        btn_faceLuci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openface =  newFacebookIntent(packageManager,"https://www.facebook.com/luciana.m.cerimonialista?fref=ts");
                startActivity(openface);

            }
        });

    //------------------------------------------------------------------------------------

        btn_instaHera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("heratechnologies");

            }
        });

        btn_instaBeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("bethbahiense");

            }
        });
        btn_insta3img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("3imagens");

            }
        });
        btn_instaDeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("deniselinsconvitessalvador");

            }
        });
        btn_instaSidn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("sidneyhaackphotos");

            }
        });
        btn_instaHarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("harmonizi");

            }
        });
        btn_instaCBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("conceptbar");

            }
        });
        btn_instaPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("valletpark");

            }
        });
        btn_instaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("masterfotodigital");

            }
        });
        btn_instaDrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("dream_rasteirinhas");

            }
        });
        btn_instaFati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("fatimabemcasados");

            }
        });
        btn_instaTani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("taniabelosbolos");

            }
        });
        btn_instaLuci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newInstagramIntent("luciana_cerimonialista");

            }
        });


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
