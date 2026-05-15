package com.gramaurja.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class UserProfile(
    @DocumentId val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val mobile: String = "",
    val villageId: String = "",
    val zoneId: String = "",
    val language: String = "en",
    val role: String = "viewer", // admin, operator, viewer
    val notificationsEnabled: Boolean = true,
    val onboarded: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
)

data class Village(
    @DocumentId val villageId: String = "",
    val villageName: String = "",
    val villageNameKn: String = "",
    val district: String = "",
    val districtKn: String = "",
    val createdAt: Timestamp = Timestamp.now()
)

data class Zone(
    @DocumentId val zoneId: String = "",
    val villageId: String = "",
    val zoneName: String = "",
    val zoneNameKn: String = "",
    val transformerName: String = "",
    val pumpsCount: Int = 0,
    val capacityKVA: Int = 0,
    val createdAt: Timestamp = Timestamp.now()
)

data class PowerStatus(
    @DocumentId val zoneId: String = "",
    val status: String = "UNKNOWN", // ON, OFF, UNKNOWN
    val updatedAt: Timestamp = Timestamp.now(),
    val updatedBy: String = "",
    val updatedByUserId: String = ""
)

data class StatusHistory(
    @DocumentId val historyId: String = "",
    val zoneId: String = "",
    val status: String = "UNKNOWN",
    val updatedAt: Timestamp = Timestamp.now(),
    val updatedBy: String = "",
    val updatedByUserId: String = ""
)

data class AppNotification(
    @DocumentId val notificationId: String = "",
    val userId: String = "",
    val zoneId: String = "",
    val message: String = "",
    val kind: String = "info", // on, off, info
    val read: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
)

data class PumpStatus(
    @DocumentId val zoneId: String = "",
    val status: String = "UNKNOWN", // ON, OFF, UNKNOWN
    val updatedAt: Timestamp = Timestamp.now(),
    val updatedBy: String = "",
    val updatedByUserId: String = ""
)

data class PumpCommand(
    @DocumentId val commandId: String = "",
    val zoneId: String = "",
    val command: String = "", // START, STOP
    val requestedByUserId: String = "",
    val requestedByName: String = "",
    val requestedAt: Timestamp = Timestamp.now(),
    val status: String = "pending" // pending, success, failed
)

data class PumpLog(
    @DocumentId val logId: String = "",
    val zoneId: String = "",
    val userId: String = "",
    val userName: String = "",
    val action: String = "",
    val crop: String = "",
    val acres: Double = 0.0,
    val pumpSize: String = "",
    val estimatedMinutes: Int = 0,
    val startTime: Timestamp? = null,
    val stopTime: Timestamp? = null,
    val actualMinutes: Int = 0,
    val status: String = "planned", // planned, running, completed, stopped
    val createdAt: Timestamp = Timestamp.now()
)
