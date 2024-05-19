package com.example.appempresa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.appempresa.ui.theme.AppEmpresaTheme

class Register : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RegisterScreen()
            }
        }
    }
}

@Composable
fun RegisterScreen() {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            registrarUsuario(context, nombre, email, password)
        }) {
            Text("Register")
        }
        Text(
            text = "¿Ya tienes cuenta?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = 30.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val intent = Intent(context, Login::class.java)
            context.startActivity(intent)
        }) {
            Text("Login")
        }
    }
}

fun registrarUsuario(context: Context, nombre: String, email: String, password: String) {
    val url = "http://IP:9080/main/userregister"

    val requestQueue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(
        Method.POST, url,
        Response.Listener<String> { response ->
            // Aquí puedes manejar la respuesta del servidor
            Toast.makeText(context, response, Toast.LENGTH_LONG).show()
        },
        Response.ErrorListener { error ->
            // Aquí puedes manejar los errores
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
        }
    ) {
        override fun getParams(): Map<String, String> {
            val params = HashMap<String, String>()
            params["nombre"] = nombre
            params["email"] = email
            params["password"] = password
            return params
        }
    }

    requestQueue.add(stringRequest)
}
