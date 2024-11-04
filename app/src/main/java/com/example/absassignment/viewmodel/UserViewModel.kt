package com.example.absassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absassignment.data.model.User
import com.example.absassignment.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>  = _users

    private val _uiState = MutableLiveData<UserUiState>()
    val uiState: LiveData<UserUiState> get() = _uiState

    fun fetchUsers(number: Int) {
        _uiState.value = UserUiState.Loading // Set loading state
        viewModelScope.launch {
            try {
                _users.value = emptyList()

                val fetchedUsers = repository.getUsers(number)
                    _uiState.value = UserUiState.Success(fetchedUsers!!)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error("Failed to fetch users: ${e.message}")
            }
        }
    }


    sealed class UserUiState {
        object Loading : UserUiState()
        data class Success(val users: List<User>) : UserUiState()
        data class Error(val message: String) : UserUiState()
    }
}
