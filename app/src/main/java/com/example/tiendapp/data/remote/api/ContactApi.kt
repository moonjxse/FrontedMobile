package com.example.tiendapp.data.remote.api

import com.example.tiendapp.model.dto.ContactDto
import retrofit2.http.*

interface ContactApi {

    @GET("api/contacts")
    suspend fun getAllContacts(): List<ContactDto>

    @GET("api/contacts/{id}")
    suspend fun getContactById(
        @Path("id") id: Long
    ): ContactDto

    @POST("api/contacts")
    suspend fun createContact(
        @Body contact: ContactDto
    ): ContactDto

    @DELETE("api/contacts/{id}")
    suspend fun deleteContact(
        @Path("id") id: Long
    )
}
