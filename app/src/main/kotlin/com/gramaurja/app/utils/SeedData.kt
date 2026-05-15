package com.gramaurja.app.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.gramaurja.app.data.model.Village
import com.gramaurja.app.data.model.Zone
import kotlinx.coroutines.tasks.await

object SeedData {
    suspend fun seed(db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
        val villageId = "angondhalli_01"
        val village = Village(
            villageId = villageId,
            villageName = "Angondhalli",
            villageNameKn = "ಆಂಗೊಂಡಹಳ್ಳಿ",
            district = "Bengaluru Rural",
            districtKn = "ಬೆಂಗಳೂರು ಗ್ರಾಮಾಂತರ"
        )
        db.collection("villages").document(villageId).set(village).await()

        val zone1 = Zone(
            zoneId = "zone_01",
            villageId = villageId,
            zoneName = "Transformer Zone 1",
            zoneNameKn = "ಟ್ರಾನ್ಸ್ಫಾರ್ಮರ್ ವಲಯ 1",
            transformerName = "TR-AGN-01",
            pumpsCount = 12,
            capacityKVA = 63
        )
        db.collection("zones").document("zone_01").set(zone1).await()

        val zone2 = Zone(
            zoneId = "zone_02",
            villageId = villageId,
            zoneName = "Transformer Zone 2",
            zoneNameKn = "ಟ್ರಾನ್ಸ್ಫಾರ್ಮರ್ ವಲಯ 2",
            transformerName = "TR-AGN-02",
            pumpsCount = 8,
            capacityKVA = 25
        )
        db.collection("zones").document("zone_02").set(zone2).await()
    }
}
