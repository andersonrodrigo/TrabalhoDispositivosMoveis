package com.eugerente.com.eugerente;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Inicial extends Activity {


    DBAdapter db = new DBAdapter(this);
    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        Intent it = getIntent();
        if (it != null) {
            Bundle valor = it.getExtras();
            String nome = valor.getString("login");
            TextView login = (TextView) findViewById(R.id.textLogin);
            setLogin(nome);
          //  Toast.makeText(Inicial.this, getLogin(), Toast.LENGTH_LONG).show();

            login.setText("Bem Vindo: "+nome);

        }





        Button btnNovaAtividade = (Button) findViewById(R.id.btnNovaAtividade);
        btnNovaAtividade.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), CadastroAtividade.class);
                Bundle valor = new Bundle();
                TextView login = (TextView) findViewById(R.id.textLogin);
                valor.putString("login", getLogin());
                it.putExtras(valor);
                startActivity(it);
            }

        });

        Button btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
             //   Toast.makeText(Inicial.this, getLogin(), Toast.LENGTH_LONG).show();

                Intent it = new Intent(v.getContext(), Visualizar.class);
                Bundle valor = new Bundle();
                TextView login = (TextView) findViewById(R.id.textLogin);
              //  Toast.makeText(Inicial.this, getLogin(), Toast.LENGTH_LONG).show();

                valor.putString("login", getLogin());
                it.putExtras(valor);
                startActivity(it);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
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
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
