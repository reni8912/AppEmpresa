package com.example.appempresa


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                LoginScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
                    val q = Volley.newRequestQueue(context)
                    val url = "http://127.0.0.1:9080/user/login"

                    val jsonObject = JSONObject()
                    jsonObject.put("email", email)
                    jsonObject.put("password", password)

                    val request = JsonObjectRequest(
                        Request.Method.POST, url, jsonObject,
                        { response ->
                            if (response.has("id") && response.has("nombre")) {
                                val id = response.getString("id")
                                val nombre = response.getString("nombre")
                                val intent = Intent(context, AñadirTarea::class.java)
                                // Puedes pasar datos adicionales al intent si es necesario
                                intent.putExtra("id", id)
                                intent.putExtra("nombre", nombre)
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "No 'id' field found in JSON response", Toast.LENGTH_SHORT).show()
                            }
                        },
                        { error ->
                            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    )

                    q.add(request)
                }) {
                    Text("Login")
                }
                Text(
                    text = "¿No tienes cuenta?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 30.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val intent = Intent(context, Register::class.java)
                    context.startActivity(intent)
                }) {
                    Text("Register")
                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        LoginScreen()
    }
}