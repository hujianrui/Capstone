package com.capstone.sportsmate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.sportsmate.Class.Ticket;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private ArrayList<Ticket> tickets = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_ticket, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.date.setText(tickets.get(i).getDate());
        viewHolder.time.setText(tickets.get(i).getTime());
        viewHolder.skilllvl.setText(tickets.get(i).getLevel());

//        viewHolder.ticket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("Recycle View", "onClick: " + tickets.get(i).getTid());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView date, time, skilllvl, pplnum;
        RelativeLayout ticket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.tv_date);
            time = itemView.findViewById(R.id.tv_time);
            skilllvl = itemView.findViewById(R.id.tv_skilllvl);
            pplnum = itemView.findViewById(R.id.tv_pplnum);
            ticket = itemView.findViewById(R.id.rv_ticket);
        }
    }

}
