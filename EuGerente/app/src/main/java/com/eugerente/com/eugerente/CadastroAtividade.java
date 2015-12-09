package com.eugerente.com.eugerente;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Currency;

public class CadastroAtividade extends Activity {


    DBAdapter db = new DBAdapter(this);
    String login="";

    private String idAtividade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);
        Intent it = getIntent();
        if (it != null) {
            Bundle valor = it.getExtras();
            login = valor.getString("login");
            idAtividade = valor.getString("idAtividade");
            if (idAtividade!=null){
                EditText nomeAtividade = (EditText) findViewById(R.id.edtNomeAtividade);
                EditText descAtividade = (EditText) findViewById(R.id.edtDescAtividade);
                EditText observacoes = (EditText) findViewById(R.id.edtObsAtividade);
                RadioButton radioPendente  = (RadioButton) findViewById(R.id.radioButton);
                RadioButton radioTerminado  = (RadioButton) findViewById(R.id.radioButton2);
                 db.open();
                Cursor cursor = db.getAtividade(Integer.parseInt(idAtividade));
                nomeAtividade.setText(cursor.getString(1));
                descAtividade.setText(cursor.getString(4));
                observacoes.setText(cursor.getString(5));
                if (cursor.getString(3).equals("Pendente")){
                    radioPendente.setChecked(true);
                }else  if (cursor.getString(3).equals("Terminado")){
                    radioTerminado.setChecked(true);
                }
                db.close();

            }
        }

        RadioButton radioPendente  = (RadioButton) findViewById(R.id.radioButton);
        radioPendente.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RadioButton radioPendenteCorrente  = (RadioButton) findViewById(R.id.radioButton);
                if (radioPendenteCorrente.isChecked()){
                    RadioButton radioTerminado  = (RadioButton) findViewById(R.id.radioButton2);
                    radioTerminado.setChecked(false);
                }
            }});

        RadioButton radioTerminado  = (RadioButton) findViewById(R.id.radioButton2);
        radioTerminado.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                RadioButton radioTerminado  = (RadioButton) findViewById(R.id.radioButton2);
                if (radioTerminado.isChecked()){
                    RadioButton radioPendente  = (RadioButton) findViewById(R.id.radioButton);
                    radioPendente.setChecked(false);
                }
            }});
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                EditText nomeAtividade = (EditText) findViewById(R.id.edtNomeAtividade);
                EditText descAtividade = (EditText) findViewById(R.id.edtDescAtividade);
                EditText observacoes = (EditText) findViewById(R.id.edtObsAtividade);
                RadioButton radioPendente  = (RadioButton) findViewById(R.id.radioButton);
                RadioButton radioTerminado  = (RadioButton) findViewById(R.id.radioButton2);
                String status = "";
                if (radioPendente.isChecked()){
                    status = "Pendente";
                }
                if (radioTerminado.isChecked()){
                    status ="Terminado";
                }
                if (status.equals("")){
                    status = "Indeterminado";
                }
                db.open();
                if (idAtividade!=null){
                    db.updateAtividade(Integer.parseInt(idAtividade),nomeAtividade.getText().toString(), descAtividade.getText().toString(), observacoes.getText().toString(), status);
                    Toast.makeText(CadastroAtividade.this, "Atualizado Com sucesso", Toast.LENGTH_LONG).show();

                }else{
                    db.insertAtividade(nomeAtividade.getText().toString(),login,descAtividade.getText().toString(),observacoes.getText().toString(),status);
                    Toast.makeText(CadastroAtividade.this, "Inserido Com sucesso", Toast.LENGTH_LONG).show();

                }
                db.close();
               finish();
            }

        });

    }

}
