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


public class AdapterProjRequest extends RecyclerView.Adapter<AdapterProjRequest.ItemViewHolder> {

    private List<Projects> arr;
    private OnItemClickListener onItemClickListener;

    public AdapterProjRequest() {
    }

    public AdapterProjRequest(List<Projects> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public AdapterProjRequest.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applicants,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProjRequest.ItemViewHolder holder, int position) {
        Projects projects = arr.get(position);

        String projName = projects.projName;
        holder.tv_name.setText(projName);

        String image = projects.getImageUrl();
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
