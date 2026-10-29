package com.exemplo.controle

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var editIp: EditText
    private lateinit var btnConectar: Button
    private var pcIp: String = ""
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState: Bundle?)
        setContentView(R.layout.activity_main)

        editIp = findViewById(R.id.editIp)
        btnConectar = findViewById(R.id.btnConectar)

        btnConectar.setOnClickListener {
            val ipDigitado = editIp.text.toString().trim()
            if (ipDigitado.isNotEmpty()) {
                pcIp = "http://$ipDigitado:5000"
                Toast.makeText(this, "Conectado ao IP: $pcIp", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, digite o IP do computador", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Detecta as teclas físicas de volume do celular
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (pcIp.isEmpty()) return super.onKeyDown(keyCode, event)

        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                enviarComando("$pcIp/avancar")
                return true // Bloqueia o som de aumentar no celular
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                enviarComando("$pcIp/voltar")
                return true // Bloqueia o som de diminuir no celular
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun enviarComando(url: String) {
        thread {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Erro ao conectar com o PC", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
