package com.example.myapp.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private  var profile: UserInfo? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var list: MutableList<DataInfo>
    private lateinit var live: LiveData<DataInfo>
    private lateinit var name: String
    private  var phone: String = ""
    private lateinit var email: String
    private lateinit var uri: String
    private lateinit var updatedUri: Uri
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var user: FirebaseUser
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = mutableListOf()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
         user = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
         id = intent.getStringExtra("uid").toString()
        ref = database.reference.child("accounts").child(id)


        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()



        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    name = snapshot.child("name").value.toString()
                     email = snapshot.child("email").value.toString()
                    phone = snapshot.child("phone").value.toString()
                    uri = snapshot.child("uri").value.toString()

                if(name.isBlank() && email.isEmpty() && phone.isEmpty() && uri.isEmpty()) {
                    binding.name.setText("")
                    binding.email.setText("")
                    binding.phone.setText("")

                }
                else {
                    binding.pb.visibility = View.INVISIBLE
                    binding.name.setText(name)
                    binding.email.setText(email)
                    binding.phone.setText(phone)
                    Picasso.get().load(uri).into(binding.profileImage)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent?  = result.data
                if(data!=null) {
                    binding.pb.visibility = View.VISIBLE
                    updatedUri = data.data!!
                    binding.profileImage.setImageURI(updatedUri)
                    updateToFirebase(updatedUri)
                }

            }

        }

        fun galleryCall() {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(Intent.createChooser(intent, "Choose Image"))
        }

        binding.updateImage.setOnClickListener {
            galleryCall()
        }



        binding.logout.setOnClickListener {
            googleSignInClient.signOut()
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }



        binding.editProfile.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("phone", phone)
            intent.putExtra("email", email)
            intent.putExtra("uri", uri)
            startActivity(intent)
        }





    }

    private fun updateToFirebase(uri: Uri)  {
        storageReference = storage.reference.child("images").child(user.uid)
        storageReference.putFile(uri).addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
            override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                if(p0.isSuccessful) {
                    storageReference.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(p0: Uri?) {
                            val imageUrl = p0.toString()
                            ref = database.reference.child("accounts").child(id).child("uri")
                            ref.setValue(imageUrl).addOnCompleteListener(object : OnCompleteListener<Void> {
                                override fun onComplete(p0: Task<Void>) {
                                    if(p0.isSuccessful) {
                                        binding.pb.visibility = View.INVISIBLE
                                        Toast.makeText(this@ProfileActivity, "Image Updated", Toast.LENGTH_SHORT).show()

                                    }
                                }

                            })
                        }

                    })
                }
            }

        })
    }


}