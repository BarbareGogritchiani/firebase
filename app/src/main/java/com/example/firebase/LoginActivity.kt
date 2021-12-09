package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextTextEmailAddress : EditText
    private lateinit var editTextTextPassword : EditText
    private lateinit var editTextTextPassword2 : EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        registerListeners()
    }
    private fun init(){
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress)
        editTextTextPassword = findViewById(R.id.editTextTextPassword)
        editTextTextPassword2 = findViewById(R.id.editTextTextPassword2)
        submitButton = findViewById(R.id.submitButton)

    }
    private fun registerListeners(){
        submitButton.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString().trim()
            val password = editTextTextPassword.text.toString().trim()
            val confirmPassword = editTextTextPassword2.text.toString().trim()


            if(!(email.contains("@")) || !(email.length > 5 ) || !(email.contains("."))){
                editTextTextEmailAddress.error = "enter a valid  E-mail"
                return@setOnClickListener
            }else if (!(password.length > 9)) {
                editTextTextPassword.error = "length must be at least 9"
                return@setOnClickListener
            }else if (password != confirmPassword) {
                editTextTextPassword2.error = "password must match "
                return@setOnClickListener
            }else if(!password.contains(Regex("[0-9]")) && !(password.matches(".*[a-z].*".toRegex())) && !(password.matches(".*[A-Z].*".toRegex()))){
                editTextTextPassword.error = "password must contain symbols and digits "
                return@setOnClickListener
            }
            if(password.length >=9 && email.contains("@") && email.length > 5 && password == confirmPassword ){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Gau , You've successfully registered ", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,"Try again , there was an error" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}