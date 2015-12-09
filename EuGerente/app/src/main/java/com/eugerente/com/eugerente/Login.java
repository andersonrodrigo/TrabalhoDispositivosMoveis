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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciaBanco();

        Button btnNovoUsuario = (Button) findViewById(R.id.btnNovoUsuario);
        btnNovoUsuario.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), CadastroUsuario.class);
                startActivity(it);
            }

        });
        Button btnEnter = (Button) findViewById(R.id.btnEntrar);
        btnEnter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText login = (EditText) findViewById(R.id.edtLogin);
                EditText senha = (EditText) findViewById(R.id.edtSenha);
                if (validaUsuario(login.getText().toString(), senha.getText().toString())) {
                    Intent it = new Intent(v.getContext(), Inicial.class);
                    Bundle valor = new Bundle();
                    valor.putString("login", login.getText().toString());
                    it.putExtras(valor);
                    startActivity(it);
                } else {
                    Toast.makeText(Login.this, "Usuário ou Senha Inválidos!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     *
     * @return
     */
    private Boolean validaUsuario(String login, String senha){

        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()){
            do{
                if (c.getString(2).equals(login)
                        && c.getString(3).equals(senha)){
                    return true;
                }
            }while (c.moveToNext());
        }
        db.close();
        return false;
    }

    private void iniciaBanco() {
        try{
            String destPath = "/data/data/"+getPackageName()+"/databases/MyDB";
            File f = new File(destPath);
            if (!f.exists()){
                CopyDB(getBaseContext().getAssets().open("/databases/MyDB"),new FileOutputStream(destPath));
            }
        }catch (Exception e){

        }

    }


    /**
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public void CopyDB (InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer))>0){
            outputStream.write(buffer,0,length);
        }
        inputStream.close();
        outputStream.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
