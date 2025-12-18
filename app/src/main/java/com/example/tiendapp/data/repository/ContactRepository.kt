package com.example.tiendapp.data.repository

import com.example.tiendapp.data.local.dao.ContactDao
import com.example.tiendapp.data.mapper.toDomain
import com.example.tiendapp.data.mapper.toEntity
import com.example.tiendapp.model.domain.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepository(
    private val contactDao: ContactDao
) {

    // READ ALL
    fun getAllContacts(): Flow<List<Contact>> =
        contactDao.getAll().map { list ->
            list.map { it.toDomain() }
        }

    // CREATE
    suspend fun insertContact(contact: Contact) {
        contactDao.insert(contact.toEntity())
    }

    // READ BY ID
    suspend fun getContactById(id: Long): Contact? =
        contactDao.getById(id)?.toDomain()

    // DELETE
    suspend fun deleteContact(contact: Contact) {
        contactDao.delete(contact.toEntity())
    }

    // DELETE ALL
    suspend fun deleteAllContacts() {
        contactDao.deleteAll()
    }

    // COUNT
    suspend fun getContactCount(): Int =
        contactDao.count()
}
