package com.ofibox.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ofibox.app.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Datos simulados (solo UI)
        binding.tvName.text = getString(R.string.profile_name_placeholder)
        binding.tvEmail.text = getString(R.string.profile_email_placeholder)
        binding.tvPhone.text = getString(R.string.profile_phone_placeholder)

        // Toolbar: icono de navegación actúa como "cerrar sesión"
        binding.toolbar.setNavigationOnClickListener { showLogoutDialog() }

        binding.btnEdit.setOnClickListener {
            Toast.makeText(this, getString(R.string.msg_edit_soon), Toast.LENGTH_SHORT).show()
        }
        binding.btnLogout.setOnClickListener { showLogoutDialog() }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dlg_logout_title))
            .setMessage(getString(R.string.dlg_logout_msg))
            .setPositiveButton(getString(R.string.dlg_yes)) { _, _ ->
                Toast.makeText(this, getString(R.string.msg_logged_out), Toast.LENGTH_SHORT).show()
                val i = Intent(this, WelcomeActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
                finish()
            }
            .setNegativeButton(getString(R.string.dlg_no), null)
            .show()
    }
}
