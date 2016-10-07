package com.example.home.weddingapp.Fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.home.weddingapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFragment.VideoFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    TextView textdias, texthoras, textminutos, textsegundos;
    VideoView videoView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private VideoFragmentInteractionListener mListener;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
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

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        textdias = (TextView) view.findViewById(R.id.dias);
        texthoras = (TextView) view.findViewById(R.id.horas);
        textminutos = (TextView) view.findViewById(R.id.minutos);
        textsegundos = (TextView) view.findViewById(R.id.segundos);

        videoView = (VideoView) view.findViewById(R.id.videoView);

        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date d = null;
        java.util.Date d1 = null;
        Calendar calendar = Calendar.getInstance();
        try {
            d = dfDate.parse("06/05/2017 19:00:00");
            d1 = dfDate.parse(dfDate.format(calendar.getTime()));
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }

        long milisegundos = (d.getTime() - d1.getTime());

        new CountDownTimer(milisegundos,1000){

            double correcao = 1000*60*60*24;

            @Override
            public void onTick(long millisUntilFinished) {
                int dias = (int) (millisUntilFinished / correcao);
                textdias.setText(String.valueOf(dias));

                int horas = (int) (((millisUntilFinished / correcao) - dias) * 24);
                texthoras.setText(String.valueOf(horas));

                int minutos = (int) (((((millisUntilFinished / correcao) - dias) * 24) - horas) * 60);
                textminutos.setText(String.valueOf(minutos));

                int segundos = (int) (((((((millisUntilFinished / correcao) - dias) * 24) - horas) * 60) - minutos) *60);
                textsegundos.setText(String.valueOf(segundos));
            }

            @Override
            public void onFinish() {

            }
        }.start();

        String uripath = "android.resource://com.example.home.weddingapp/"+R.raw.wildlife;
        Uri src = Uri.parse(uripath);
        videoView.setVideoURI(src);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0,0);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll1);

        Animation blink = AnimationUtils.loadAnimation(getActivity(),R.anim.blink);
        layout.startAnimation(blink);

            return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onVideoFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VideoFragmentInteractionListener) {
            mListener = (VideoFragmentInteractionListener) context;
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

    public interface VideoFragmentInteractionListener {
        // TODO: Update argument type and name
        void onVideoFragmentInteraction(Uri uri);
    }

}
