package com.eugerente.com.eugerente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_SENHA = "senha";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE_USUARIO = "usuario";
    private static final String DATABASE_TABLE_ATIVIDADE = "atividades";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE1 =
        "create table usuario (_id integer primary key autoincrement, "
        + "nome text not null, login text not null , senha text not null);";
    private static final String DATABASE_CREATE2 =  " create table atividades (_id integer primary key autoincrement, "
        +" nome text not null, login text not null , status text not null, descricao text  , observacao text );";
        
    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
        	try {
        		db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
            } catch (SQLException e) {
        		e.printStackTrace();
        	}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }    

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a contact into the database---
    public long insertUsuario(String nome, String login, String senha){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_LOGIN, login);
        initialValues.put(KEY_SENHA, senha);
        return db.insert(DATABASE_TABLE_USUARIO, null, initialValues);
    }

    public long insertAtividade(String nome,String login, String descricao, String observacao,String status){
        ContentValues initialValues = new ContentValues();
        initialValues.put("nome", nome);
        initialValues.put("login", login);
        initialValues.put("status", status);
        initialValues.put("descricao", descricao);
        initialValues.put("observacao", observacao);

        return db.insert(DATABASE_TABLE_ATIVIDADE, null, initialValues);
    }


    //---deletes a particular contact---
    public boolean deleteContact(long rowId) 
    {
        return db.delete(DATABASE_TABLE_USUARIO, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE_USUARIO, new String[] {KEY_ROWID, KEY_NOME,
                KEY_LOGIN,KEY_SENHA}, null, null, null, null, null);
    }
    public Cursor getAllAtividades(String login)
    {


        return db.query(DATABASE_TABLE_ATIVIDADE, new String[] {KEY_ROWID, "nome","login","status","descricao","observacao"}, null,
                null, null, null, null);

    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_USUARIO, new String[] {KEY_ROWID,
                KEY_NOME, KEY_LOGIN}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    /**
     *
     * @param rowId
     * @return
     * @throws SQLException
     */
    public Cursor getAtividade(long rowId) throws SQLException{
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_ATIVIDADE, new String[] {KEY_ROWID,
                                KEY_NOME, KEY_LOGIN,"status","descricao","observacao"}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     *
     * @param rowId
     * @param name
     * @param email
     * @return
     */
    public boolean updateContact(long rowId, String name, String email){
        ContentValues args = new ContentValues();
        args.put(KEY_NOME, name);
        args.put(KEY_LOGIN, email);
        return db.update(DATABASE_TABLE_USUARIO, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     *
     * @param rowId
     * @param name
     * @param descricao
     * @param observacao
     * @param status
     * @return
     */
    public boolean updateAtividade(long rowId, String name, String descricao, String observacao, String status){
        ContentValues args = new ContentValues();
        args.put(KEY_NOME, name);
        args.put("descricao", descricao);
        args.put("observacao", observacao);
        args.put("status", status);
        return db.update(DATABASE_TABLE_ATIVIDADE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
