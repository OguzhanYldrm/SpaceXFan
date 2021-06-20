package com.example.spacexfan.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.spacexfan.R
import com.example.spacexfan.utils.Alerts
import com.example.spacexfan.utils.loadGif
import com.example.spacexfan.view.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_favourite_rockets.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Login : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val RC_SIGN_IN = 100
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private var passwordLength = 0
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loading_login.loadGif()

        // Configure Google Sign In
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //Auth Instance
        mAuth = FirebaseAuth.getInstance()
        oAuth()

        //Edit Text
        emailEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                email = emailEditText?.text.toString()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                email = emailEditText?.text.toString().trim { it <= ' ' }
                if (emailValid(email)) {
                    emailLayout?.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {
                email = emailEditText?.text.toString().trim { it <= ' ' }
                if (emailValid(email)) {
                    emailLayout?.isErrorEnabled = false
                }
            }
        })
        passwordEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                passwordLength = passwordEditText!!.length()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                passwordLength = passwordEditText!!.length()
                if (passwordLength in 8..127) {
                    passwordLayout?.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {
                passwordLength = passwordEditText!!.length()
                if (passwordLength >= 8) {
                    passwordLayout?.isErrorEnabled = false
                }
            }
        })
        loginButton.setOnClickListener(this)
        registerButton.setOnClickListener(this)
        googleSignIn.setOnClickListener(this)
    }

    private fun oAuth() {
        val firebaseUser = mAuth.currentUser
        if (firebaseUser != null){
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            ActivityCompat.finishAffinity(this@Login)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.loginButton -> defaultLogin()

            R.id.registerButton -> {
                val registerIntent = Intent(applicationContext, Register::class.java)
                startActivity(registerIntent)
            }
            R.id.googleSignIn -> signIn()
        }
    }

    private fun emailValid(email: String): Boolean {
        val pattern: Pattern
        val emailPattern = getString(R.string.email_validation)
        pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    /**
     * This is the default login operation of app
     */
    private fun defaultLogin() {
        var isValid = true
        when {
            passwordLength == 0 -> {
                passwordLayout!!.isErrorEnabled = true
                passwordLayout!!.error = "Şifre alanı boş bırakılamaz!"
                isValid = false
            }
            passwordLength < 8 -> {
                passwordLayout!!.isErrorEnabled = true
                passwordLayout!!.error = "Şifre minimum 8 karakter olmalı!!"
                isValid = false
            }
            passwordLength > 128 -> {
                passwordLayout!!.error = "Şifre maximum 128 karakter olmalı!!"
                isValid = false
            }
        }
        if (!emailValid(emailEditText!!.text.toString().trim { it <= ' ' })) {
            emailLayout!!.isErrorEnabled = true
            emailLayout!!.error = "Lütfen geçerli bir e-posta giriniz !"
            isValid = false
        }
        if (isValid) {
            emailLayout!!.isErrorEnabled = false
            passwordLayout!!.isErrorEnabled = false
            val email = emailEditText!!.text.toString().trim { it <= ' ' }
            val password = passwordEditText!!.text.toString()
            isLoading(true)
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    ActivityCompat.finishAffinity(this@Login)
                }.addOnFailureListener { e ->
                    isLoading(false)
                    Alerts.authError(this@Login, e.localizedMessage)
                }
        }
    }

    /**
     * Google login operation
     */
    private fun signIn() {
        googleSignInClient.signOut()
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * Opens dialog to get the desired Google Account from user.
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        isLoading(true)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    account.idToken?.let { firebaseAuthWithGoogle(it) }
                }
            } catch (e: ApiException) {
                isLoading(false)
                Alerts.authError(this@Login, task.exception?.localizedMessage)
            }
        }
    }

    /**
     * Do the actual login operation with google.
     * @param idToken = user token given by google
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    ActivityCompat.finishAffinity(this@Login)
                } else {
                    isLoading(false)
                    Alerts.authError(
                        this@Login,
                        task.exception?.localizedMessage
                    )
                }
            }
    }

    private fun isLoading(visibility: Boolean) {
        if (visibility) {
            progressLayout.visibility = View.VISIBLE
            loading_login.visibility = View.VISIBLE
        } else {
            progressLayout.visibility = View.INVISIBLE
            loading_login.visibility = View.INVISIBLE
        }
    }


}