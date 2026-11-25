package com.example.hubnoibots;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaActivity extends AppCompatActivity {
    private RecyclerView recyclerCompromisso;
    private TextView txtListaVazia;
    private CompromissoAdapter adapter;
    private AppDataBase banco;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerCompromisso = findViewById(R.id.recyclerCompromisso);
        txtListaVazia = findViewById(R.id.txtListaVazia);
        Button btnNovo = findViewById(R.id.btnNovoNaLista);
        banco = AppDataBase.getDatabase(this);
        executor = Executors.newSingleThreadExecutor();

        adapter = new CompromissoAdapter(new CompromissoAdapter.OnItemListener() {
            @Override
            public void aoEditar(Compromisso compromisso) {
                abrirEdicao(compromisso);
            }

            @Override
            public void aoExcluir(Compromisso compromisso) {
                confirmarExclusao(compromisso);
            }
        });
        recyclerCompromisso.setLayoutManager(new LinearLayoutManager(this));
        recyclerCompromisso.setAdapter(adapter);
        btnNovo.setOnClickListener(view->abrirCadastro());

    }

    @Override
    protected void onResume(){
        super.onResume();
        carregarLista();
    }
    private  void carregarLista(){
        executor.execute(()->{
            List<Compromisso> compromissos = banco.compromissoDao().listarTodos();
            runOnUiThread(()->{
                adapter.atualizarDados(compromissos);
                boolean vazia = compromissos.isEmpty();
                txtListaVazia.setVisibility(vazia ? View.VISIBLE : View.GONE);
                recyclerCompromisso.setVisibility(vazia ? View.GONE : View.VISIBLE);
            });
        });
    }

    private void abrirEdicao(Compromisso compromisso){
        Intent intent = new Intent(this, CadastroActivity.class);
        intent.putExtra(CadastroActivity.EXTRA_ID, compromisso.getId());
        startActivity(intent);
    }
    private void abrirCadastro(){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
    private void confirmarExclusao(Compromisso compromisso){
        new AlertDialog.Builder(this).setTitle(R.string.confirmar_exclusao).setMessage(
                getString(R.string.texto_exclusao, compromisso.getTitulo())).setPositiveButton(
                R.string.sim_excluir,(dialog, which)->excluir(compromisso)
        ).setNegativeButton(R.string.nao_cancelar, null).show();

    }
    private void excluir(Compromisso compromisso){
        executor.execute(()->{
            banco.compromissoDao().excluir(compromisso);
            runOnUiThread(()->{
                Toast.makeText(this,R.string.mensagem_excluido, Toast.LENGTH_SHORT).show();
                carregarLista();
            });
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        executor.shutdown();
    }
}
