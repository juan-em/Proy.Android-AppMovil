package com.example.recicla

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_top_ten_all.*
import org.json.JSONException

var usuarios: MutableList<String> = mutableListOf()

class TopTenAll : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_ten_all)

        cargarProyectos()


        lista.addItemDecoration(DividerItemDecoration( this , DividerItemDecoration.VERTICAL))

        lista.layoutManager = LinearLayoutManager(this);

        var llenarLista = ArrayList<TopTen>()

        for(i in 0 until 10){
            llenarLista.add(TopTen( usuarios[i],
                BitmapFactory.decodeResource(resources , R.drawable.imaconfiguracion)))
        }

        for(i in usuarios.indices){
            llenarLista.add(TopTen( usuarios[i],
                BitmapFactory.decodeResource(resources , R.drawable.imaconfiguracion)))
        }
        val adapter = TopTen.AdaptadorElementos(llenarLista)
        lista.adapter = adapter

        //Log.e("valor0", usuarios[0])

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation);
        navigation?.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.action_calendar -> {
                    startActivity(Intent(this,CalendarEvents::class.java))
                    return@setOnItemSelectedListener true
                }
                R.id.action_top10->{
                    startActivity(Intent(this,TopTenAll::class.java))
                    return@setOnItemSelectedListener true
                }
                R.id.action_statics->{
                    startActivity(Intent(this,Estadisticas::class.java))
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

    fun cargarProyectos() {
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(com.example.recicla.R.string.urlAPI) + "/api/usuarios"
            val stringRequest = JsonArrayRequest(url,
                { response ->
                    try {
                        var username=""

                        for (i in 0 until response.length()) {
                            username =
                                response.getJSONObject(i).getString("username")

                            usuarios.add(username)
                        }

                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
        principal()
    }



    fun agregar(nombre: String) {
        var user = usuarios
        var name = nombre

        usuarios.add(name)

        lista.addItemDecoration(DividerItemDecoration( this , DividerItemDecoration.VERTICAL))

        lista.layoutManager = LinearLayoutManager(this);

        var llenarLista = ArrayList<TopTen>()

        for(i in 0 until 10){
            llenarLista.add(TopTen( usuarios[i],
                BitmapFactory.decodeResource(resources , R.drawable.imaconfiguracion)))
        }

        for(i in usuarios.indices){
            llenarLista.add(TopTen( usuarios[i],
                BitmapFactory.decodeResource(resources , R.drawable.imaconfiguracion)))
        }
        val adapter = TopTen.AdaptadorElementos(llenarLista)
        lista.adapter = adapter

        //Log.e("valor0", usuarios[0])
    }

    fun principal() {
        lista.addItemDecoration(DividerItemDecoration( this , DividerItemDecoration.VERTICAL))

        lista.layoutManager = LinearLayoutManager(this);

        var llenarLista = ArrayList<TopTen>()

        for(i in 0 until 10){
            llenarLista.add(TopTen( usuarios[i],
                BitmapFactory.decodeResource(resources , R.drawable.imaconfiguracion)))
        }

        for(i in usuarios.indices){
            llenarLista.add(TopTen( usuarios[i],
                BitmapFactory.decodeResource(resources , R.drawable.imaconfiguracion)))
        }
        val adapter = TopTen.AdaptadorElementos(llenarLista)
        lista.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}