package com.redveloper.android_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*

class AddActivity : AppCompatActivity() {

    val databse : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        getLastKey()
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
                }
            }
        })
    }
}
