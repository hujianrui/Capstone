package com.capstone.sportsmate;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.sportsmate.Class.Ticket;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private ArrayList<Ticket> tickets = new ArrayList<>();
    private View selectedView;
    private String selectedTid;


    public void resetSelectedView() {
        this.selectedView = null;
    }

    public String getSelectedTid() { return selectedTid; }

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.date.setText(tickets.get(i).getDate());
        viewHolder.time.setText(tickets.get(i).getTime());
        viewHolder.skilllvl.setText(tickets.get(i).getLevel());
        int num = tickets.get(i).getUserID().size();
        viewHolder.pplnum.setText(String.valueOf(num));
        String sport = tickets.get(i).getSports();
        if(sport.matches("Badminton")){
            viewHolder.image.setImageResource(R.drawable.badminton);
        }else if (sport.matches("Basketball")){
            viewHolder.image.setImageResource(R.drawable.basketball);
        }else if (sport.matches("Tennis")){
            viewHolder.image.setImageResource(R.drawable.tennis);
        }

        viewHolder.ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedView != null && selectedView != v){
                    selectedView.setBackgroundColor(Color.WHITE);
                    v.setBackgroundColor(Color.GRAY);
                    selectedTid = tickets.get(i).getTid();
                    selectedView = v;
//                    Toast.makeText(v.getContext(), "onClick: " + tickets.get(i).getTid(), Toast.LENGTH_LONG).show();
                }else if(selectedView == null){
                    v.setBackgroundColor(Color.GRAY);
                    selectedTid = tickets.get(i).getTid();
                    selectedView = v;
//                    Toast.makeText(v.getContext(), "onClick: " + tickets.get(i).getTid(), Toast.LENGTH_LONG).show();
                }else {
                    v.setBackgroundColor(Color.WHITE);
                    selectedView = null;
                    selectedTid = null;
//                    Toast.makeText(v.getContext(), "Clear Info", Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(v.getContext(), "onClick: " + tickets.get(i).getTid(), Toast.LENGTH_LONG).show();
            }
        });

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
            ticket = itemView.findViewById(R.id.rl_ticket);
        }

    }

}
