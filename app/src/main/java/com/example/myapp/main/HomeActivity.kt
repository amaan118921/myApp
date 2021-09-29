package com.example.myapp.main

import android.annotation.SuppressLint
import android.content.Intent
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

    private val navArgs: HomeActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

//        if(user == null) {
//            navController.navigate(R.id.googleLogInFragment)
//        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.home_fragment_container_view) as NavHostFragment

        navController = navHostFragment.navController



        val name = navArgs.name
        uid = navArgs.uid




        database = FirebaseDatabase.getInstance()
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        binding.userName.setText(name)

        binding.weather.setOnClickListener {
            navController.navigate(R.id.weatherFragment)
        }
        binding.news.setOnClickListener {
            navController.navigate(R.id.newsFragment)
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId)     {
                    R.id.home_menu ->  {
                      navController.navigate(R.id.newsFragment)

                    }

                    R.id.qr ->  {

                    }

                    R.id.profile -> {
                        val intent  = Intent(this@HomeActivity, ProfileActivity::class.java)
                        intent.putExtra("uid", uid)
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