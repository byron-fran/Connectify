package com.example.connectify.utils

import com.example.connectify.data.local.entities.ContactEntity
import com.example.connectify.domain.models.Contact

fun Contact.toContactEntity(): ContactEntity {
    return ContactEntity(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        email = email,
        imageUrl = imageUrl,

        )
}

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        email = email,
        imageUrl = imageUrl,
    )

}