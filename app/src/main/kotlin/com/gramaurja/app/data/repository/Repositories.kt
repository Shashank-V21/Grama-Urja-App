package com.gramaurja.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.gramaurja.app.data.model.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    fun getCurrentUser() = auth.currentUser
    fun isLoggedIn() = auth.currentUser != null
    suspend fun signIn(email: String, pass: String) = auth.signInWithEmailAndPassword(email, pass).await()
    suspend fun signUp(email: String, pass: String) = auth.createUserWithEmailAndPassword(email, pass).await()
    fun signOut() = auth.signOut()
    suspend fun resetPassword(email: String) = auth.sendPasswordResetEmail(email).await()
}

class UserRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    private val usersCollection = db.collection("users")

    suspend fun getUserProfile(userId: String) = usersCollection.document(userId).get().await().toObject(UserProfile::class.java)
    suspend fun saveUserProfile(profile: UserProfile) = usersCollection.document(profile.userId).set(profile).await()
    
    fun observeUserProfile(userId: String): Flow<UserProfile?> = callbackFlow {
        val listener = usersCollection.document(userId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            trySend(snapshot?.toObject(UserProfile::class.java))
        }
        awaitClose { listener.remove() }
    }
}

class VillageRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    suspend fun getVillages() = db.collection("villages").get().await().toObjects(Village::class.java)
    suspend fun getZones(villageId: String) = db.collection("zones")
        .whereEqualTo("villageId", villageId).get().await().toObjects(Zone::class.java)
}

class PowerRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun observePowerStatus(zoneId: String): Flow<PowerStatus?> = callbackFlow {
        val listener = db.collection("powerStatus").document(zoneId).addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            trySend(snapshot?.toObject(PowerStatus::class.java))
        }
        awaitClose { listener.remove() }
    }

    fun observeStatusHistory(zoneId: String): Flow<List<StatusHistory>> = callbackFlow {
        val listener = db.collection("statusHistory")
            .whereEqualTo("zoneId", zoneId)
            .orderBy("updatedAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(20)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                trySend(snapshot?.toObjects(StatusHistory::class.java) ?: emptyList())
            }
        awaitClose { listener.remove() }
    }

    suspend fun updatePowerStatus(zoneId: String, status: String, updatedBy: String, updatedByUserId: String) {
        val powerData = PowerStatus(zoneId, status, com.google.firebase.Timestamp.now(), updatedBy, updatedByUserId)
        db.collection("powerStatus").document(zoneId).set(powerData).await()
        
        // Add to history
        val history = StatusHistory(
            zoneId = zoneId,
            status = status,
            updatedAt = com.google.firebase.Timestamp.now(),
            updatedBy = updatedBy,
            updatedByUserId = updatedByUserId
        )
        db.collection("statusHistory").add(history).await()
    }
}

class PumpRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun observePumpStatus(zoneId: String): Flow<PumpStatus?> = callbackFlow {
        val listener = db.collection("pumpStatus").document(zoneId).addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            trySend(snapshot?.toObject(PumpStatus::class.java))
        }
        awaitClose { listener.remove() }
    }

    suspend fun sendCommand(command: PumpCommand) {
        db.collection("pumpCommands").add(command).await()
    }
    
    suspend fun updatePumpStatus(zoneId: String, status: String, userId: String, userName: String) {
        val statusData = PumpStatus(zoneId, status, com.google.firebase.Timestamp.now(), userName, userId)
        db.collection("pumpStatus").document(zoneId).set(statusData).await()
        
        // Log the action
        val log = PumpLog(
            zoneId = zoneId,
            userId = userId,
            userName = userName,
            action = status,
            status = if (status == "ON") "running" else "stopped",
            startTime = if (status == "ON") com.google.firebase.Timestamp.now() else null,
            stopTime = if (status == "OFF") com.google.firebase.Timestamp.now() else null,
            createdAt = com.google.firebase.Timestamp.now()
        )
        db.collection("pumpLogs").add(log).await()
    }

    fun observeUserLogs(userId: String): Flow<List<PumpLog>> = callbackFlow {
        val listener = db.collection("pumpLogs")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(50)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                trySend(snapshot?.toObjects(PumpLog::class.java) ?: emptyList())
            }
        awaitClose { listener.remove() }
    }
}
