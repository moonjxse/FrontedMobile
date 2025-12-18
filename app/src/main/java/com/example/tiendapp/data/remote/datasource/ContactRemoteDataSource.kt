package com.example.tiendapp.data.remote.datasource

import com.example.tiendapp.data.remote.api.ContactApi
import com.example.tiendapp.model.dto.ContactDto

class ContactRemoteDataSource(
    private val api: ContactApi
) {

    suspend fun getAll(): List<ContactDto> =
        api.getAllContacts()

    suspend fun create(contact: ContactDto): ContactDto =
        api.createContact(contact)

    suspend fun delete(id: Long) =
        api.deleteContact(id)
}
