

@file:Suppress("unused")

package com.ndsoftwares.dialogs.input

/**
 * Result of the validation success or failure of the validation. At failure, an error text can be displayed.
 */
data class ValidationResult(
    internal val valid: Boolean = true,
    internal val errorText: String? = null
)


