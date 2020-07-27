package com.redveloper.android_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity(), View.OnClickListener {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var position : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        if (intent.extras != null){
            this.position = intent.getIntExtra("keyPosition", 0)

            getData()
        }

        btn_update.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_update -> updateData(
                et_nama.text.toString(),
                et_warna.text.toString()
            )
            R.id.btn_delete -> deleteData()
        }
    }

    fun updateData(nama : String, warna: String){
        val ref = database.getReference("barangs").child(position.toString())
        val mapData : HashMap<String, String> = HashMap()
        mapData.put("nama", nama)
        mapData.put("warna", warna)
        ref.updateChildren(mapData as Map<String, Any>).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                toMain()
            } else {
                Toast.makeText(this, "Gagal update data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getData(){
        val ref = database.getReference("barangs").child(position.toString())
        ref.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val nama : String = snapshot.child("nama").getValue().toString()
                val warna : String = snapshot.child("warna").getValue().toString()

                showData(nama, warna)
            }
        })
    }

    fun showData(nama : String, warna : String){
        et_nama.setText(nama)
        et_warna.setText(warna)
    }

    fun deleteData(){
        val ref = database.getReference("barangs").child(position.toString())
        ref.removeValue().addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                toMain()
            } else {
                Toast.makeText(this, "Gagal delete data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun toMain(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}
