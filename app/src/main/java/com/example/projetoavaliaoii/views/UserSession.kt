package com.example.projetoavaliaoii.views

import com.example.projetoavaliaoii.views.model.User

class UserSession {
    companion object {
        var currentUser: User? = null

        fun start(user: User){
            this.currentUser = user
        }

        fun reset(){
            this.currentUser = null
        }
    }
}
