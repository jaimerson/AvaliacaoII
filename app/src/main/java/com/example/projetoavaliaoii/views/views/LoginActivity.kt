package com.example.projetoavaliaoii.views.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetoavaliaoii.R
import com.example.projetoavaliaoii.views.UserRepository
import com.example.projetoavaliaoii.views.UserSession
import com.example.projetoavaliaoii.views.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(UserSession.currentUser != null){
            startActivity(Intent(this, ListActivity::class.java))
            return
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = UserRepository.getUser(this)

        if(user != null){
            edtEmailLogin.setText(user.email)
            edtSenhaLogin.setText(user.password)
        }

        btnCadastrarLogin.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        btnEntrarLogin.setOnClickListener {
            val newUser = User(edtEmailLogin.text.toString(), edtSenhaLogin.text.toString())
            if(newUser.equals(user)){
                UserSession.start(newUser)
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
