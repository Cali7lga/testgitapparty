package com.example.home.weddingapp.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.Others.CustomImageView;
import com.example.home.weddingapp.Others.FileInfoUrl;
import com.example.home.weddingapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CaptureFragment.CaptureFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CaptureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaptureFragment extends Fragment {

    StorageReference storageReference;
    DatabaseReference databaseReference;
    CustomImageView customimageView;
    Button btn_new, btn_share;
    ProgressBar progressBar;
    String filepath;
    TextView textView;
    Matrix matrix;

    public static final int REQUEST_EXTERNAL_STORAGE = 0;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PIC_CROP = 2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CaptureFragmentInteractionListener mListener;

    public CaptureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaptureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaptureFragment newInstance(String param1, String param2) {
        CaptureFragment fragment = new CaptureFragment();
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
        View view = inflater.inflate(R.layout.fragment_capture, container, false);

        customimageView = (CustomImageView) view.findViewById(R.id.imageView20);
        textView = (TextView) view.findViewById(R.id.textView36);
        btn_new = (Button) view.findViewById(R.id.button9);
        btn_share = (Button) view.findViewById(R.id.button10);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        camerapermission();
        galeriapermission();

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    Uri file = Uri.fromFile(compressedFoto(new File(getFilepath())));
                    String uniqueID = UUID.randomUUID().toString();
                    UploadTask uploadTask = storageReference.child("fotosUrl/"+uniqueID).putFile(file);
                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            textView.setVisibility(View.VISIBLE);
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            int inteiro = (int) progress;
                            progressBar.setProgress(inteiro);

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            textView.setVisibility(View.INVISIBLE);
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            FileInfoUrl fileinfo = new FileInfoUrl();
                            fileinfo.setImageUrl(downloadUrl.toString());
                            databaseReference.child("fotosUrl").push().setValue(fileinfo);
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Upload Completo");
                            alertDialog.setMessage("Muito obrigado por compartilhar seu momento conosco!");
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity mainActivity = (MainActivity) getActivity();
                                    mainActivity.backfragment();
                                }
                            });
                            alertDialog.show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCaptureFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CaptureFragmentInteractionListener) {
            mListener = (CaptureFragmentInteractionListener) context;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            setFilepath(cursor.getString(columnIndex));

            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(getFilepath());

            try{
                ExifInterface exif = new ExifInterface(getFilepath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,1);

                setMatrix(new Matrix());
                if (orientation == 6) {
                    getMatrix().postRotate(90);
                }
                else if (orientation == 3) {
                    getMatrix().postRotate(180);
                }
                else if (orientation == 8) {
                    getMatrix().postRotate(270);
                }

                bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),getMatrix(),true);

            } catch (IOException e) {
                e.printStackTrace();
            }

            customimageView.setBackground(null);
            customimageView.setImageBitmap(bitmap);
            btn_share.setVisibility(View.VISIBLE);

        }
    }

    private void camerapermission() {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void galeriapermission() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
    }

    public String getFilepath(){
        return filepath;
    }
    public void setFilepath(String filepath){
        this.filepath = filepath;
    }

    public Matrix getMatrix(){
        return matrix;
    }
    public void setMatrix(Matrix matrix){
        this.matrix = matrix;
    }

    public File compressedFoto (File file){

        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            selectedBitmap = Bitmap.createBitmap(selectedBitmap,0,0,selectedBitmap.getWidth(),selectedBitmap.getHeight(),getMatrix(),true);

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;

        } catch (Exception e) {
            return null;
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
    public interface CaptureFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCaptureFragmentInteraction(Uri uri);
    }
}