package com.example.myapp.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.ActivityHomeBinding
import com.example.myapp.modelData.UserInfo
import com.example.myapp.registrationAndLogin.GoogleLogInFragment
import com.example.myapp.viewModel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private  var item: UserInfo? = null
    private lateinit var binding: ActivityHomeBinding
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: NavController
    private lateinit var uid: String
    private lateinit var userUid: String
    private val navArgs: HomeActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        database = FirebaseDatabase.getInstance()

        val id = navArgs.uid

        ref = database.reference.child("accounts").child(id)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value.toString()
//                Toast.makeText(this@HomeActivity, auth.currentUser!!.uid, Toast.LENGTH_SHORT).show()
                binding.userName.setText(name)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.home_fragment_container_view) as NavHostFragment

        navController = navHostFragment.navController






        database = FirebaseDatabase.getInstance()
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)




        binding.weather.setOnClickListener {
            binding.apply {
                weather.setCardBackgroundColor(resources.getColor(R.color.blue))
                weatherT.setTextColor(resources.getColor(R.color.white))
                news.setCardBackgroundColor(resources.getColor(R.color.white))
                newsT.setTextColor(resources.getColor(R.color.black))
            }

            navController.navigate(R.id.weatherFragment)
        }
        binding.news.setOnClickListener {
            binding.apply {
                newsT.setTextColor(resources.getColor(R.color.white))
                news.setCardBackgroundColor(resources.getColor(R.color.blue))
                weatherT.setTextColor(resources.getColor(R.color.black))
                weather.setCardBackgroundColor(resources.getColor(R.color.white))
            }


            navController.navigate(R.id.newsFragment)
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId)     {
                    R.id.home_menu ->  {
                        binding.apply {
                            newsT.setTextColor(resources.getColor(R.color.white))
                            news.setCardBackgroundColor(resources.getColor(R.color.blue))
                            weatherT.setTextColor(resources.getColor(R.color.black))
                            weather.setCardBackgroundColor(resources.getColor(R.color.white))
                        }
                      navController.navigate(R.id.newsFragment)

                    }


                    R.id.profile -> {
                        val intent  = Intent(this@HomeActivity, ProfileActivity::class.java)
                        intent.putExtra("uid", id)
                        startActivity(intent)
                    }

                }
                return true
            }

        })




    }

     fun getUid(): String {
        return uid
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//
//        this.moveTaskToBack(false)
//        this.finish()
//
//    }

}