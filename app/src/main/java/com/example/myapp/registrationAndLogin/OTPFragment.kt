package com.example.myapp.registrationAndLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentOTPBinding
import com.example.myapp.room.DataInfo
import com.example.myapp.viewModel.AppViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OTPFragment : Fragment() {

     private lateinit var binding: FragmentOTPBinding
     private lateinit var otp: String
     private lateinit var userid: String
     private lateinit var userToken: PhoneAuthProvider.ForceResendingToken
     private lateinit var auth: FirebaseAuth
     private val model: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOTPBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()


        binding.verify.setOnClickListener {
            otp = binding.getOtp.text.toString()
            if(otp.length<6) {
                Toast.makeText(requireContext(), "Enter 6 digit OTP", Toast.LENGTH_SHORT).show()
            }
            else  {
                binding.pb.visibility=View.VISIBLE
                val credential = PhoneAuthProvider.getCredential(userid, otp)
                signInWithPhoneAuthCredential(credential)
            }
        }


        val user = auth.currentUser

//        val data  = DataInfo(id = 17, name = user!!.displayName!!, email = user.email!!
//            , userToken = user.uid, uri = user.photoUrl!!.toString(), phone = model.getPhone())
//
//        model.insertInRoom(data)



        fun getCallbacks() {

            val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Toast.makeText(requireContext(), "Verification Successful", Toast.LENGTH_SHORT).show()
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    if(e is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "Invalid Request", Toast.LENGTH_SHORT).show()

                    }
                    else if(e is FirebaseTooManyRequestsException) {
                        Toast.makeText(requireContext(), "SMS Quota Exceeded", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    userid = p0
                    userToken = p1
                }

            }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(model.getPhone()).setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)



        }

        getCallbacks()




    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if(p0.isSuccessful) {
                        binding.pb.visibility=View.INVISIBLE
                        Toast.makeText(requireContext(), "Verification Successful", Toast.LENGTH_SHORT).show()
                        val action = OTPFragmentDirections.actionOTPFragmentToHomeActivity(model.getName(), model.getUid())
                        findNavController().navigate(action)
                    }
                }

            })
    }
}