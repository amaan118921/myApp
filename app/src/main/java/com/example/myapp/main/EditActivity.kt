package com.example.myapp.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.example.myapp.R
import com.example.myapp.databinding.ActivityEditAcitivtyBinding
import com.example.myapp.modelData.UpdatedUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rilixtech.widget.countrycodepicker.CountryCodePicker

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAcitivtyBinding
    private lateinit var ccp: CountryCodePicker
    private lateinit var name: String
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        ccp = binding.ccp
        ccp.registerPhoneNumberTextView(binding.getPhone)

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val uri = intent.getStringExtra("uri")
        binding.name.setText(name)
        binding.getPhone.setText(phone)

        binding.cancel.setOnClickListener {
            finish()
        }



        binding.save.setOnClickListener {
           val newName = binding.name.text.toString()
            val newNumber = binding.getPhone.text.toString()

            if(newName != name || newNumber != phone) {
                binding.pb.visibility = View.VISIBLE
                val user = auth.currentUser
                ref = database.reference.child("accounts").child(user!!.uid)
                val updatedUser = UpdatedUser(newName, email!!, uri!!, newNumber)
                ref.setValue(updatedUser).addOnCompleteListener(object: OnCompleteListener<Void> {
                    override fun onComplete(p0: Task<Void>) {
                        if(p0.isSuccessful) {
                            binding.pb.visibility = View.INVISIBLE
                            finish()
                        }
                    }

                })
            }
            else {
                finish()
            }

        }





    }
}