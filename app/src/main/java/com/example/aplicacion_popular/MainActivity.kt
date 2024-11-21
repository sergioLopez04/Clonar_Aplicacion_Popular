package com.example.aplicacion_popular

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacion_popular.ui.theme.Aplicacion_PopularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aplicacion_PopularTheme {
                GmailApp()
            }

        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GmailApp() {
    var mostrarEnviar by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            if (!mostrarEnviar) {
                FloatingActionButton(onClick = { mostrarEnviar = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Redactar")
                }
            }
        }
    ) {
        if (mostrarEnviar) {
            pantallaEnviarCorreo(correoEnviado = { mostrarEnviar = false })
        } else {
            ListaCorreos()
        }
    }
}

@Composable
fun pantallaEnviarCorreo(correoEnviado: () -> Unit) {
    var remitente by remember { mutableStateOf("") }
    var destinatario by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Nuevo Correo", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = remitente,
            onValueChange = { remitente = it },
            label = { Text("De:") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = destinatario,
            onValueChange = { destinatario = it },
            label = { Text("Para:") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción:") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { mostrarConfirmacion = true },
            modifier = Modifier.align(Alignment.End)
        )
        {
            Text("Enviar")
        }

        if (mostrarConfirmacion) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacion = false },
                title = { Text("Correo Enviado") },
                text = { Text("El correo ha sido enviado exitosamente") },
                confirmButton = {
                    TextButton(onClick = {
                        mostrarConfirmacion = false
                        correoEnviado()
                    }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}


@Composable
fun Correo(correo: Correo, correoSelec: (Correo) -> Unit) {

    var colorEstrella by remember { mutableStateOf(false) }
    var mostrarFavorito by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { correoSelec(correo) }
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = correo.inicial,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = correo.remitente,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = correo.hora,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
            Text(
                text = correo.asunto,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = correo.mensaje,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = {
                colorEstrella = !colorEstrella
                if (colorEstrella) mostrarFavorito = true
            }) {

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Marcar como favorito",
                tint = if (colorEstrella) Color.Yellow else Color.LightGray
            )
        }
    }

    if (mostrarFavorito) {
        AlertDialog(
            onDismissRequest = { mostrarFavorito = false },
            title = { Text(text = "Favorito") },
            text = { Text(text = "Lo has añadido a favoritos") },
            confirmButton = {
                TextButton(onClick = { mostrarFavorito = false }) {
                    Text("OK")
                }
            }
        )
    }

}


@Composable
fun ListaCorreos() {
    val correos = listOf(
        Correo(
            "A",
            "Aliexpress",
            "¡Tu paquete está a punto de llegar!",
            "Hola Sergio, ¿te acuerdas de ese paquete que pediste? ¡Está en camino!",
            "18:05"
        ),
        Correo(
            "G",
            "Games",
            "Aprovecha el descuento exclusivo",
            "Hola Sergio, tengo una oferta limitada solo para ti. ¡Compra 2, lleva 3! Solo por hoy en nuestra tienda online.",
            "18:04"
        ),
        Correo(
            "G",
            "Google",
            "¡Nuevas actualizaciones en tu cuenta de Google!",
            "Hola, tu cuenta de Google acaba de recibir nuevas funciones. Echa un vistazo a las novedades y disfruta de las mejoras.",
            "17:58"
        ),
        Correo(
            "N",
            "Netflix",
            "Cambios en tu suscripción de Netflix",
            "Hola Sergio, hemos actualizado tu suscripción de Netflix. Echa un vistazo para ver las nuevas opciones y contenidos disponibles.",
            "17:57"
        ),
        Correo(
            "S",
            "Steam",
            "Tu código de descuento para Steam",
            "Hola Sergio, gracias por tu compra. Aquí tienes un código de descuento del 20% para tu próxima compra en Steam. ¡Aprovéchalo ya!",
            "17:56"
        ),
        Correo(
            "G",
            "Google Calendar",
            "¡Recuerda tu evento programado!",
            "Revisión del Proyecto.",
            "17:55"
        )
    )

    var correoSeleccionado by remember { mutableStateOf<Correo?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        correos.forEach { correo ->
            Correo(correo, correoSelec = { correoSeleccionado = it })
        }
    }

    if (correoSeleccionado != null) {
        AlertDialog(
            onDismissRequest = { correoSeleccionado = null },
            title = { Text(text = "Detalles del Correo") },
            text = {
                Column {
                    Text("Remitente: ${correoSeleccionado?.remitente}")
                    Text("Asunto: ${correoSeleccionado?.asunto}")
                    Text("Mensaje: ${correoSeleccionado?.mensaje}")
                    Text("Hora: ${correoSeleccionado?.hora}")
                }
            },
            confirmButton = {
                TextButton(onClick = { correoSeleccionado = null }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

