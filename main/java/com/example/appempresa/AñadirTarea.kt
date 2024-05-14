package com.example.appempresa


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest


import com.android.volley.toolbox.Volley


class AñadirTarea : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")
        val nombre = intent.getStringExtra("nombre")
        setContent {
            MaterialTheme {
                OtraActivityScreen(id,nombre, this)
            }
        }
    }
}

fun añadirTarea(context: Context, idUser: String, descripcion: String) {
    val url = "http://127.0.0.1:9080/main/nuevaTarea"

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
            params["idUser"] = idUser!!
            params["descripcion"] = descripcion
            return params
        }
    }

    requestQueue.add(stringRequest)
}



@Composable
fun OtraActivityScreen(id: String?,nombre:String?, activity: ComponentActivity) {
    var tareaText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Agregando tarea para: $nombre", textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tareaText,
            onValueChange = { tareaText = it },
            label = { Text("Descripción de la tarea") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!id.isNullOrEmpty()) {
                añadirTarea(context, id, tareaText)
            }
        }) {
            Text("Añadir Tarea")
        }

    }
}

