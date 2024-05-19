package com.example.appempresa


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest


import com.android.volley.toolbox.Volley
import org.json.JSONObject


class AñadirTarea : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        setContent {
            MaterialTheme {
                OtraActivityScreen(id, name, this)
            }
        }
    }
}

fun añadirTarea(context: Context, id_user: String, descripcion: String, onTaskAdded: () -> Unit) {
    val url = "http://IP:9080/main/nuevaTarea"

    val requestQueue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(
        Method.POST, url,
        Response.Listener<String> { response ->
            // Aquí puedes manejar la respuesta del servidor
            Toast.makeText(context, response, Toast.LENGTH_LONG).show()

            // Llama a la función proporcionada para indicar que se ha agregado una nueva tarea
            onTaskAdded()
        },
        Response.ErrorListener { error ->
            // Aquí puedes manejar los errores
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
        }
    ) {
        override fun getParams(): Map<String, String> {
            val params = HashMap<String, String>()
            params["id_user"] = id_user
            params["descripcion"] = descripcion
            return params
        }
    }

    requestQueue.add(stringRequest)
}

fun obtenerTareas(context: Context, id_user: String, onResult: (List<Tarea>) -> Unit) {
    val url = "http://IP:9080/main/usuario/$id_user"

    val requestQueue = Volley.newRequestQueue(context)

    val jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET, url, null,
        { response ->
            Log.d("JSONResponse", response.toString())
            val tareas = mutableListOf<Tarea>()
            for (i in 0 until response.length()) {
                val tareaJson = response.getJSONObject(i)
                val id = tareaJson.optInt("id")
                val id_user = tareaJson.optInt("id_user")
                val descripcion = tareaJson.optString("descripcion")

                if (id != null && id_user != null && descripcion != null) {
                    val tarea = Tarea(
                        id = id,
                        id_user = id_user,
                        descripcion = descripcion
                    )
                    tareas.add(tarea)
                } else {
                    Log.e("Error", "Error parsing tarea: $tareaJson")
                }
            }
            onResult(tareas)
        },
        { error ->
            Toast.makeText(context, "Error obteniendo tareas: $error", Toast.LENGTH_LONG).show()
            Log.e("Error", "Error en la solicitud de tareas", error)
        }
    )

    requestQueue.add(jsonArrayRequest)
}

fun eliminarTarea(context: Context, id: Int, onTaskDeleted: () -> Unit) {
    val url = "http://IP:9080/main/tarea/$id"

    val requestQueue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.DELETE, url,
        { response ->
            // Manejar la respuesta del servidor
            Toast.makeText(context, response, Toast.LENGTH_LONG).show()

            // Llama a la función proporcionada para indicar que se ha eliminado una tarea
            onTaskDeleted()
        },
        { error ->
            // Manejar errores
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
        }
    )

    requestQueue.add(stringRequest)
}

fun cambiarDescripcionTarea(context: Context, id: Int, nuevaDescripcion: String, onTaskUpdated: () -> Unit) {
    val url = "http://IP:9080/main/descripcion"

    val requestQueue = Volley.newRequestQueue(context)

    val jsonObject = JSONObject()
    jsonObject.put("id", id)
    jsonObject.put("descripcion", nuevaDescripcion)

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.PUT, url, jsonObject,
        { response ->
            // Manejar la respuesta del servidor
            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()

            // Llama a la función proporcionada para indicar que se ha actualizado una tarea
            onTaskUpdated()
        },
        { error ->
            // Manejar errores
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
        }
    )

    requestQueue.add(jsonObjectRequest)
}

data class Tarea(val id: Int, val id_user: Int, val descripcion: String)

@Composable
fun OtraActivityScreen(id: String?, nombre: String?, activity: ComponentActivity) {
    var tareaText by remember { mutableStateOf("") }
    var tareas by remember { mutableStateOf<List<Tarea>>(emptyList()) }
    val context = LocalContext.current

    // Función para cargar las tareas
    fun cargarTareas() {
        if (!id.isNullOrEmpty()) {
            obtenerTareas(context, id) { tareasObtenidas ->
                tareas = tareasObtenidas
            }
        }
    }

    // Cargar las tareas al iniciar la pantalla
    LaunchedEffect(id) {
        cargarTareas()
    }
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
                añadirTarea(context, id!!, tareaText) {
                    // Después de agregar la tarea, recargar la lista de tareas
                    cargarTareas()
                    // Limpiar el campo de texto después de agregar la tarea
                    tareaText = ""
                }
            }
        }) {
            Text("Añadir Tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Tareas existentes:")

        tareas.forEach { tarea ->
            var isEditing by remember { mutableStateOf(false) }
            var newDescripcion by remember { mutableStateOf(tarea.descripcion) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = newDescripcion,
                        onValueChange = { newDescripcion = it },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        // Al hacer clic en Guardar, actualiza la descripción de la tarea
                        cambiarDescripcionTarea(context, tarea.id, newDescripcion) {
                            // Después de actualizar la descripción, recargar la lista de tareas
                            cargarTareas()
                        }
                        isEditing = false
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Guardar")
                    }
                } else {
                    Text(text = tarea.descripcion, modifier = Modifier.weight(1f))
                    IconButton(onClick = { isEditing = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = {
                        eliminarTarea(context, tarea.id) {
                            // Después de eliminar la tarea, recargar la lista de tareas
                            cargarTareas()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}


