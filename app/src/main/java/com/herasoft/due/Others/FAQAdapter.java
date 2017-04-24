package com.herasoft.due.Others;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herasoft.due.R;

/**
 * Created by Home on 19/04/2017.
 */

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyViewHolder> {

    private final CharSequence[] items_perguntas;
    private final CharSequence[] items_respostas;

    public FAQAdapter(Context context, @ArrayRes int arrayResId1, @ArrayRes int arrayResId2) {
        this(context.getResources().getTextArray(arrayResId1),context.getResources().getTextArray(arrayResId2));
    }

    private FAQAdapter(CharSequence[] items1,CharSequence[] items2) {
        this.items_perguntas = items1;
        this.items_respostas = items2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_help,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.pergunta.setText(items_perguntas[position]);
        holder.resposta.setText(items_respostas[position]);

    }

    @Override
    public int getItemCount() {
        return items_perguntas.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView pergunta, resposta;

        MyViewHolder(View itemView) {
            super(itemView);

            pergunta = (TextView) itemView.findViewById(R.id.textView40);
            resposta = (TextView) itemView.findViewById(R.id.textView41);

        }
    }
}
