package com.gramaurja.app.util

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.gramaurja.app.data.model.*
import kotlinx.coroutines.tasks.await

object SeedData {
    suspend fun seedAll() {
        val db = FirebaseFirestore.getInstance()
        
        // Check if already seeded
        val villageCheck = db.collection("villages").limit(1).get().await()
        if (!villageCheck.isEmpty) return

        // 1. Villages
        val v1 = Village("v1", "Green Valley", "ಹಸಿರು ಕಣಿವೆ", "Shimoga", "ಶಿವಮೊಗ್ಗ")
        val v2 = Village("v2", "River Bank", "ನದಿ ತೀರ", "Mandya", "ಮಂಡ್ಯ")
        db.collection("villages").document(v1.villageId).set(v1).await()
        db.collection("villages").document(v2.villageId).set(v2).await()

        // 2. Transformer Zones
        val z1 = Zone("z1", "v1", "Zone A - North", "ವಲಯ ಎ - ಉತ್ತರ", "Transformer T-01", 12, 100)
        val z2 = Zone("z2", "v1", "Zone B - South", "ವಲಯ ಬಿ - ದಕ್ಷಿಣ", "Transformer T-02", 8, 75)
        val z3 = Zone("z3", "v2", "Main Cluster", "ಮುಖ್ಯ ಕ್ಲಸ್ಟರ್", "Transformer M-01", 20, 250)
        
        db.collection("zones").document(z1.zoneId).set(z1).await()
        db.collection("zones").document(z2.zoneId).set(z2).await()
        db.collection("zones").document(z3.zoneId).set(z3).await()

        // 3. Initial Power Status (Real-time targets)
        val p1 = PowerStatus(z1.zoneId, "ON", Timestamp.now(), "System", "system_id")
        val p2 = PowerStatus(z2.zoneId, "OFF", Timestamp.now(), "System", "system_id")
        val p3 = PowerStatus(z3.zoneId, "ON", Timestamp.now(), "System", "system_id")
        
        db.collection("powerStatus").document(z1.zoneId).set(p1).await()
        db.collection("powerStatus").document(z2.zoneId).set(p2).await()
        db.collection("powerStatus").document(z3.zoneId).set(p3).await()
    }
}
