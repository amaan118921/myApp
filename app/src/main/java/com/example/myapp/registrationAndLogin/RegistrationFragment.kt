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
import com.example.myapp.databinding.FragmentRegistrationBinding
import com.example.myapp.viewModel.AppViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rilixtech.widget.countrycodepicker.CountryCodePicker

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val model: AppViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var countryCodePicker: CountryCodePicker
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        countryCodePicker = binding.ccp
        countryCodePicker.registerPhoneNumberTextView(binding.getPhone)
        binding.sendOtp.setOnClickListener {
            if(binding.getPhone.text.toString().length < 10) {
                Toast.makeText(requireContext(), "Enter 10 Digit Number", Toast.LENGTH_SHORT).show()
            }
            else  {
                binding.pb.visibility = View.VISIBLE
                model.setPhone(countryCodePicker.fullNumberWithPlus)
                ref=database.reference.child("accounts").child(auth.currentUser!!.uid).child("phone")
                ref.setValue(countryCodePicker.fullNumberWithPlus).addOnCompleteListener(object: OnCompleteListener<Void> {
                    override fun onComplete(p0: Task<Void>) {
                        binding.pb.visibility = View.INVISIBLE
                        findNavController().navigate(R.id.action_registrationFragment_to_OTPFragment)
                    }

                })



            }
        }

    }
}