package com.redveloper.android_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
    }

    fun getData(){
        val ref : DatabaseReference = database.getReference("barangs")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val listModel : ArrayList<Model> = ArrayList()
                for (data in snapshot.children){
                    val nama : String = data.child("nama").getValue().toString()
                    val warna : String = data.child("warna").getValue().toString()

                    listModel.add(Model(nama, warna))
                }

                showData(listModel)

            }
        })
    }

    fun showData(listModel: ArrayList<Model>) {
        val viewManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        val viewAdapter : Adapter = Adapter(listModel)

        recyclerview.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


}
