package com.redveloper.android_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), View.OnClickListener {

    val databse : FirebaseDatabase = FirebaseDatabase.getInstance()
    var pos : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        getLastKey()
        btn_add.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_add -> tambahData(
                et_nama.text.toString(),
                et_warna.text.toString()
            )
        }
    }

    fun tambahData(nama : String, warna: String){
        if (pos != null){
            val ref : DatabaseReference = databse.getReference("barangs").child((pos!! + 1).toString())
            val data : HashMap<String, String> = HashMap()
            data.put("nama", nama)
            data.put("warna", warna)
            ref.setValue(data).addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "gagal menambah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getLastKey(){
        val ref : DatabaseReference = databse.getReference("barangs")
        val query : Query = ref.orderByKey().limitToLast(1)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    Log.i("dataKey", data.key.toString())
                    pos = data.key?.toInt()
                }
            }
        })
    }
}
