package com.ofibox.app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.widget.doAfterTextChanged
import com.ofibox.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Limpiar errores al escribir
        binding.edtEmail.doAfterTextChanged { binding.tilEmail.error = null }
        binding.edtPassword.doAfterTextChanged { binding.tilPassword.error = null }

        binding.btnLogin.setOnClickListener {
            hideKeyboard()
            if (validate()) {
                Toast.makeText(this, getString(R.string.msg_login_ok), Toast.LENGTH_SHORT).show()
                // Navegar a Perfil (simulado)
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
        }

        // ¿Olvidaste tu contraseña?
        binding.tvForgot.setOnClickListener {
            Toast.makeText(this, "Función no implementada (UI).", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validate(): Boolean {
        var ok = true
        val email = binding.edtEmail.text?.toString()?.trim().orEmpty()
        val pass = binding.edtPassword.text?.toString().orEmpty()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.err_login_email)
            ok = false
        }
        if (pass.isEmpty()) {
            binding.tilPassword.error = getString(R.string.err_login_password)
            ok = false
        }
        return ok
    }

    private fun hideKeyboard() {
        val imm = getSystemService<InputMethodManager>()
        imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
