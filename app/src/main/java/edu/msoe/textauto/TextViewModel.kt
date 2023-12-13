package edu.msoe.textauto

import androidx.lifecycle.ViewModel
import edu.msoe.textauto.DataBase.TextRepository

/**
 * Main repository
 */
class TextViewModel : ViewModel() {
    private val repository = TextRepository.get()

    fun getRepository(): TextRepository {
        return repository
    }



    suspend fun deleteAllData() {
        repository.removeAll()
    }
}