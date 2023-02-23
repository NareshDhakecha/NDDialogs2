 // NDSoftwares

package com.ndsoftwares.dialogs.time

/**
 * Time formats that can be used.
 */
enum class TimeFormat {

    /** HH:mm:ss (e. g. 12h 10m 30s) */
    HH_MM_SS,

    /** HH:mm (e.g. 20h 30m) */
    HH_MM,

    /** mm:ss (e.g. 12 30m) */
    MM_SS,

    /** m:ss (e.g. 6m 0m) */
    M_SS,

    /** HH (e.g. 8h) */
    HH,

    /** MM (e.g. 12m) */
    MM,

    /** ss (e.g. 45s) */
    SS;

    val length = this.name.filterNot { it == '_' }.length
}