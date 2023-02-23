

package com.ndsoftwares.dialogs.calendar

/**
 * Calendar modes that can be used.
 */
enum class CalendarMode(internal val rows: Int) {

    /**
     * Week view with 1 row.
     */
    WEEK_1(1),

    /**
     * Week view with 2 rows.
     */
    WEEK_2(2),

    /**
     * Week view with 3 rows.
     */
    WEEK_3(3),

    /**
     * Month view.
     */
    MONTH(6)
}