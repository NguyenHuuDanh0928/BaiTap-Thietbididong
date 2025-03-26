package com.example.api

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class TaskViewModel : ViewModel() {
    private val _uiState = mutableStateOf<UiState<List<Tasks>>>(UiState.Loading)
    val uiState: State<UiState<List<Tasks>>> = _uiState

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                Log.d("API_CALL", "Starting API call...")
                val response = RetrofitInstance.api.getTasks()
                if (response.isSuccess) {
                    _uiState.value = UiState.Success(response.data)
                    Log.d("API_CALL", "Successfully loaded ${response.data.size} tasks")
                } else {
                    _uiState.value = UiState.Error("API returned error: ${response.message}")
                    Log.e("API_ERROR", "API returned error: ${response.message}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error fetching data: ${e.message}")
                Log.e("API_ERROR", "Error fetching data: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            try {
                val newTask = Tasks(
                    id = 0,
                    title = title,
                    description = description,
                    status = "Pending",
                    priority = "Medium",
                    category = "General",
                    dueDate = "2025-03-25",
                    createdAt = "2025-03-22",
                    updatedAt = "2025-03-22",
                    subtasks = emptyList(),
                    attachments = emptyList(),
                    reminders = emptyList()
                )
                val response = RetrofitInstance.api.addTask(newTask)
                if (response.isSuccess) {
                    val currentTasks = (_uiState.value as? UiState.Success)?.data ?: emptyList()
                    _uiState.value = UiState.Success(currentTasks + response.data)
                    Log.d("API_CALL", "Task added successfully: ${response.data.title}")
                } else {
                    Log.e("API_ERROR", "Failed to add task: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error adding task: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        val currentTasks = (_uiState.value as? UiState.Success)?.data ?: return
        val updatedTasks = currentTasks.map { task ->
            if (task.id == taskId) task.copy(status = if (isCompleted) "Completed" else "Pending")
            else task
        }
        _uiState.value = UiState.Success(updatedTasks)
    }
}