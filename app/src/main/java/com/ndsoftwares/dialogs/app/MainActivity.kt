package com.ndsoftwares.dialogs.app

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.textfield.TextInputLayout
import com.ndsoftwares.dialogs.app.custom.CustomNDDialog
import com.ndsoftwares.dialogs.app.databinding.ActivityMainBinding
import com.ndsoftwares.dialogs.app.utils.SheetExample
import com.ndsoftwares.dialogs.calc.NDCalcDialog
import com.ndsoftwares.dialogs.calendar.CalendarMode
import com.ndsoftwares.dialogs.calendar.NDCalendarDialog
import com.ndsoftwares.dialogs.calendar.SelectionMode
import com.ndsoftwares.dialogs.calendar.TimeLine
import com.ndsoftwares.dialogs.color.ColorView
import com.ndsoftwares.dialogs.color.NDColorDialog
import com.ndsoftwares.dialogs.core.*
import com.ndsoftwares.dialogs.core.layoutmanagers.CustomStaggeredGridLayoutManager
import com.ndsoftwares.dialogs.info.NDInfoDialog
import com.ndsoftwares.dialogs.input.NDInputDialog
import com.ndsoftwares.dialogs.input.Validation
import com.ndsoftwares.dialogs.input.type.InputCheckBox
import com.ndsoftwares.dialogs.input.type.InputEditText
import com.ndsoftwares.dialogs.input.type.InputRadioButtons
import com.ndsoftwares.dialogs.input.type.InputSpinner
import com.ndsoftwares.dialogs.lottie.LottieAnimation
import com.ndsoftwares.dialogs.lottie.cancelCoverAnimation
import com.ndsoftwares.dialogs.lottie.withCoverLottieAnimation
import com.ndsoftwares.dialogs.options.DisplayMode
import com.ndsoftwares.dialogs.options.NDOptionsDialog
import com.ndsoftwares.dialogs.options.Option
import com.ndsoftwares.dialogs.time.NDTimeDialog
import com.ndsoftwares.dialogs.time.TimeFormat
import com.ndsoftwares.dialogs.time_clock.NDClockTimeDialog
import com.ndsoftwares.sample.utils.toFormattedDate
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var bv: ActivityMainBinding
    private val prefs: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    private var dialogStyle: DialogStyle? = null
    private var themeMode: ThemeMode? = null

    companion object {
        @Suppress("unused")
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        loadPreferences()
        applyThemeMode()
        super.onCreate(savedInstanceState)
        bv = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setup()

    }

    private fun setup() {
        setSupportActionBar(bv.toolbar)

        bv.exampleRecyclerView.layoutManager =
            CustomStaggeredGridLayoutManager(2, RecyclerView.VERTICAL, false)
        bv.exampleRecyclerView.adapter = BottomSheetExampleAdapter(this, ::showBottomSheet)

        bv.sheetStyleButtonGroup.check(when (dialogStyle) {
            DialogStyle.BOTTOM_SHEET -> R.id.bottomSheet
            DialogStyle.DIALOG -> R.id.dialog
            null -> R.id.random
        })

        bv.sheetStyleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                dialogStyle = when (checkedId) {
                    R.id.bottomSheet -> DialogStyle.BOTTOM_SHEET
                    R.id.dialog -> DialogStyle.DIALOG
                    else -> null
                }
                setPrefString(Preference.SHEET_STYLE, dialogStyle?.name)
            }
        }

        bv.themeButtonGroup.check(when (themeMode) {
            ThemeMode.NIGHT_MODE -> R.id.nightMode
            ThemeMode.DAY_MODE -> R.id.dayMode
            else -> R.id.auto
        })

        bv.themeButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                themeMode = when (checkedId) {
                    R.id.dayMode -> ThemeMode.DAY_MODE
                    R.id.nightMode -> ThemeMode.NIGHT_MODE
                    else -> ThemeMode.AUTO
                }
                setPrefString(Preference.THEME_MODE, themeMode?.name)
                applyThemeMode()
            }
        }
    }

    private fun getSheetStyle(): DialogStyle = dialogStyle ?: DialogStyle.values().random()

    private fun showBottomSheet(example: SheetExample) {

        when (example) {
            SheetExample.CALC -> showCalcSheet()
            SheetExample.OPTIONS_LIST -> showOptionsSheetList()
            SheetExample.OPTIONS_HORIZONTAL_SMALL -> showOptionsSheetGridSmall(DisplayMode.GRID_HORIZONTAL)
            SheetExample.OPTIONS_HORIZONTAL_MIDDLE -> showOptionsSheetGridMiddle(DisplayMode.GRID_HORIZONTAL)
            SheetExample.OPTIONS_HORIZONTAL_LARGE -> showOptionsSheetGridLarge(DisplayMode.GRID_HORIZONTAL)
            SheetExample.OPTIONS_VERTICAL_SMALL -> showOptionsSheetGridSmall(DisplayMode.GRID_VERTICAL)
            SheetExample.OPTIONS_VERTICAL_MIDDLE -> showOptionsSheetGridMiddle(DisplayMode.GRID_VERTICAL)
            SheetExample.OPTIONS_VERTICAL_LARGE -> showOptionsSheetGridLarge(DisplayMode.GRID_VERTICAL)
            SheetExample.COLOR -> showColorSheet()
            SheetExample.COLOR_TEMPLATE -> showColorSheetTemplate()
            SheetExample.COLOR_CUSTOM -> showColorSheetCustom()
            SheetExample.CLOCK_TIME -> showClockTimeSheet()
            SheetExample.TIME_HH_MM_SS -> showTimeSheet(TimeFormat.HH_MM_SS)
            SheetExample.TIME_HH_MM -> showTimeSheet(TimeFormat.HH_MM)
            SheetExample.TIME_MM_SS -> showTimeSheet(TimeFormat.MM_SS)
            SheetExample.TIME_M_SS -> showTimeSheet(TimeFormat.M_SS)
            SheetExample.TIME_SS -> showTimeSheet(TimeFormat.SS)
            SheetExample.TIME_MM -> showTimeSheet(TimeFormat.MM)
            SheetExample.TIME_HH -> showTimeSheet(TimeFormat.HH)
            SheetExample.CALENDAR_RANGE_MONTH -> showCalendarSheet()
            SheetExample.CALENDAR_WEEK1 -> showCalendarSheetWeek1()
            SheetExample.CALENDAR_RANGE_WEEK2 -> showCalendarSheetWeek2()
            SheetExample.CALENDAR_RANGE_WEEK3 -> showCalendarSheetWeek3()
            SheetExample.INFO -> showInfoSheet()
            SheetExample.INFO_COVER_IMAGE_1 -> showInfoSheetTopStyleTop()
            SheetExample.INFO_COVER_IMAGE_2 -> showInfoSheetTopStyleBottom()
            SheetExample.INFO_COVER_IMAGE_3 -> showInfoSheetTopStyleMixed()
            SheetExample.INFO_LOTTIE -> showInfoSheetLottie()
            SheetExample.INPUT_SHORT -> showInputSheetShort()
            SheetExample.INPUT_LONG -> showInputSheetLong()
            SheetExample.INPUT_PASSWORD -> showInputSheetPassword()
            SheetExample.CUSTOM1 -> showCustomSheet()
        }
    }

    private fun showCustomSheet() {

        CustomNDDialog().show(this) {
            style(getSheetStyle())
            title("Custom Example")
            onPositive("Cool")
        }
    }

    private fun showColorSheet() {
        NDColorDialog().show(this) { // Build and show
            style(getSheetStyle())
            title("Background color")
            defaultView(ColorView.TEMPLATE) // Set the default view when the sheet is visible
            // disableSwitchColorView() Disable switching between template and custom color view
            onPositive { color ->
                // Use Color
            }
        }
    }


    private fun showColorSheetTemplate() {
        NDColorDialog().show(this) { // Build and show
            style(getSheetStyle())
            title("Background color")
            defaultView(ColorView.TEMPLATE) // Set the default view when the sheet is visible
            disableSwitchColorView()
            onPositive { color ->
                // Use Color
            }
        }
    }

    private fun showColorSheetCustom() {
        NDColorDialog().show(this) { // Build and show
            style(getSheetStyle())
            title("Background color")
            defaultView(ColorView.CUSTOM) // Set the default view when the sheet is visible
            disableSwitchColorView()
            onPositive { color ->
                // Use Color
            }
        }
    }

    private fun showInputSheetShort() {

        NDInputDialog().show(this) {
            style(getSheetStyle())
            title("Survey about this library.")
            content("We would like to ask you some questions about this library. We put a lot of effort into it and hope to make it easy to use.")
            with(InputRadioButtons("") {
                required()
                drawable(R.drawable.ic_telegram)
                label("How did you find this library?")
                options(mutableListOf("Google", "GitHub", "Twitter"))
                selected(1)
                changeListener { value -> showToast("RadioButton change", value.toString()) }
                resultListener { value -> showToast("RadioButton result", value.toString()) }
            })
            with(InputCheckBox("use") { // Read value later by index or custom key from bundle
                label("Usage")
                text("I use this library in my awesome app.")
                changeListener { value -> showToast("CheckBox change", value.toString()) }
                resultListener { value -> showToast("CheckBox result", value.toString()) }
            })

            onNegative { showToast("InputSheet cancelled", "No result") }
            onPositive { result ->
                showToastLong("InputSheet result", result.toString())
                val text = result.getString("0") // Read value of inputs by index
                val check = result.getBoolean("use") // Read value by passed key
            }
        }
    }


    private fun showInputSheetLong() {

        NDInputDialog().show(this) {
            style(getSheetStyle())
            title("Short survey")
            content("We would like to ask you some questions reading your streaming platform usage.")
            with(InputEditText {
                required()
                startIconDrawable(R.drawable.ic_mail)
                label("Your favorite TV-Show")
                hint("The Mandalorian, ...")
                inputType(InputType.TYPE_CLASS_TEXT)
                changeListener { value -> showToast("Text change", value) }
                resultListener { value -> showToast("Text result", value) }
            })
            with(InputCheckBox("binge_watching") { // Read value later by index or custom key from bundle
                label("Binge Watching")
                text("I'm regularly binge watching shows on Netflix.")
                defaultValue(false)
                changeListener { value -> showToast("CheckBox change", value.toString()) }
                resultListener { value -> showToast("CheckBox result", value.toString()) }
            })

            with(InputSpinner {
                required()
                drawable(R.drawable.ic_telegram)
                label("Favorite show in the list")
                noSelectionText("Select Show")
                options(
                    mutableListOf(
                        "Westworld",
                        "Fringe",
                        "The Expanse",
                        "Rick and Morty",
                        "Attack on Titan",
                        "Death Note",
                        "Parasite",
                        "Jujutsu Kaisen"
                    )
                )
                changeListener { value -> showToast("Spinner change", value.toString()) }
                resultListener { value -> showToast("Spinner result", value.toString()) }
            })

            with(InputRadioButtons("") {
                required()
                drawable(R.drawable.ic_telegram)
                label("Streaming service of your choice")
                options(mutableListOf("Netflix", "Amazon", "Other"))
                selected(0)
                changeListener { value -> showToast("RadioButton change", value.toString()) }
                resultListener { value -> showToast("RadioButton result", value.toString()) }
            })
            onNegative { showToast("InputSheet cancelled", "No result") }
            onPositive { result ->
                showToastLong("InputSheet result", result.toString())
                val text = result.getString("0") // Read value of inputs by index
                val check = result.getBoolean("binge_watching") // Read value by passed key
            }
        }
    }

    private fun showInputSheetPassword() {

        var password1: String? = "1"
        var password2: String?
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val errorText =
            "Must contain at least one digit, lower case letter, upper case letter, special character, no whitespace and at least 8 characters."

        NDInputDialog().show(this) {
            style(getSheetStyle())
            title("Choose a password")
            content("Make sure the password is safe enough and is not used for any other account.")
            withIconButton(IconButton(R.drawable.ic_help)) {
                showToast(
                    "IconButton",
                    "Help clicked..."
                )
            }
            with(InputEditText {
                required()
                hint("Password")
                startIconDrawable(R.drawable.ic_lock)
                endIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE)
                passwordVisible(false /* Don't display password in clear text. */)
                validationListener { value ->
                    password1 = value
                    val pattern = Pattern.compile(regex)
                    val matcher = pattern.matcher(value)
                    val valid = matcher.find()
                    if (valid) Validation.success()
                    else Validation.failed(errorText)
                }
                changeListener { value -> showToast("Text change", value) }
                resultListener { value -> showToast("Text result", value) }
            })
            with(InputEditText {
                required()
                startIconDrawable(R.drawable.ic_lock)
                hint("Repeat password")
                endIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE)
                passwordVisible(false)
                validationListener { value ->
                    password2 = value
                    if (password1 != password2) {
                        Validation.failed("Passwords don't match.")
                    } else Validation.success()
                }
                resultListener { value -> showToast("Text result", value) }
            })
            onNegative("cancel") { showToast("InputSheet cancelled", "No result") }
            onPositive("register") {
                showToastLong("InputSheet result", "Passwords matched.")
            }
        }
    }

    private fun showOptionsSheetList() {

        val sheet = NDOptionsDialog().build(this) { // Build and show
            style(getSheetStyle())
            displayMode(DisplayMode.LIST)
            cornerFamily(CornerFamily.CUT)
            titleColor(Color.YELLOW)
            cornerRadius(16f)
            title("Note from 27th dec") // Set the title of the sheet
            if (Random.nextBoolean()) {
                displayButtons() // For single choice, no buttons are displayed, except you enforce to display them
            }
            with( // Add options
                Option(R.drawable.ic_send, "Send"),
                Option(R.drawable.ic_invite, "Invite"), // String or StringRes
                Option(R.drawable.ic_archive, "Archive").disabled(false)
            )
            onPositive { index, option ->
                // Selected index / option
                showOptionsSheetGridSmall(DisplayMode.GRID_HORIZONTAL)
            }
            onDismiss {
                bv.exampleRecyclerView.visibility = View.VISIBLE
            }
        }

        sheet.show() // Show later
    }

    private fun showOptionsSheetGridSmall(displayMode: DisplayMode) {

        val sheet = NDOptionsDialog().build(this) { // Build only
            style(getSheetStyle())
            displayMode(displayMode) // Display mode for list/grid + scroll into height or width
            title("What's your favorite fruit?")
            displayMode(DisplayMode.GRID_HORIZONTAL) // Display mode for list/grid + scroll into height or width
            with( // Add options
                Option(R.drawable.ic_fruit_cherries, "Cherries"),
                Option(R.drawable.ic_fruit_watermelon, "Watermelon"),
//                Option(R.drawable.ic_fruit_grapes, "Grapes")
            )
            onPositive { index, option ->
                // All selected indices / options
                showOptionsSheetGridMiddle(DisplayMode.GRID_HORIZONTAL)
            }
        }

        sheet.show() // Show later
    }

    private fun showOptionsSheetGridMiddle(displayMode: DisplayMode) {

        NDOptionsDialog().show(this) { // Build and show
            style(getSheetStyle())
            displayMode(displayMode) // Display mode for list/grid + scroll into height or width
            title("What would you like to eat daily?")
            multipleChoices() // Apply to make it multiple choices
            minChoices(3) // Set minimum choices
            maxChoices(4) // Set maximum choices
            displayMultipleChoicesInfo() // Show info view for selection
            displayButtons()
            with(
                Option(R.drawable.ic_apple, "Apple"),
                Option(
                    R.drawable.ic_fruit_cherries,
                    "Cherries"
                ).disable(), // An option can be disabled
                Option(R.drawable.ic_food_pasta, "Pasta"),
                Option(R.drawable.ic_fruit_watermelon, "Watermelon"),
                Option(
                    R.drawable.ic_fruit_grapes,
                    "Grapes"
                ).select(), // An option can be preselected
                Option(R.drawable.ic_food_burger, "Burger"),
                Option(R.drawable.ic_fruit_pineapple, "Pineapple"),
                Option(R.drawable.ic_food_croissant, "Croissant")
            )
            onPositiveMultiple { selectedIndices: MutableList<Int>, selectedOptions: MutableList<Option> ->
                // All selected indices / options

                showOptionsSheetGridLarge(DisplayMode.GRID_HORIZONTAL)
            }
        }
    }

    private fun showOptionsSheetGridLarge(displayMode: DisplayMode) {

        NDOptionsDialog().show(this) { // Build and show
            style(getSheetStyle())
            displayMode(displayMode) // Display mode for list/grid + scroll into height or width
            title("What would you like to eat daily?")
            with(
                Option(R.drawable.ic_food_burger, "Burger"),
                Option(R.drawable.ic_fruit_pineapple, "Pineapple"),
                Option(R.drawable.ic_food_croissant, "Croissant"),
                Option(R.drawable.ic_apple, "Apple"),
                Option(R.drawable.ic_fruit_cherries, "Cherries").disable(), // An option can be disabled
                Option(R.drawable.ic_food_pasta, "Pasta"),
                Option(R.drawable.ic_fruit_watermelon, "Watermelon"),
                Option(R.drawable.ic_fruit_grapes, "Grapes"), // An option can be preselected
                Option(R.drawable.ic_food_burger, "Burger"),
                Option(R.drawable.ic_fruit_pineapple, "Pineapple"),
                Option(R.drawable.ic_food_croissant, "Croissant"),

                Option(R.drawable.ic_food_burger, "Burger"),
                Option(R.drawable.ic_fruit_pineapple, "Pineapple"),
                Option(R.drawable.ic_food_croissant, "Croissant"),
                Option(R.drawable.ic_apple, "Apple"),
                Option(R.drawable.ic_fruit_cherries, "Cherries").disable(), // An option can be disabled
                Option(R.drawable.ic_food_pasta, "Pasta"),
                Option(R.drawable.ic_fruit_watermelon, "Watermelon"),
                Option(R.drawable.ic_fruit_grapes, "Grapes"), // An option can be preselected
                Option(R.drawable.ic_food_burger, "Burger"),
                Option(R.drawable.ic_fruit_pineapple, "Pineapple"),
                Option(R.drawable.ic_food_croissant, "Croissant"),

                Option(R.drawable.ic_food_burger, "Burger"),
                Option(R.drawable.ic_fruit_pineapple, "Pineapple"),
                Option(R.drawable.ic_food_croissant, "Croissant"),
                Option(R.drawable.ic_apple, "Apple"),
                Option(R.drawable.ic_fruit_cherries, "Cherries").disable(), // An option can be disabled
                Option(R.drawable.ic_food_pasta, "Pasta"),
                Option(R.drawable.ic_fruit_watermelon, "Watermelon"),
                Option(R.drawable.ic_fruit_grapes, "Grapes"), // An option can be preselected
                Option(R.drawable.ic_food_burger, "Burger"),
                Option(R.drawable.ic_fruit_pineapple, "Pineapple"),
                Option(R.drawable.ic_food_croissant, "Croissant")
            )
            onPositive { index, option ->
                // All selected indices / options
            }
        }
    }

    private fun showClockTimeSheet() {

        NDClockTimeDialog().show(this) {
            style(getSheetStyle())
            title("Wake-up time")
            format24Hours(Random.nextBoolean()) // By default 24-hours format is enabled
            currentTime(
                TimeUnit.HOURS.toMillis(5).plus(TimeUnit.MINUTES.toMillis(30))
            ) // Set current time
            onPositive { milliseconds, hours, minutes ->
                // Use selected clock time in millis
                showToastLong("Clock time", "$hours - $minutes ($milliseconds)")
            }
        }
    }

    private fun showTimeSheet(timeFormat: TimeFormat) {

        NDTimeDialog().show(this) {
            style(getSheetStyle())
            title("Snooze time")
            format(timeFormat)
            currentTime(
                TimeUnit.HOURS.toSeconds(0)
                .plus(TimeUnit.MINUTES.toSeconds(50).plus(TimeUnit.SECONDS.toSeconds(12))))
//            currentTime(0) // Set current time in seconds
//            minTime(1) // Set minimum time in seconds
//            maxTime(600) // Set maximum time in seconds
            onPositive { timeInSec ->
                // Use selected time in millis
                Log.d(TAG, "timeInSec: $timeInSec")
                showToastLong("Time", "Seconds: $timeInSec")
            }
        }
    }

    private fun showCalcSheet() {
        NDCalcDialog().show(this){
            style(getSheetStyle())
            title("Calculator") // Set the title of the sheet
            defaultAmount(2452342343.856)
            onPositive("Save") { amount ->
                showToastLong(
                    "Amount",
                    " $amount"
                )
            }
        }
    }

    private fun showInfoSheetLottie() {
        NDInfoDialog().show(this) {
            style(getSheetStyle())
            displayCloseButton(false)
            cornerFamily(CornerFamily.CUT)
            cornerRadius(16f)
            topStyle(TopStyle.values().random())
            withCoverLottieAnimation(LottieAnimation {
                ratio(Ratio(3, 2))
                setupAnimation {
                    // Lottie animation setup
                    setAnimation(R.raw.anim_lottie_business_team)
                    // .. more settings
                }
                setupImageView {
                    // Manipulate ImageView
//                    setBackgroundColor(ContextCompat.getColor(context, R.color.sheetDividerColor))
                }
            })
            withIconButton(IconButton(R.drawable.ic_help)) { cancelCoverAnimation() }
            title("Team Collaboration")
            content("In the world of software projects, it is inevitable that we will find ourselves working in a team to deliver a project.")
            onNegative("Learn how") { }
            onPositive("Great")
        }
    }

    private fun showInfoSheetTopStyleMixed() {
        NDInfoDialog().show(this) {
            displayButtons(false)
            style(getSheetStyle())
            cornerFamily(CornerFamily.CUT)
            topStyle(TopStyle.MIXED)
            withCoverImage(Image("https://cdn.wallpapersafari.com/11/17/LjhbqX.jpg") {
                setupRequest {
                    // For placeholder, error, fallback drawable and other image loading configs
                    crossfade(300)
                }
                setupImageView {
                    // Setup ImageView
                }
            })
            withIconButton(IconButton(R.drawable.ic_help)) { /* Will not automatically dismiss the sheet. */ }
            title("Attack on Titan")
            content("It is set in a world where humanity lives inside cities surrounded by enormous walls that protect them from gigantic man-eating humanoids referred to as Titans; the story follows Eren Yeager, who vows to exterminate the Titans after a Titan brings about the destruction of his hometown and the death of his mother.")
        }
    }

    private fun showInfoSheetTopStyleBottom() {
        NDInfoDialog().show(this) {
            style(getSheetStyle())
            displayCloseButton(false)
            cornerFamily(CornerFamily.CUT)
            cornerRadius(24f)
            topStyle(TopStyle.BELOW_COVER)
            withCoverImage(Image("https://img4.goodfon.com/wallpaper/nbig/7/47/westworld-anthony-hopkins-robert-ford-actor-show-faces.jpg") {
                setupRequest {
                    // Image request / loading setup
                    // fallback(R.drawable.ic_help)
                }
                setupImageView {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    // Manipulate ImageView
                }
            })
            withIconButton(IconButton(R.drawable.ic_help)) { }
            title("Dr. Robert Ford")
            content("Dreams mean everything. They’re the stories we tell ourselves of what could be, who we could become.\"")
            onNegative("Disagree")
            onPositive("Agree")
        }
    }

    private fun showInfoSheetTopStyleTop() {
        NDInfoDialog().show(this) {
            style(getSheetStyle())
            cornerFamily(CornerFamily.CUT)
            topStyle(TopStyle.ABOVE_COVER)
            withCoverImage(Image("https://images.hdqwalls.com/download/interstellar-gargantua-u4-1440x900.jpg"))
            withIconButton(IconButton(R.drawable.ic_github)) { /* e. g. open website. */ }
            withIconButton(IconButton(R.drawable.ic_mail)) { /* Will not automatically dismiss the sheet. */ }
            title("Interstellar")
            content("“We used to look up at the sky and wonder at our place in the stars, now we just look down and worry about our place in the dirt.” — Cooper")
            onNegative("")
            onPositive("Ok")
        }
    }

    private fun showInfoSheet() {
        NDInfoDialog().show(this, width = null /* Use custom view width (e.g. for landscape mode or tablet). */ ) {
            style(getSheetStyle())
            withIconButton(IconButton(R.drawable.ic_github)) { /* e. g. open website. */ }
            title("Did you read the README?")
            content("It will help you to setup beautiful sheets in your project.")
            onNegative(
                "Not yet",
                R.drawable.ic_github
            ) { /* Set listener when negative button is clicked. */
                showToast("onNegative", "")
            }
            onPositive("Yes") {
                showToast("onPositive", "")
            }
            positiveButtonStyle(ButtonStyle.NORMAL)
            negativeButtonStyle(ButtonStyle.OUTLINED)
            drawable(R.drawable.ic_github)
            drawableColor(R.color.teal_700)
        }

    }

    private fun showCalendarSheetWeek1() {

        NDCalendarDialog().show(this) { // Build and show
            style(getSheetStyle())
            title("When do you want to take holidays?") // Set the title of the sheet
            rangeYears(50)
            selectionMode(SelectionMode.DATE)
            calendarMode(CalendarMode.WEEK_1)
            onPositive { dateStart, dateEnd -> // dateEnd is only not null if the selection is a range
                dateEnd?.let {
                    showToastLong(
                        "CalendarSheet result range",
                        "${dateStart.timeInMillis.toFormattedDate()} - ${it.timeInMillis.toFormattedDate()}"
                    )
                } ?: kotlin.run {
                    showToastLong(
                        "CalendarSheet result date",
                        dateStart.timeInMillis.toFormattedDate()
                    )
                }
            }
        }
    }

    private fun showCalendarSheetWeek2() {

        NDCalendarDialog().show(this) { // Build and show
            style(getSheetStyle())
            title("When do you want to take holidays?") // Set the title of the sheet
            selectionMode(SelectionMode.RANGE)
            calendarMode(CalendarMode.WEEK_2)
//            disableTimeline(TimeLine.FUTURE)
            onPositive { dateStart, dateEnd -> // dateEnd is only not null if the selection is a range
                dateEnd?.let {
                    showToastLong(
                        "CalendarSheet result range",
                        "${dateStart.timeInMillis.toFormattedDate()} - ${it.timeInMillis.toFormattedDate()}"
                    )
                } ?: kotlin.run {
                    showToastLong(
                        "CalendarSheet result date",
                        dateStart.timeInMillis.toFormattedDate()
                    )
                }
            }
        }
    }

    private fun showCalendarSheetWeek3() {

        NDCalendarDialog().show(this) { // Build and show
            style(getSheetStyle())
            title("When do you want to take holidays?") // Set the title of the sheet
            selectionMode(SelectionMode.RANGE)
            calendarMode(CalendarMode.WEEK_3)
            disableTimeline(TimeLine.FUTURE)
            onPositive { dateStart, dateEnd -> // dateEnd is only not null if the selection is a range
                dateEnd?.let {
                    showToastLong(
                        "CalendarSheet result range",
                        "${dateStart.timeInMillis.toFormattedDate()} - ${it.timeInMillis.toFormattedDate()}"
                    )
                } ?: kotlin.run {
                    showToastLong(
                        "CalendarSheet result date",
                        dateStart.timeInMillis.toFormattedDate()
                    )
                }
            }
        }
    }

    private fun showCalendarSheet() {

        NDCalendarDialog().show(this) { // Build and show
            style(getSheetStyle())
            title("When do you want to take holidays?") // Set the title of the sheet
            rangeYears(50)
            selectionMode(SelectionMode.RANGE)
            calendarMode(CalendarMode.MONTH)
//            hideToolbar()
//            disableTimeline(TimeLine.PAST)
            disable(
                Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    .apply { add(Calendar.DAY_OF_MONTH, 2) })
            disable(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 10) })
            disable(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 20) })
            repeat(5) {
                disable(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 2 + it) })
            }
            onPositive { dateStart, dateEnd -> // dateEnd is only not null if the selection is a range
                dateEnd?.let {
                    showToastLong(
                        "CalendarSheet result range",
                        "${dateStart.timeInMillis.toFormattedDate()} - ${it.timeInMillis.toFormattedDate()}"
                    )
                } ?: kotlin.run {
                    showToastLong(
                        "CalendarSheet result date",
                        dateStart.timeInMillis.toFormattedDate()
                    )
                }
            }
        }
    }

    private fun showToast(id: String, value: String?) {
        Toast.makeText(
            this@MainActivity,
            id.plus(": $value"),
            Toast.LENGTH_SHORT
        ).apply { show() }
    }

    private fun showToastLong(id: String, value: String?) {
        Toast.makeText(
            this@MainActivity,
            id.plus(": $value"),
            Toast.LENGTH_LONG
        ).apply { show() }
    }

    private fun applyThemeMode() {
        when (themeMode) {
            ThemeMode.NIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            ThemeMode.DAY_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun loadPreferences() {
        val sheetStyleName = getPrefString(Preference.SHEET_STYLE)
        dialogStyle =
            sheetStyleName?.takeIf { DialogStyle.values().any { it.name == sheetStyleName } }
                ?.let { DialogStyle.valueOf(it) }

        val themeModeName = getPrefString(Preference.THEME_MODE)

        themeMode =
            themeModeName?.takeIf { ThemeMode.values().any { it.name == themeModeName } }
                ?.let { ThemeMode.valueOf(it) }
    }

    enum class ThemeMode {
        AUTO,
        NIGHT_MODE,
        DAY_MODE
    }

    enum class Preference(val key: String) {
        THEME_MODE("pref_theme_mode"),
        SHEET_STYLE("pref_sheet_style"),
    }

    private fun setPrefString(pref: Preference, value: String?) =
        prefs.edit().putString(pref.key, value).apply()

    private fun getPrefString(preference: Preference): String? =
        prefs.getString(preference.key, null)
}