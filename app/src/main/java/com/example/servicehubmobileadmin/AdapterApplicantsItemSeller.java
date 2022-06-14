package com.example.servicehubmobileadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterApplicantsItemSeller extends RecyclerView.Adapter<AdapterApplicantsItemSeller.ItemViewHolder> {

    private List<ApplicantsSeller> arr;
    private OnItemClickListener onItemClickListener;

    public AdapterApplicantsItemSeller() {
    }

    public AdapterApplicantsItemSeller(ArrayList<ApplicantsSeller> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterApplicantsItemSeller.ItemViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applicants,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ApplicantsSeller applicantsSeller = arr.get(position);

        String name = applicantsSeller.firstName + " " + applicantsSeller.lastName;
        holder.tv_name.setText(name);

        String image = applicantsSeller.selfieUrl;
        Picasso.get().load(image).into(holder.iv_techPhoto);

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView iv_techPhoto;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            iv_techPhoto = itemView.findViewById(R.id.iv_techPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
