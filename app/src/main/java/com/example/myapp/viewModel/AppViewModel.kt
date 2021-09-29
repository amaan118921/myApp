package com.example.myapp.viewModel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.myapp.modelData.Daily
import com.example.myapp.modelData.News
import com.example.myapp.modelData.UserInfo
import com.example.myapp.network.ApiNews
import com.example.myapp.network.WApi
import com.example.myapp.registrationAndLogin.GoogleLogInFragment
import com.example.myapp.room.Dao
import com.example.myapp.room.DataInfo
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class AppViewModel(private val itemDao: Dao): ViewModel() {

    private lateinit var phone: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var account: GoogleSignInAccount
    private lateinit var name: String
    private lateinit var uid: String


    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }


    fun setUid(uid: String) {
        this.uid = uid
    }

    fun getUid(): String {
        return uid
    }


    fun insertInRoom(data: DataInfo) {
        viewModelScope.launch {
            itemDao.insert(data)
        }
    }








    fun setPhone(phone: String) {
        this.phone = phone
    }


    fun getPhone(): String {
        return phone
    }






    fun setAccount(account: GoogleSignInAccount) {
        this.account = account
    }

    fun insertInFirebase() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        ref = database.reference.child("accounts").child(auth.uid!!)
        val user = UserInfo(account.displayName!!, account.email!!, account.idToken!!, account.photoUrl!!.toString())
        ref.push().setValue(user).addOnCompleteListener(object: OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {
                GoogleLogInFragment.binding.pb.visibility = View.INVISIBLE
            }

        })

    }

}

class AppViewModelFactory(private val itemDao: Dao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(itemDao) as T
        }

        throw IllegalArgumentException("Unknown")
    }

}