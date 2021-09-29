package com.example.myapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.myapp.MainActivity
import com.example.myapp.R
import com.example.myapp.databinding.ActivityProfileBinding
import com.example.myapp.room.DataInfo
import com.example.myapp.modelData.UserInfo
import com.example.myapp.room.DatabaseApplication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private  var profile: UserInfo? = null
    private lateinit var list: MutableList<DataInfo>
    private lateinit var live: LiveData<DataInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = mutableListOf()
        auth = FirebaseAuth.getInstance()



        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val user = auth.currentUser

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        uid = intent.getStringExtra("uid")!!


//        val dao = (this.application as DatabaseApplication).database.itemDao()

//        lifecycleScope.launch {
//            live = dao.getUserDetails().asLiveData()
//        }





//
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//              val profile = snapshot.getValue(UserInfo::class.java)
//                if(profile != null) {
//                    if(profile.name.isNotEmpty()) {
//                        Toast.makeText(this@ProfileActivity, "Name Not Empty", Toast.LENGTH_SHORT).show()
//                        binding.pb.visibility = View.INVISIBLE
//                    }
//                    else  {
//                        Toast.makeText(this@ProfileActivity, "Name Empty", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//                else  {
//                    Toast.makeText(this@ProfileActivity, "Null", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@ProfileActivity, "Null and Cancelled", Toast.LENGTH_SHORT).show()
//            }
//
//        })



        binding.logout.setOnClickListener {
            googleSignInClient.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }




            binding.pb.visibility = View.INVISIBLE
            Picasso.get().load(user!!.photoUrl.toString()).into(binding.profileImage)
            binding.name.setText(user.displayName)
            binding.email.setText(user.email)
            binding.phone.setText("9123654582")









    }


}