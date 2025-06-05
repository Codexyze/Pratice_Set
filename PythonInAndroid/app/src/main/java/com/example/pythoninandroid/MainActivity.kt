package com.example.pythoninandroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.pythoninandroid.ui.theme.PythonInAndroidTheme
import java.io.File

//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            PythonInAndroidTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Box(modifier = Modifier.padding(innerPadding)) {
//                        PythonText()
//
//                    }
//
//                }
//            }
//        }
//    }
//}
//@Composable
//fun PythonText() {
//    var pythonOutput by remember { mutableStateOf("Loading...") }
//    val context = LocalContext.current
//
//    LaunchedEffect(true) {
//        if (!Python.isStarted()) {
//            Python.start(AndroidPlatform(context))
//        }
//        val py = Python.getInstance()
//        val pyObj = py.getModule("script")
//        pythonOutput = pyObj.callAttr("say_hello").toString()
//    }
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text(text = pythonOutput)
//        Button(onClick = {
//            val py = Python.getInstance()
//            pythonOutput = py.getModule("script").callAttr("say_hello").toString()
//        }) {
//            Text("New joke")
//        }
//    }
//
//
//}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start Python engine (Chaquopy)
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var userInput by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Enter Text for DOCX") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val filePath = generateDocFromPython(context, userInput)
                if (filePath != null) {
                    Toast.makeText(context, "Saved at: $filePath", Toast.LENGTH_LONG).show()
                    openFile(context, filePath)
                } else {
                    Toast.makeText(context, "Failed to generate file", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Generate DOCX")
            }
        }
    }
}

fun generateDocFromPython(context: Context, content: String): String? {
    return try {
        val fileName = "MyFile.docx"
        val file = File(context.getExternalFilesDir(null), fileName)

        val py = Python.getInstance()
        val pyObj = py.getModule("script")
        pyObj.callAttr("document", content, file.absolutePath)

        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun openFile(context: Context, path: String) {
    val file = File(path)
    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(Intent.createChooser(intent, "Open with"))
}

fun example(){

}