package com.imamfrf.dicoding.submission5made

import android.content.Context
import android.content.SharedPreferences


class SharedPrefManager(val context: Context) {

    //the constants
    private val SHARED_PREF_NAME = "submission5madesharedpref"
    private val KEY_DAILY = "daily_reminder"
    private val KEY_RELEASE = "release_reminder"
    private val KEY_INIT = "init"

    private var mInstance: SharedPrefManager? = null
    private val sharedPreferences: SharedPreferences?
            = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)



    @Synchronized
    fun getInstance(context: Context): SharedPrefManager {
        if (mInstance == null) {
            mInstance = SharedPrefManager(context)
        }
        return mInstance as SharedPrefManager
    }

    fun setDailyReminder(daily: Boolean){
        val editor = sharedPreferences?.edit()

        editor?.putBoolean(KEY_DAILY, daily)
        editor?.putInt(KEY_INIT, 1)
        editor?.apply()
    }
    fun setReleaseReminder(release: Boolean){
        val editor = sharedPreferences?.edit()

        editor?.putBoolean(KEY_RELEASE, release)
        editor?.putInt(KEY_INIT, 1)
        editor?.apply()
    }


    fun checkDailyReminder() : Boolean?{
        return sharedPreferences?.getBoolean(KEY_DAILY, true)
    }

    fun checkReleaseReminder() : Boolean?{
        return sharedPreferences?.getBoolean(KEY_RELEASE, true)
    }

    fun checkInit(): Int? {
        return sharedPreferences?.getInt(KEY_INIT,0)
    }
//
//    //method to let the user login
//    //this method will store the user data in shared preferences
//    fun userLogin(user: User, token: String) {
//        val editor = sharedPreferences?.edit()
//        val date = Date()
//
//        //Set user session for next 7 days
//        val millis = date.time.plus(7 * 24 * 60 * 60 * 1000)
//        editor?.putLong(KEY_EXPIRES, millis)
//        editor?.putString(KEY_ID, user.id)
//        editor?.putString(KEY_NAME, user.name)
//        editor?.putString(KEY_EMAIL, user.email)
//        editor?.putString(KEY_PHONE, user.phone)
//        editor?.putString(KEY_ADDRESS, user.address)
//        editor?.putString(KEY_ROLE, user.role)
//        editor?.putString(KEY_PHOTO, user.photo)
//        editor?.putString(KEY_TENANT_ID, user.tenantId)
//        editor?.putString(KEY_TOKEN, token)
//        editor?.apply()
//    }
//
//    //this method will checker whether user is already logged in or not
//    fun isLoggedIn(): Boolean {
//        val currentDate = Date()
//
//        val millis = sharedPreferences?.getLong(KEY_EXPIRES, 0)
//
//        /* If shared preferences does not have a value
//         then user is not logged in
//         */
//        if (millis == 0L) {
//            return false
//        }
//        val expiryDate = Date(millis!!)
//
//        /* Check if session is expired by comparing
//        current date and Session expiry date
//        */
//        return currentDate.before(expiryDate)
//    }
//
//
//
//    //this method will give the logged in user
//    fun getUser(): User {
//        return User(
//            sharedPreferences?.getString(KEY_ID, ""), sharedPreferences?.getString(KEY_NAME, ""),
//            sharedPreferences?.getString(KEY_EMAIL, ""), sharedPreferences?.getString(KEY_PHONE, ""),
//            sharedPreferences?.getString(KEY_ADDRESS, ""), sharedPreferences?.getString(KEY_ROLE, ""),
//            sharedPreferences?.getString(KEY_PHOTO, ""),
//            sharedPreferences?.getString(KEY_TENANT_ID, ""),
//            Date(sharedPreferences!!.getLong(KEY_EXPIRES, 0)))
//    }
//
//    fun getToken(): String?{
//        return sharedPreferences?.getString(KEY_TOKEN, "")
//    }
//
//    fun updateToken(newToken: String){
//        val editor = sharedPreferences?.edit()
//        editor?.putString(KEY_TOKEN, newToken)
//        editor?.apply()
//    }
//
//    fun updateTenant(id: String){
//        val editor = sharedPreferences?.edit()
//        editor?.putString(KEY_TENANT_ID, id)
//        editor?.apply()
//    }
//
//    fun updateRole(newRole: String){
//        val editor = sharedPreferences?.edit()
//        editor?.putString(KEY_ROLE, newRole)
//        editor?.apply()
//    }
//
//    //this method will logout the user
//    fun logout() {
//        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()
//        context.startActivity(Intent(context, LoginActivity::class.java))
//
//    }
}