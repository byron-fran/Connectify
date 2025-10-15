package com.example.connectify.utils

object SharedTransition {
    fun sharedTransitionImageKey(id : String) : String {
        return "shared_transition_image_key_$id"

    }

    fun sharedTransitionTitleKey(id : String) : String {
        return "shared_transition_title_key_$id"
    }

}