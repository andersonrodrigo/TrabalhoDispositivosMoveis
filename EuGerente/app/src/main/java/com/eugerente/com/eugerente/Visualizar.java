package com.eugerente.com.eugerente;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Visualizar extends Activity {

    String[] atividades;
    DBAdapter db = new DBAdapter(this);
    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        Intent it = getIntent();
        if (it != null) {
            Bundle valor = it.getExtras();
            String nome = valor.getString("login");
           // TextView login = (TextView) findViewById(R.id.textLogin);


         //   login.setText("Usuário: "+nome); /**
          List<Atividade> lista = null;
            try {
                lista =  montaListaAtividades(nome);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),
                        "Não temos atividades!!!!!!", Toast.LENGTH_SHORT).show();
            }
            if (lista!=null && !lista.isEmpty()){

                String[] atividades = new String[lista.size()];

                int i = 0;
                for (Atividade itemAtividade:lista) {
                    atividades[i] = "("+ itemAtividade.getId()+") "+itemAtividade.getNome()+" - "+ itemAtividade.getStatus();
                  //  Toast.makeText(getApplicationContext(),
                    //        "Atividade"+ atividades[i], Toast.LENGTH_SHORT).show();
                    i++;
                }
                GridView grid = (GridView) findViewById(R.id.gridViewAtividades);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, atividades);

                grid.setAdapter(adapter);

                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        String linha =  ((TextView) v).getText().toString();
                        String idBanco =  linha.substring(linha.indexOf("(") + 1, linha.indexOf(")"));
                        Toast.makeText(getApplicationContext(),
                                idBanco, Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(v.getContext(), CadastroAtividade.class);
                        Bundle valor = new Bundle();
                        valor.putString("idAtividade", idBanco);
                        it.putExtras(valor);
                        startActivity(it);
                        finish();
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(),
                      "Não temos atividades!!!!!!", Toast.LENGTH_SHORT).show();
            }


        }


        Button btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     *
     * @param login
     * @return
     */
    private List<Atividade> montaListaAtividades(String login){

        List<Atividade> listaAtividade = new ArrayList<Atividade>();

        try {
            db.open();

            Cursor mCursor  =  db.getAllAtividades(login);

            if (mCursor != null) {

                mCursor.moveToFirst();
                int i=0;
                do{
                    Atividade atividade = new Atividade();
                    atividade.setId(Long.valueOf(mCursor.getInt(0)));
                    atividade.setNome(mCursor.getString(1));
                    atividade.setLogin(mCursor.getString(2));
                    atividade.setObservacao(mCursor.getString(4));
                    atividade.setStatus(mCursor.getString(3));
                    if (login.equals(atividade.getLogin())){
                        listaAtividade.add(atividade);
                    }
                }while (mCursor.moveToNext());
            }else{
                Toast.makeText(getApplicationContext(),
                       "Cursor voltou null :(", Toast.LENGTH_SHORT).show();
            }
            db.close();
         }catch (Exception e){
           // Toast.makeText(getApplicationContext(),
            //        e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
        return listaAtividade;
    }


}
