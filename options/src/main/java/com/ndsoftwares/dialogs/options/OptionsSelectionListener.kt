 // NDSoftwares

package com.ndsoftwares.dialogs.options

internal interface OptionsSelectionListener {
    fun select(index: Int)
    fun selectMultipleChoice(index: Int)
    fun deselectMultipleChoice(index: Int)
    fun isSelected(index: Int): Boolean
    fun isMultipleChoiceSelectionAllowed(index: Int): Boolean
}