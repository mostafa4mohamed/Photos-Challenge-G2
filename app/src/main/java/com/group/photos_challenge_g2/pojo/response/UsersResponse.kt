package com.group.photos_challenge_g2.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class UsersResponse : ArrayList<User>()

@Entity
data class User(
    @ColumnInfo val address: Address,
    @ColumnInfo val company: Company,
    @ColumnInfo val email: String,
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val username: String,
    @ColumnInfo val website: String
)

data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)

data class Company(
    val bs: String,
    val catchPhrase: String,
    val name: String
)

data class Geo(
    val lat: String,
    val lng: String
)