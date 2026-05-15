package com.gramaurja.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramaurja.app.data.model.*
import com.gramaurja.app.data.repository.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepo: AuthRepository = AuthRepository()) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                authRepo.signIn(email, pass)
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}

class DashboardViewModel(
    private val userRepo: UserRepository = UserRepository(),
    private val powerRepo: PowerRepository = PowerRepository(),
    private val authRepo: AuthRepository = AuthRepository()
) : ViewModel() {
    
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    val powerStatus: StateFlow<PowerStatus?> = _userProfile
        .filterNotNull()
        .flatMapLatest { profile -> powerRepo.observePowerStatus(profile.zoneId) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        authRepo.getCurrentUser()?.uid?.let { uid ->
            viewModelScope.launch {
                userRepo.observeUserProfile(uid).collect { _userProfile.value = it }
            }
        }
    }
}

class PumpViewModel(
    private val pumpRepo: PumpRepository = PumpRepository(),
    private val authRepo: AuthRepository = AuthRepository()
) : ViewModel() {
    
    private val _zoneId = MutableStateFlow("")
    val pumpStatus = _zoneId.flatMapLatest { id ->
        if (id.isEmpty()) flowOf(null) else pumpRepo.observePumpStatus(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setZone(zoneId: String) { _zoneId.value = zoneId }

    fun togglePump(status: String, userName: String) {
        viewModelScope.launch {
            val uid = authRepo.getCurrentUser()?.uid ?: return@launch
            pumpRepo.updatePumpStatus(_zoneId.value, status, uid, userName)
            // Also log it
        }
    }
}
