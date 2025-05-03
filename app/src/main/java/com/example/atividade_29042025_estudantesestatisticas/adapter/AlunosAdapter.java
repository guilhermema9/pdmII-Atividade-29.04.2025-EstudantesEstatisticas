package com.example.atividade_29042025_estudantesestatisticas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade_29042025_estudantesestatisticas.R;

import java.util.List;

public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.ViewHolder>{

    private List<String> alunosList;
    private OnItemClickListener listener;

    public AlunosAdapter(List<String> alunosList) {
        this.alunosList = alunosList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_estudante, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextViewNome().setText(alunosList.get(position));
    }

    @Override
    public int getItemCount() {
        return alunosList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeRecyclerView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null){
                        listener.onItemClick(position);
                    }
                }
            });
        }

        public TextView getTextViewNome() {
            return textViewNome;
        }
    }
}