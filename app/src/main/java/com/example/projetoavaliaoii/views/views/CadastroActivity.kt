package com.example.projetoavaliaoii.views.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetoavaliaoii.R
import com.example.projetoavaliaoii.views.UserRepository
import com.example.projetoavaliaoii.views.model.User
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnCadastrarCad.setOnClickListener {
            val email = edtEmailCad.text.toString()
            val password = edtSenhaCad.text.toString()

            if (email.isNotBlank() && password.isNotBlank()){
                createUser(email, password)
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createUser(email: String, password: String): User {
        val user = User(email, password)
        UserRepository.createUser(this, user)
        return user
    }
}
