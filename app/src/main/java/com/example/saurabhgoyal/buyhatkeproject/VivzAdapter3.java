package com.example.saurabhgoyal.buyhatkeproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by saurabh goyal on 9/19/2015.
 */
public class VivzAdapter3 extends RecyclerView.Adapter<VivzAdapter3.MyViewHolder> {
    private ClickListener clickListener;
    private LayoutInflater inflater;
    List<MessageEntity> data= Collections.emptyList();//take care of null pointer exception
    private Context context;

    public VivzAdapter3(Context context, List<MessageEntity> data){
        inflater= LayoutInflater.from(context);
        this.context=context;
        this.data=data;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        //called only once
    View view=inflater.inflate(R.layout.customrow3, viewGroup, false);

        Log.d("Vivz", "on create view holder is called");
        MyViewHolder holder=new MyViewHolder(view);



        return holder;
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
MessageEntity current=data.get(i);
        Log.d("Vivz", "on bind view holder is called" + i);
        viewHolder.sender.setText(current.getUseremail());
        viewHolder.reciever.setText(current.getRecieveremail());
        viewHolder.message.setText(current.getMessage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sender;
        TextView reciever;
        TextView message;
        public MyViewHolder(View itemView) {
            super(itemView);
            sender= (TextView) itemView.findViewById(R.id.senderEmail);
            reciever= (TextView) itemView.findViewById(R.id.reciverEmail);
            message= (TextView) itemView.findViewById(R.id.message);

itemView.setOnClickListener(this);


        }
        public void delete(int position){

data.remove(position);
            notifyItemRemoved(position);



        }

        @Override
        public void onClick(View v) {
//delete(getPosition());

    //
            if(clickListener!=null){
                clickListener.itemclicked(v,getPosition());
            }

        }


    }
    public interface ClickListener{

        public void itemclicked(View view, int position);



    }
}
