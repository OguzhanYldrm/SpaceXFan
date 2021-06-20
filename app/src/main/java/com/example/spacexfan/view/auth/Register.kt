package com.example.spacexfan.view.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.spacexfan.R
import com.example.spacexfan.utils.Alerts
import com.example.spacexfan.utils.loadGif
import com.example.spacexfan.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.emailEditText
import kotlinx.android.synthetic.main.activity_register.emailLayout
import kotlinx.android.synthetic.main.activity_register.passwordEditText
import kotlinx.android.synthetic.main.activity_register.passwordLayout
import kotlinx.android.synthetic.main.activity_register.progressLayout
import java.util.regex.Matcher
import java.util.regex.Pattern

class Register : AppCompatActivity(), View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private var name: String = ""
    private var surname: String = ""
    private var email: String = ""
    private var password: String = ""
    private var registerClicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loading_register.loadGif()

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        //Inputs
        nameEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                name = nameEditText!!.text.toString()
                if (registerClicked) {
                    checkName(name)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                name = nameEditText!!.text.toString()
                if (registerClicked) {
                    checkName(name)
                }
            }

            override fun afterTextChanged(s: Editable) {
                name = nameEditText!!.text.toString()
                if (registerClicked) {
                    checkName(name)
                }
            }
        })
        surnameEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                surname = surnameEditText?.text.toString()
                if (registerClicked) {
                    checkSurname(surname)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                surname = surnameEditText?.text.toString()
                if (registerClicked) {
                    checkSurname(surname)
                }
            }

            override fun afterTextChanged(s: Editable) {
                surname = surnameEditText?.text.toString()
                if (registerClicked) {
                    checkSurname(surname)
                }
            }
        })
        emailEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                email = emailEditText!!.text.toString()
                if (registerClicked) {
                    checkEmail(email)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                email = emailEditText!!.text.toString()
                if (registerClicked) {
                    checkEmail(email)
                }
            }

            override fun afterTextChanged(s: Editable) {
                email = emailEditText!!.text.toString().trim { it <= ' ' }
                if (registerClicked) {
                    checkEmail(email)
                }
            }
        })
        passwordEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                password = passwordEditText?.text.toString()
                if (registerClicked) {
                    checkPassword(password)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                password = passwordEditText!!.text.toString()
                if (registerClicked) {
                    checkPassword(password)
                }
            }

            override fun afterTextChanged(s: Editable) {
                password = passwordEditText!!.text.toString()
                if (registerClicked) {
                    checkPassword(password)
                }
            }
        })


        //Onclick Methods
        register_button.setOnClickListener(this)
        backButton.setOnClickListener(this)
        userAgreement.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.register_button -> {
                registerClicked = true
                email = emailEditText!!.text.toString().trim { it <= ' ' }
                password = passwordEditText!!.text.toString()
                name = nameEditText!!.text.toString().trim { it <= ' ' }
                surname = surnameEditText!!.text.toString().trim { it <= ' ' }
                val emailValid = checkEmail(email)
                val passwordValid = checkPassword(password)
                val nameValid = checkName(name)
                val surnameValid = checkSurname(surname)
                if (emailValid && passwordValid && nameValid && surnameValid) {
                    emailLayout!!.isErrorEnabled = false
                    nameLayout!!.isErrorEnabled = false
                    passwordLayout!!.isErrorEnabled = false
                    surnameLayout!!.isErrorEnabled = false
                    if (userAgreementCheckbox!!.isChecked) {
                        signUpUser(email, password)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Kullanıcı Sözleşmesini onaylamanız gerekmektedir !",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            R.id.backButton -> {
                val loginIntent = Intent(applicationContext, Login::class.java)
                startActivity(loginIntent)
                ActivityCompat.finishAffinity(this@Register)
            }
            R.id.userAgreement -> {
                val agreementIntent = Intent(Intent.ACTION_VIEW)
                agreementIntent.data = Uri.parse("https://www.termsandconditionsgenerator.com/")
                startActivity(agreementIntent)
            }
            else -> {
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern: Pattern
        val emailPattern = getString(R.string.email_validation)
        pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val pattern: Pattern
        val passwordPattern = getString(R.string.valid_password)
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun checkPassword(givenPassword: String): Boolean {
        if (givenPassword.isEmpty()) {
            passwordLayout!!.isErrorEnabled = true
            passwordLayout!!.error = "Şifre alanı boş bırakılamaz !"
        } else if (givenPassword.length < 8) {
            passwordLayout!!.isErrorEnabled = true
            passwordLayout!!.error = "Şifre minimum 8 karakter olmalıdır !"
        } else if (givenPassword.length > 128) {
            passwordLayout!!.isErrorEnabled = true
            passwordLayout!!.error = "Şifre maximum 128 karakter olabilir !"
        } else if (!isPasswordValid(givenPassword)) {
            passwordLayout!!.isErrorEnabled = true
            passwordLayout!!.error =
                "Şifre en az 1 rakam, 1 küçük harf, 1 büyük harf ve 1 karakter (.!?@*_+,$#&=') içermelidir !"
        } else {
            passwordLayout!!.isErrorEnabled = false
            return true
        }
        return false
    }

    private fun checkEmail(givenEmail: String): Boolean {
        if (givenEmail.isEmpty()) {
            emailLayout!!.isErrorEnabled = true
            emailLayout!!.error = "E-posta alanı boş bırakılamaz !"
        } else if (!isEmailValid(givenEmail)) {
            emailLayout!!.isErrorEnabled = true
            emailLayout!!.error = "Lütfen geçerli bir e-posta giriniz !"
        } else if (givenEmail.length > 254) {
            emailLayout!!.isErrorEnabled = true
            emailLayout!!.error = "E-posta maximum 254 karakter olabilir !"
        } else {
            emailLayout!!.isErrorEnabled = false
            return true
        }
        return false
    }

    private fun checkName(givenName: String): Boolean {
        when {
            givenName.isEmpty() -> {
                nameLayout!!.isErrorEnabled = true
                nameLayout!!.error = "Ad alanı boş bırakılamaz !"
            }
            givenName.length > 100 -> {
                nameLayout!!.isErrorEnabled = true
                nameLayout!!.error = "Ad maximum 100 karakter olabilir !"
            }
            else -> {
                nameLayout!!.isErrorEnabled = false
                return true
            }
        }
        return false
    }

    private fun checkSurname(givenSurname: String): Boolean {
        when {
            givenSurname.isEmpty() -> {
                surnameLayout!!.isErrorEnabled = true
                surnameLayout!!.error = "Soyad alanı boş bırakılamaz !"
            }
            givenSurname.length > 100 -> {
                surnameLayout!!.isErrorEnabled = true
                surnameLayout!!.error = "Soyad maximum 100 karakter olabilir !"
            }
            else -> {
                surnameLayout!!.isErrorEnabled = false
                return true
            }
        }
        return false
    }

    private fun signUpUser(userEmail: String, userPassword: String) {
        isLoading(true)
        mAuth!!.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnSuccessListener {
                val dashboardIntent = Intent(applicationContext, MainActivity::class.java)
                startActivity(dashboardIntent)
                ActivityCompat.finishAffinity(this@Register)
            }
            .addOnFailureListener { e ->
                isLoading(false)
                Alerts.authError(this@Register, e.localizedMessage)
            }
    }

    private fun isLoading(visibility: Boolean) {

        if (visibility) {
            progressLayout.visibility = View.VISIBLE
            loading_register.visibility = View.VISIBLE
        } else {
            progressLayout.visibility = View.INVISIBLE
            loading_register.visibility = View.INVISIBLE
        }
    }
}