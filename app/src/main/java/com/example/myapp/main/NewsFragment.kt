package com.example.myapp.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.adapter.NewsAdapter
import com.example.myapp.databinding.FragmentNewsBinding
import com.example.myapp.modelData.News
import com.example.myapp.viewModel.AppViewModel
import com.example.myapp.viewModel.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

class NewsFragment : Fragment() {

    private lateinit var adapter: NewsAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var list: MutableList<String>

    @SuppressLint("StaticFieldLeak")
    companion object  {
        @SuppressLint("StaticFieldLeak")
         lateinit var binding: FragmentNewsBinding

    }

    private val model: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        list = mutableListOf("animals", "sports", "nature", "backgrounds", "fashion", "nature", "science", "education", "feelings", "health", "people",
            "religion", "places", "animals", "industry", "computer", "food", "sports", "transportation", "travel", "investments")

//        val user = auth.currentUser
//        if(user==null) {
//            findNavController().navigate(R.id.action_newsFragment2_to_googleLogInFragment)
//        }
        adapter = NewsAdapter(model,requireParentFragment())
        binding.newsRecyclerView.adapter = adapter

         model.getNewsResponse("industry")

        model.newsData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        adapter.notifyDataSetChanged()




    }
}