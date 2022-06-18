package com.group.photos_challenge_g2.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.group.photos_challenge_g2.pojo.response.Address
import com.group.photos_challenge_g2.pojo.response.Company
import com.group.photos_challenge_g2.pojo.response.Geo

class ConverterHelper {

    @TypeConverter
    fun fromCompany(company: Company?): String? {
        if (company == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(company, Company::class.java)
    }

    @TypeConverter
    fun toCompany(josnString: String?): Company? {
        if (josnString == null) {
            return null
        }
        val gson = Gson()
        return gson.fromJson(josnString, Company::class.java)
    }

    @TypeConverter
    fun fromAddress(address: Address?): String? {
        if (address == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(address, address::class.java)
    }

    @TypeConverter
    fun toAddress(josnString: String?): Address? {
        if (josnString == null) {
            return null
        }
        val gson = Gson()
        return gson.fromJson(josnString, Address::class.java)
    }

    @TypeConverter
    fun fromGeo(geo: Geo?): String? {
        if (geo == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(geo, geo::class.java)
    }

    @TypeConverter
    fun toGeo(josnString: String?): Geo? {
        if (josnString == null) {
            return null
        }
        val gson = Gson()
        return gson.fromJson(josnString, Geo::class.java)
    }

}