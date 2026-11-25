package com.example.hubnoibots;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CompromissoAdapter extends RecyclerView.Adapter<CompromissoAdapter.CompromissoViewHolder>{
    public interface OnItemListener{
        void aoEditar(Compromisso compromisso);
        void aoExcluir(Compromisso compromisso);
    }
    private final List<Compromisso> itens = new ArrayList<>();
    private final OnItemListener listener;

    public CompromissoAdapter(OnItemListener listener){
        this.listener = listener;
    }
    public void atualizarDados(List<Compromisso> novosItens){
        itens.clear();
        itens.addAll(novosItens);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompromissoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compromisso,parent,
                false);
        return new CompromissoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CompromissoViewHolder holder, int position){
        holder.vincular(itens.get(position), listener);
    }
    @Override
    public int getItemCount(){
        return itens.size();
    }

    static class CompromissoViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtTitulo;
        private final TextView txtDataHorario;
        private final TextView txtDescricao;
        private final Button btnEditar;
        private final Button btnExcluir;

        public CompromissoViewHolder(@NonNull View itemView){
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtItemTitulo);
            txtDataHorario = itemView.findViewById(R.id.txtItemDataHorario);
            txtDescricao = itemView.findViewById(R.id.txtItemDescricao);
            btnEditar = itemView.findViewById(R.id.btnItemEditar);
            btnExcluir = itemView.findViewById(R.id.btnItemExcluir);
        }
        void vincular(Compromisso compromisso, OnItemListener listener) {
            txtTitulo.setText(compromisso.getTitulo());
            txtDataHorario.setText(itemView.getContext().getString(
                    R.string.data_horario, compromisso.getData(), compromisso.getHorario()
            ));
            String descricao = compromisso.getDescricao();
            if(descricao == null || descricao.trim().isEmpty()) {
                txtDescricao.setText(R.string.sem_descricao);
            }else{
                txtDescricao.setText(descricao);
            }
            btnExcluir.setOnClickListener(view->listener.aoExcluir(compromisso));
            btnEditar.setOnClickListener(view->listener.aoEditar(compromisso));
        }
    }
}
