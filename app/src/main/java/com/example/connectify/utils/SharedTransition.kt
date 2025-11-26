package com.example.connectify.utils

object SharedTransition {
    fun sharedTransitionImageKey(id : String, screenKey: String? = null) : String {
        return if (screenKey != null) {
            "shared_transition_image_key_${screenKey}_$id"
        } else {
            "shared_transition_image_key_$id"
        }
    }

    fun sharedTransitionTitleKey(id : String, screenKey: String? = null) : String {
        return if (screenKey != null) {
            "shared_transition_title_key_${screenKey}_$id"
        } else {
            "shared_transition_title_key_$id"
        }
    }

}