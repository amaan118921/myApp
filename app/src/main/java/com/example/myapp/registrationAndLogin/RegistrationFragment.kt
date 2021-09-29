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
import com.rilixtech.widget.countrycodepicker.CountryCodePicker

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val model: AppViewModel by activityViewModels()
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

        countryCodePicker = binding.ccp
        countryCodePicker.registerPhoneNumberTextView(binding.getPhone)
        binding.sendOtp.setOnClickListener {
            if(binding.getPhone.text.toString().length < 10) {
                Toast.makeText(requireContext(), "Enter 10 Digit Number", Toast.LENGTH_SHORT).show()
            }
            else  {
                model.setPhone(countryCodePicker.fullNumberWithPlus)
                findNavController().navigate(R.id.action_registrationFragment_to_OTPFragment)
            }
        }

    }
}