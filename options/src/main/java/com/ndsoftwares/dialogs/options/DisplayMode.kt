 // NDSoftwares

package com.ndsoftwares.dialogs.options

/**
 *  The display modes that can be used.
 *  [LIST], [GRID_HORIZONTAL]
 */
enum class DisplayMode {

    /**
     * Depending on the amount of [Option]s,
     * the options are be displayed in one to two rows with at most 4 options per row.
     * With more than 8 options, the options are displayed in a one row horizontal scrollable view.
     */
    GRID_HORIZONTAL,

    /**
     * Depending on the amount of [Option]s,
     * the options are be displayed in one to two rows with at most 4 options per row.
     * With more than 8 options, the options are displayed in a vertical scrollable view.
     */
    GRID_VERTICAL,


    /**
     * Displays the options in a vertical list.
     */
    LIST
}