package com.murad.kareem_school.helpers

/**
 * This class is made to handle LiveData
 * -- what i mean by this
 * -- when we get a live data in an activity or fragment
 * -- and once a an activity or fragment is rotated
 * -- then the live data object will be observed again
 *
 *  ex :) lets suppose we have a text view shows if the network call fails or succeed
 *      - now in case of failure lets say , the textview will show fail , and if the screen is rotated
 *      - then the live data object that
 *
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}