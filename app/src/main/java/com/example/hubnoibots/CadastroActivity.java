package com.example.hubnoibots;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CadastroActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "compromisso_id";
    private static final long SEM_ID = -1L;

    private EditText edtTitulo;
    private EditText edtData;
    private EditText edtHorario;
    private EditText edtDescricao;
    private TextView txtTituloFormulario;

    private AppDataBase banco;
    private ExecutorService executor;
    private long compromissoId = SEM_ID;
    private com.example.hubnoibots.Compromisso compromissoAtual;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        txtTituloFormulario = findViewById(R.id.txtTituloFormulario);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtData = findViewById(R.id.edtData);
        edtHorario = findViewById(R.id.edtHorario);
        edtDescricao = findViewById(R.id.edtDescricao);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnCancelar = findViewById(R.id.btnCancelar);
        banco = AppDataBase.getDatabase(this);
        executor = Executors.newSingleThreadExecutor();

        compromissoId = getIntent().getLongExtra(EXTRA_ID, SEM_ID);
        if(compromissoId != SEM_ID){
            txtTituloFormulario.setText(R.string.titulo_editar);
            carregarCompromisso();
        }
        btnSalvar.setOnClickListener(view ->salvar());
        btnCancelar.setOnClickListener(view ->finish());
    }

    private void carregarCompromisso(){
        executor.execute(()-> {
            com.example.hubnoibots.Compromisso encontrado = banco.compromissoDao().buscarPorId(compromissoId);
            runOnUiThread(()-> {
                if(encontrado == null){
                    Toast.makeText(this,R.string.registro_nao_encontrado, Toast.LENGTH_SHORT).
                            show();
                    finish();
                    return;
                }
                compromissoAtual = encontrado;
                edtTitulo.setText(encontrado.getTitulo());
                edtData.setText(encontrado.getData());
                edtHorario.setText(encontrado.getHorario());
                edtDescricao.setText(encontrado.getDescricao());
            });
        });
    }

    private boolean validarCampos(){
        String titulo = edtTitulo.getText().toString().trim();
        String data = edtData.getText().toString().trim();
        String horario = edtHorario.getText().toString().trim();
        if(TextUtils.isEmpty(titulo)){
            edtTitulo.setError(getString(R.string.erro_titulo));
            edtTitulo.requestFocus();
            return false;
        }
        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            edtData.setError(getString(R.string.erro_data));
            edtData.requestFocus();
            return false;
        }
        if(!horario.matches("([01]\\d|2[0-3]):[0-5]\\d")){
            edtHorario.setError(getString(R.string.erro_horario));
            edtHorario.requestFocus();
            return false;
        }
        return true;
    }

    private void salvar(){
        if(!validarCampos()){
            return;
        }
        boolean modoEdicao = compromissoId != SEM_ID;
        if(compromissoAtual == null){
            compromissoAtual = new com.example.hubnoibots.Compromisso();
        }
        compromissoAtual.setTitulo(edtTitulo.getText().toString().trim());
        compromissoAtual.setData(edtData.getText().toString().trim());
        compromissoAtual.setHorario(edtHorario.getText().toString().trim());
        compromissoAtual.setDescricao(edtDescricao.getText().toString().trim());
        executor.execute(()->{
            if(modoEdicao){
                banco.compromissoDao().atualizar(compromissoAtual);
            }else{
                banco.compromissoDao().inserir(compromissoAtual);
            }
            runOnUiThread(()->{
                int mensagem = modoEdicao
                        ? R.string.mensagem_atualizado
                        : R.string.mensagem_cadastrado;
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
