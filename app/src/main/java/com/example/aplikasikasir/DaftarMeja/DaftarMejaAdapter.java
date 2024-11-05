package com.example.aplikasikasir.DaftarMeja;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasikasir.R;

import java.util.ArrayList;
import java.util.List;


public class DaftarMejaAdapter extends RecyclerView.Adapter<DaftarMejaAdapter.GridViewHolder>{

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(ModelMeja data);
    }


    public static  ArrayList<ModelMeja> modelMejas;
    Activity context;
    private final ArrayList<ModelMeja> modelMejaArrayList;
    public DaftarMejaAdapter(ArrayList<ModelMeja> modelMejas, Activity context) {
        this.modelMejas = modelMejas;
        this.context = context;

        modelMejaArrayList = new ArrayList<>(DaftarMejaAdapter.modelMejas);
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_daftar_meja, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {

        ModelMeja modelMeja = modelMejas.get(holder.getAdapterPosition());

        holder.tvNomorMeja.setText(String.valueOf(modelMeja.getJmlhMeja()));

        holder.cvListMeja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(modelMejas.get(holder.getAdapterPosition()));
            }
        });

//        holder.ivListMeja.setBackgroundResource(R.drawable.backgroundtema);
    }

    @Override
    public int getItemCount() {
        return modelMejas.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomorMeja;
        LinearLayout llListMeja;
//        ImageView ivListMeja;
        CardView cvListMeja;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            llListMeja = itemView.findViewById(R.id.llListMeja);
            tvNomorMeja = itemView.findViewById(R.id.tvNomorMeja);
            cvListMeja = itemView.findViewById(R.id.cvListMeja);
//            ivListMeja = itemView.findViewById(R.id.ivListMeja);

        }
    }

    public Filter getFilter() {
        return filterNomorMeja;
    }

    private final Filter filterNomorMeja = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ModelMeja> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(modelMejaArrayList);
            }else{

                String filterpattern = constraint.toString().toLowerCase().trim();

                for (ModelMeja item : modelMejaArrayList){
                    if (item.getJmlhMeja().toLowerCase().contains(filterpattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelMejas.clear();
            modelMejas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
