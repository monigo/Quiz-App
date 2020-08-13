package com.os;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.Viewholder> {

    private String category ;
    private List<SubCategoryModel> subcategoryModelList ;

    public SubCategoryAdapter(List<SubCategoryModel> subCategoryModelList , String category) {
        this.subcategoryModelList = subCategoryModelList;
        this.category = category ;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new SubCategoryAdapter.Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.Viewholder holder, int position) {
        holder.setData(subcategoryModelList.get(position).getUrl(),subcategoryModelList.get(position).getName(),subcategoryModelList.get(position).getSets());
    }

    @Override
    public int getItemCount() {
        return subcategoryModelList.size();
    }

    class Viewholder extends  RecyclerView.ViewHolder{

        private CircleImageView imageView ;
        private TextView title;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.title);
        }

        private void setData(String url , final String subCategory ,final int sets){
            Glide.with(itemView.getContext()).load(url).into(imageView);
            this.title.setText(subCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent setIntent = new Intent(itemView.getContext(),SetsActivity.class);
                    setIntent.putExtra("category",category);
                    setIntent.putExtra("subCategory",subCategory);
                    setIntent.putExtra("sets",sets);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }
}
