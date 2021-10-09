package com.example.myapp.modelData

data class UserInfo(
    val name: String = "" ,
    val email: String = "" ,
    val uri: String = ""
)

data class UpdatedUser (
    val name: String = "" ,
    val email: String = "" ,
    val uri: String = "",
    val phone: String = ""
        )
