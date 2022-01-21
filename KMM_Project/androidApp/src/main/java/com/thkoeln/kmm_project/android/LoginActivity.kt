package com.thkoeln.kmm_project.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }
            true
        }
    }

    private fun goToMainActivity() {
        val errorMessage = findViewById<TextView>(R.id.login_error)
        val userName = findViewById<EditText>(R.id.login_editText)
        if (userName.text.toString() != "") {
            val intent = Intent(this, MainActivity::class.java)
            errorMessage.visibility = View.INVISIBLE
            intent.putExtra("userName", userName.text.toString())
            startActivity(intent)

        } else {
            errorMessage.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        val loginButton = findViewById<Button>(R.id.login_button)

        val userName = findViewById<EditText>(R.id.login_editText)

        userName.onSubmit {
            goToMainActivity()
        }

        loginButton.setOnClickListener {
            goToMainActivity()
        }

    }


    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }


}