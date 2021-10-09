package com.example.myapp.registrationAndLogin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentGoogleLogInBinding
import com.example.myapp.main.HomeActivity
import com.example.myapp.modelData.UserInfo
import com.example.myapp.room.DataInfo
import com.example.myapp.room.DatabaseApplication
import com.example.myapp.viewModel.AppViewModel
import com.example.myapp.viewModel.AppViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class GoogleLogInFragment : Fragment() {

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private val model: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (requireActivity().application as DatabaseApplication).database.itemDao()
        )
    }
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var dataRef: DatabaseReference
    private lateinit var idList: MutableList<String>
    private lateinit var user: FirebaseUser
    companion object {
         @SuppressLint("StaticFieldLeak")
         lateinit var binding: FragmentGoogleLogInBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoogleLogInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        if(auth.currentUser != null) {
//            val action = GoogleLogInFragmentDirections.actionGoogleLogInFragmentToHomeActivity(auth.currentUser!!.uid)
//            findNavController().navigate(action)
//             requireActivity().finish()
            binding.l1.visibility = View.INVISIBLE
            binding.splash.visibility=View.VISIBLE
            signIn()
        }

        idList = mutableListOf()






        binding.cardView.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, 4)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==4) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {

        try {
            val account = task.getResult(ApiException::class.java)
            binding.pb.visibility = View.VISIBLE
            firebaseAuthWithGoogle(account)
        }
        catch (e: ApiException) {
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        model.setAccount(account)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
         auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task->

            if(task.isSuccessful) {

                 user = auth.currentUser!!
                database = FirebaseDatabase.getInstance()
                ref = database.reference.child("accounts").child(user.uid)

                if(task.result.additionalUserInfo?.isNewUser == true) {



                    val item = UserInfo(user.displayName!!, user.email  !!, user.photoUrl!!.toString())
                    ref.setValue(item).addOnCompleteListener(object: OnCompleteListener<Void> {

                        override fun onComplete(p0: Task<Void>) {
                            model.setName(user.displayName!!)
                            model.setUid(user.uid)
                            binding.pb.visibility = View.INVISIBLE
                            Toast.makeText(requireContext(), "Sign in Successful", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_googleLogInFragment_to_registrationFragment)


                        }

                    })

                }
                else {
                    binding.pb.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), user.displayName, Toast.LENGTH_SHORT).show()
                    val action = GoogleLogInFragmentDirections.actionGoogleLogInFragmentToHomeActivity(user.uid)
                    findNavController().navigate(action)
                    requireActivity().finish()
                }


            }

        }




    }





}