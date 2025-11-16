package com.example.callrecorder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import org.androix.wowcallrecorder.service.CallRecordingService

class MainActivity : ComponentActivity() {

    private var isServiceRunning by mutableStateOf(false)

    private val requiredPermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS
    )

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            startRecordingService()
        } else {
            Toast.makeText(this, "Permissions are required for call recording",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CallRecorderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CallRecorderScreen(
                        isServiceRunning = isServiceRunning,
                        onStartClick = { handleStartClick() },
                        onStopClick = { handleStopClick() }
                    )
                }
            }
        }
    }

    private fun handleStartClick() {
        if (checkPermissions()) {
            startRecordingService()
        } else {
            requestPermissions()
        }
    }

    private fun handleStopClick() {
        stopRecordingService()
    }

    private fun checkPermissions(): Boolean {
        return requiredPermissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) ==
                    PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        permissionLauncher.launch(requiredPermissions)
    }

    private fun startRecordingService() {
        val intent = Intent(this, CallRecordingService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        isServiceRunning = true
    }

    private fun stopRecordingService() {
        val intent = Intent(this, CallRecordingService::class.java)
        stopService(intent)
        isServiceRunning = false
    }
}

@Composable
fun CallRecorderScreen(
    isServiceRunning: Boolean,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Title
        Text(
            text = "Call Recorder",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Status Icon
        Surface(
            modifier = Modifier.size(120.dp),
            shape = RoundedCornerShape(60.dp),
            color = if (isServiceRunning)
                Color(0xFF4CAF50).copy(alpha = 0.2f)
            else
                Color(0xFFFF5722).copy(alpha = 0.2f)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isServiceRunning) "🎙️" else "⏸️",
                    fontSize = 48.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Status Text
        Text(
            text = if (isServiceRunning) "Service Active" else "Service Stopped",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = if (isServiceRunning) Color(0xFF4CAF50) else Color(0xFFFF5722)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Start Button
        Button(
            onClick = onStartClick,
            enabled = !isServiceRunning,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                disabledContainerColor = Color(0xFF4CAF50).copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Start Recording Service",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stop Button
        Button(
            onClick = onStopClick,
            enabled = isServiceRunning,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5722),
                disabledContainerColor = Color(0xFFFF5722).copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Stop Recording Service",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Warning Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF3E0)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "⚠️ Important Information",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Ensure all permissions are granted\n" +
                            "• Recordings saved in Music/CallRecordings\n" +
                            "• May not work on Android 10+ on some devices\n" +
                            "• Check local laws before recording calls",
                    fontSize = 12.sp,
                    color = Color(0xFFE65100),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun CallRecorderTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF2196F3),
            secondary = Color(0xFF4CAF50),
            background = Color(0xFFFAFAFA)
        ),
        content = content
    )
}