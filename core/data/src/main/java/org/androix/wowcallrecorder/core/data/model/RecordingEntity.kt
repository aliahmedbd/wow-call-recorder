package org.androix.wowcallrecorder.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordings")
data class RecordingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val uri: String,
    val fileName: String,
    val timestamp: Long,
    val durationMs: Long,
    val direction: String, // Inbound | Outbound
    val phoneNumber: String?,
    val contactName: String?,
    val sizeBytes: Long,
    val aiTitle: String?,
    val aiSummary: String?,
    val transcriptPath: String?,
    val sentiment: Float?,
    val keywords: List<String>,
    val favorite: Boolean,
    val flags: Int
)
