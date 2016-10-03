package com.example.home.weddingapp.Others;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.home.weddingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Home on 02/10/2016.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private List<String> mensagem;
    private List<String> nome;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView msg;
        public TextView nm;

        public ViewHolder(View itemView) {
            super(itemView);

            msg = (TextView) itemView.findViewById(R.id.textView26);
            nm = (TextView) itemView.findViewById(R.id.textView27);

        }
    }

    public RvAdapter(List<String> lista){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.i("sepa", "onBindViewHolder: "+nome.toArray());
        //holder.msg.setText(mensagem);
       // holder.nm.setText(nome);

    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
