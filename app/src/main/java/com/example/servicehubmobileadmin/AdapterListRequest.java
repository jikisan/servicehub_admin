package com.example.servicehubmobileadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListRequest extends RecyclerView.Adapter<AdapterListRequest.ItemViewHolder> {

    private List<Listings> arr;
    private OnItemClickListener onItemClickListener;

    public AdapterListRequest() {
    }

    public AdapterListRequest(List<Listings> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public AdapterListRequest.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterListRequest.ItemViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applicants,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListRequest.ItemViewHolder holder, int position) {
        Listings listings = arr.get(position);

        String listName = listings.listName;
        holder.tv_name.setText(listName);

        String image = listings.getImageUrl();
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
