package com.redveloper.android_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_katasandi.text.toString().trim()
            loginEmailPassword(email, password)
        }
    }

    fun loginAnonymus(){
        auth.signInAnonymously().addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Gagal Login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginEmailPassword(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            task ->
            if (task.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Gagal Login", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
