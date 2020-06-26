package com.example.mlbastan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class GorevAdapter extends RecyclerView.Adapter<GorevAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder
    {


        public TextView gorevText;
        public TextView gorevNum;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gorevText = itemView.findViewById(R.id.gorev);
            gorevNum = itemView.findViewById(R.id.gorevnum);
            cardView = itemView.findViewById(R.id.cardviewList);

        }

    }

    private List<GorevModel> user_list;
    static Context context;

    GorevAdapter(List<GorevModel> user_list, Context context){
        this.user_list = user_list;
        this.context = context;
    }

    @NonNull
    @Override
    public GorevAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View vr = LayoutInflater.from(parent.getContext()).inflate(R.layout.gorevlist,parent,false);
        final  ViewHolder view_holder = new ViewHolder(vr);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

     //   int sayigoster = position+1;
        if (user_list.size()==1){
            holder.gorevText.setText(user_list.get(position).getgorev());
            holder.gorevNum.setText("");
        }else {
            holder.gorevText.setText(user_list.get(position).getgorev());
            holder.gorevNum.setText(user_list.get(position).getgorevnum());
        }
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }

}
