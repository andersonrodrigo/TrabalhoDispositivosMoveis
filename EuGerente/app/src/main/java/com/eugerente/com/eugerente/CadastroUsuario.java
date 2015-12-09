package com.eugerente.com.eugerente;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroUsuario extends Activity {

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        Button btnCadastro = (Button) findViewById(R.id.btnCadastro);
        btnCadastro.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText nome = (EditText) findViewById(R.id.edtLogin);
                EditText login = (EditText) findViewById(R.id.edtLogin);
                EditText senha = (EditText) findViewById(R.id.edtSenha);
                db.open();
                if (verificaLoginCadastrado(login.getText().toString())) {
                    Toast.makeText(CadastroUsuario.this, "Login existente! Informe outro Login", Toast.LENGTH_LONG).show();
                } else {
                    Intent it = new Intent(v.getContext(), Login.class);
                    db.insertUsuario(nome.getText().toString(), login.getText().toString(), senha.getText().toString());
                    Toast.makeText(CadastroUsuario.this, "Inserido Com sucesso", Toast.LENGTH_LONG).show();
                    startActivity(it);
                }
                db.close();
            }
        });

    }

    /**
     *
     * @param login
     * @return
     */
    public Boolean verificaLoginCadastrado(String login){
         Cursor c = db.getAllContacts();
        boolean achou = false;
        if (c.moveToFirst()){
            do{
                if (c.getString(2).equals(login)){
                    achou = true;
                }
            }while (c.moveToNext());
        }
        return achou;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
