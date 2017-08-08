package com.herasoft.due.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.herasoft.due.Activity.MainActivity;
import com.herasoft.due.Others.CustomImageView;
import com.herasoft.due.Others.FileInfoUrl;
import com.herasoft.due.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FotosFragment.FotosFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FotosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference().child("Codes").child(MainFragment.codigo);

    ImageButton imageButton;

    FirebaseRecyclerAdapter<FileInfoUrl,PhotoViewHolder> adapter;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FotosFragmentInteractionListener mListener;

    public FotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FotosFragment newInstance(String param1, String param2) {
        FotosFragment fragment = new FotosFragment();
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
        final View view = inflater.inflate(R.layout.fragment_fotos, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_rv2);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new FirebaseRecyclerAdapter<FileInfoUrl, PhotoViewHolder>(
                FileInfoUrl.class,
                R.layout.fragment_photoitem,
                PhotoViewHolder.class,
                dbRef.child("fotos")
        ) {
            @Override
            protected void populateViewHolder(final PhotoViewHolder viewHolder, final FileInfoUrl model, final int position) {

                viewHolder.imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int width = viewHolder.imageView.getMeasuredWidth();
                        int height = width*3/4;

                        viewHolder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
                        viewHolder.legenda.setHeight(height/4);

                        viewHolder.imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    }
                });

                if(model.getLegenda()!=null) {

                    viewHolder.legenda.setVisibility(View.VISIBLE);
                    viewHolder.legenda.setText(model.getLegenda());
                    viewHolder.legenda.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            view.getParent().requestDisallowInterceptTouchEvent(true);
                            return false;
                        }
                    });
                    viewHolder.legenda.setMovementMethod(new ScrollingMovementMethod());

                } else{
                    viewHolder.legenda.setVisibility(View.INVISIBLE);
                }

                final String url = model.getImageUrl();
                Uri uri = Uri.parse(url);
                Glide.with(getActivity()).load(uri).centerCrop().into(viewHolder.imageView);

                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        zoomImageFromThumb(viewHolder.imageView, url);

                    }
                });

                viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        final EditText input = new EditText(getActivity());

                        final AlertDialog aDialog = new AlertDialog.Builder(getActivity()).create();
                        aDialog.setTitle("Área Reservada");
                        aDialog.setMessage("Área destinada aos noivos. Se deseja remover a foto, digite a senha:");
                        aDialog.setView(input);
                        aDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Remover", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(input.getText().toString().equals("Remove")){

                                    adapter.getRef(position).removeValue();
                                    Toast.makeText(getActivity(), "Foto deletada com sucesso", Toast.LENGTH_SHORT).show();

                                }
                                else{

                                    Toast.makeText(getActivity(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                                    aDialog.dismiss();

                                }

                            }
                        });
                        aDialog.show();

                        return true;
                    }
                });

            }
        };

        mRecyclerView.setAdapter(adapter);

        imageButton = (ImageButton) view.findViewById(R.id.imageButton19);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadCapture();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFotosFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FotosFragmentInteractionListener) {
            mListener = (FotosFragmentInteractionListener) context;
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

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        CustomImageView imageView;
        TextView legenda;
        RelativeLayout relativeLayout;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            legenda = (TextView) itemView.findViewById(R.id.textView49);
            imageView = (CustomImageView) itemView.findViewById(R.id.imageView19);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_photo);

        }
    }

    private void zoomImageFromThumb(final View thumbView, String imageUrl) {

        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) getView().findViewById(
                R.id.expanded_image);

        Uri uri = Uri.parse(imageUrl);
        Glide.with(getActivity()).load(uri).into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        getView().findViewById(R.id.container_rv)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
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

    public interface FotosFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFotosFragmentInteraction(Uri uri);
    }
}
