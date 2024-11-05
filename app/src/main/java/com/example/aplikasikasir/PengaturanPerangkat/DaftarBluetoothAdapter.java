package com.example.aplikasikasir.PengaturanPerangkat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasikasir.R;

import java.util.ArrayList;

public class DaftarBluetoothAdapter extends  RecyclerView.Adapter<DaftarBluetoothAdapter.ListViewHolder>{
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    private ArrayList<ModelListBluetooth> modelListBluetooths;
    public DaftarBluetoothAdapter(ArrayList<ModelListBluetooth> modelListBluetooths) {
        this.modelListBluetooths = modelListBluetooths;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_bluetooth, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ModelListBluetooth modelListBluetooth = modelListBluetooths.get(position);
        holder.tvListNamaBluetooth.setText(modelListBluetooth.getNama_bluetooth());
        holder.tvListAddressBluetooth.setText("("+modelListBluetooth.getAddress_bluetooth()+")");

        holder.llListBluetooths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(modelListBluetooths.get(holder.getAdapterPosition()));

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelListBluetooths.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{


        TextView tvListNamaBluetooth, tvListAddressBluetooth;
        LinearLayout llListBluetooths;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvListNamaBluetooth = itemView.findViewById(R.id.tvListNamaBluetooth);
            tvListAddressBluetooth = itemView.findViewById(R.id.tvListAddressBluetooth);
            llListBluetooths = itemView.findViewById(R.id.llListBluetooths);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(ModelListBluetooth data);
    }
}
