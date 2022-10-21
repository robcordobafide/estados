package com.estados
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.estados.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Definimos un objeto para la utentificacion de Firebase
    private lateinit var auth : FirebaseAuth

    //Definimos un objeto para acceder a los elementos de pantalla xml
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la autentificascion
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth



        // Definir el onmclick del boton register
        binding.btRegister.setOnClickListener{ haceRegistro() }


        // Definir el onmclick del boton register
        binding.btLogin.setOnClickListener{ haceLogin() }

    }


    private fun haceRegistro() {
        //Recupero info
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()


        // uTILIZO AUTH PARA HACER EL REGISTRO
        auth.createUserWithEmailAndPassword(email,clave).addOnCompleteListener(this) {task ->
            if(task.isSuccessful) {
                // se creo el usuario
                Log.d("Registrandose","Se registro")
                val user = auth.currentUser
                refresca(user)
            }else {
                //no se creo el usuario
                Log.d("Registrandose","Error de registro")
                Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                refresca(null)
                //prueba
            }

        }

    }

    private fun refresca(user: FirebaseUser?) {
        if (user != null ) {
            val intent = Intent(this,Principal::class.java)
            startActivity(intent)
        }
    }

    private fun haceLogin() {
        //Recupero info
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()


        // uTILIZO AUTH PARA HACER EL REGISTRO
        auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful) {
                    // se creo el usuario
                    Log.d("Autenticando","Se autentico")
                    val user = auth.currentUser
                    refresca(user)
                }else {
                    //no se creo el usuario
                    Log.d("Autenticando","Error de autentificacion")
                    Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                    refresca(null)
                    //prueba
                }

            }
    }

    // Estoi se ejecuta cuando se presenta el app en la pa nntalla, valida si alguien esta logueado
    public override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        refresca(usuario)
    }

}