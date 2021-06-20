package com.example.spacexfan.view

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.spacexfan.R
import com.example.spacexfan.view.auth.Login
import com.example.spacexfan.view.tabs.FavouriteRocketsFragment
import com.example.spacexfan.view.tabs.RocketListFragment
import com.example.spacexfan.view.tabs.UpcomingLaunchesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var currentTab : Int = R.id.rockets_page
    private var cancellationSignal : CancellationSignal? = null
    private val authCallback : BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    bottom_navigation.selectedItemId = R.id.favourite_page
                    replaceFragment(FavouriteRocketsFragment())

                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyAuthStatus("Authentication Error : $errString")
                    bottom_navigation.selectedItemId = currentTab
                }
            }



    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        checkBiometricSupport()

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.rockets_page -> {
                    replaceFragment(RocketListFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.upcoming_page -> {
                    replaceFragment(UpcomingLaunchesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favourite_page -> {
                    currentTab = bottomNavigation.selectedItemId
                    val biometricPrompt: BiometricPrompt = BiometricPrompt.Builder(this)
                        .setTitle("Favourite Page Authentication")
                        .setSubtitle("Authentication is required")
                        .setDescription("To enter Favourite Tab you need to successfully complete authentication.")
                        .setNegativeButton("Cancel", this.mainExecutor, { dialog, which ->
                            notifyAuthStatus("Authentication is cancelled")
                            bottom_navigation.selectedItemId = currentTab
                        })
                        .build()

                    biometricPrompt.authenticate(getCancelationSignal(), mainExecutor, authCallback)

                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }

        replaceFragment(RocketListFragment())


    }

    private fun getCancelationSignal() : CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyAuthStatus("Authentication is cancelled.")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport() : Boolean{
        val keyguardManager : KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure){
            notifyAuthStatus("Fingerprint option is not available or disabled.")
            return false
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            notifyAuthStatus("Fingerprint Authentication permission not given.")
            return false
        }

        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            return true
        } else true
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun notifyAuthStatus(message: String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.status_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_logout) {
            mAuth.signOut()
            val logoutIntent = Intent(this@MainActivity, Login::class.java)
            startActivity(logoutIntent)
            ActivityCompat.finishAffinity(this@MainActivity)
        }
        return super.onOptionsItemSelected(item)
    }


}