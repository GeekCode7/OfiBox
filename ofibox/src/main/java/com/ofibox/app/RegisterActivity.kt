package com.ofibox.app

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ofibox.app.databinding.ActivityRegisterBinding
import androidx.core.widget.doAfterTextChanged


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                Toast.makeText(this, getString(R.string.msg_register_ok), Toast.LENGTH_SHORT).show()
                // Aquí en el futuro: guardar datos / llamar API / navegar a Login
            }
        }

        // Limpia errores al escribir
        listOf(
            binding.tilFullname to binding.edtFullname,
            binding.tilEmail to binding.edtEmail,
            binding.tilPhone to binding.edtPhone,
            binding.tilPassword to binding.edtPassword,
            binding.tilConfirm to binding.edtConfirm
        ).forEach { (til, edt) ->
            edt.doAfterTextChanged { til.error = null }
        }

    }

    private fun validateForm(): Boolean {
        var ok = true

        val fullname = binding.edtFullname.text?.toString()?.trim().orEmpty()
        val email = binding.edtEmail.text?.toString()?.trim().orEmpty()
        val phone = binding.edtPhone.text?.toString()?.trim().orEmpty()
        val pass = binding.edtPassword.text?.toString().orEmpty()
        val confirm = binding.edtConfirm.text?.toString().orEmpty()
        val terms = binding.chkTerms.isChecked

        if (fullname.isEmpty()) {
            binding.tilFullname.error = getString(R.string.err_required); ok = false
        }
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.err_required); ok = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.err_email); ok = false
        }

        // Teléfono simple: 10 dígitos
        val phoneRegex = Regex("^[0-9]{10}$")
        if (phone.isEmpty()) {
            binding.tilPhone.error = getString(R.string.err_required); ok = false
        } else if (!phoneRegex.matches(phone)) {
            binding.tilPhone.error = getString(R.string.err_phone); ok = false
        }

        if (pass.length < 6) {
            binding.tilPassword.error = getString(R.string.err_password_len); ok = false
        }
        if (confirm != pass) {
            binding.tilConfirm.error = getString(R.string.err_password_match); ok = false
        }

        if (!terms) {
            Toast.makeText(this, getString(R.string.err_terms), Toast.LENGTH_SHORT).show()
            ok = false
        }
        return ok
    }
}
