package com.example.modul6;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private TextInputEditText editTextNama;
    private TextInputEditText editTextJurusan;
    private TextInputEditText editTextEmail;
    private Button buttonAdd;
    private Button buttonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inisialisasi dari View
        editTextNama = (TextInputEditText) findViewById(R.id.editTextNama);
        editTextJurusan = (TextInputEditText) findViewById(R.id.editTextJurusan);
        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);
        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }
    //Dibawah ini merupakan perintah untuk Menambahkan Mahasiswa (CREATE)
    private void addMahasiswa(){
        final String nama = editTextNama.getText().toString().trim();
        final String jurusan = editTextJurusan.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        @SuppressLint("StaticFieldLeak")
        class AddMahasiswa extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_MHS_NAMA,nama);
                params.put(Konfigurasi.KEY_MHS_JURUSAN,jurusan);
                params.put(Konfigurasi.KEY_MHS_EMAIL,email);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                return res;
            }
        }
        AddMahasiswa ae = new AddMahasiswa();
        ae.execute();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addMahasiswa();
        }
        if(v == buttonView){
            startActivity(new Intent(MainActivity.this,Read.class));
        }
    }
}
