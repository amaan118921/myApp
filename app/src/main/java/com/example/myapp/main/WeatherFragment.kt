package com.example.myapp.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.example.myapp.databinding.FragmentWeatherBinding
import com.example.myapp.modelData.Daily
import com.example.myapp.viewModel.AppViewModel
import com.example.myapp.viewModel.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.properties.Delegates


class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var location: Location
    private val model: HomeViewModel by activityViewModels()
    private lateinit var list: List<Daily>
    private val kelvin = 273.0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        list = listOf()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationProviderClient.lastLocation.addOnCompleteListener(object : OnCompleteListener<Location> {
                override fun onComplete(p0: Task<Location>) {
                  location = p0.result
                    latitude = location.latitude
                    longitude = location.longitude
                    model.setLat(latitude.toString())
                    model.setLon(longitude.toString())

                    Toast.makeText(requireContext(), "Latitude$latitude" + "Longitude$longitude", Toast.LENGTH_SHORT).show()

                }

            })
        }
        else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                0 )
        }


            model.getWeatherResponse(latitude.toString(), longitude.toString())
            model.weatherData.observe(viewLifecycleOwner) {

                val t1 = convertTemp(it[0].dailyTemp.dayTemp)
                binding.t1.setText(t1)

                val t2 = convertTemp(it[1].dailyTemp.dayTemp)
                binding.T1.setText(t2)

                val t3 = convertTemp(it[2].dailyTemp.dayTemp)
                binding.Tt1.setText(t3)
            }











    }



  private fun convertTemp(temp: String): String {
      val res = temp.toDouble() - kelvin
      val df = DecimalFormat("#.##")
      df.roundingMode = RoundingMode.CEILING
      return df.format(res).toDouble().toString()
  }


















//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode) {
//
//            0 ->  {
//                if((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                }
//
//
//            }
//        }
//    }
}