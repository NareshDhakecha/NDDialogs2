

@file:Suppress("unused")

package com.ndsoftwares.dialogs.input

/**
 * Static helper methods for validations.
 */
object Validation {

    /**
     * Report an valid text.
     */
    fun success(): ValidationResult = ValidationResult(true)

    /**
     * Report an invalid text with an error text.
     */
    fun failed(errorText: String): ValidationResult = ValidationResult(false, errorText)
}
