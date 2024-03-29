# NDDialogs



<img src="art/showcase.png" alt="sheets Library">

## Table of Contents

- [Get started](#get-started)
  - [Info Sheet](#info)
  - [Options Sheet](#options)
  - [Clock Time Sheet](#clock-time)
  - [Time Sheet](#time)
  - [Input Sheet](#input)
  - [Calendar Sheet](#calendar)
  - [Color Sheet](#color)
  - [Custom Sheet](#custom)
  - [Lottie](#lottie)
  - [Appearance](#appearance)
- [Misc](#misc)
  - [Credits](#credits)

# Get started

You have to use the `core` module as it is the foundation of any sheet.

In your top-level `build.gradle` file:

```gradle
repositories {
  ...
  maven { url "https://jitpack.io" }
}
```

In your app `build.gradle` file:

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:core:<latest-version>'
}
```

**Base functions** <br/>
Following functions can be called from any type of sheet.

| Function              | Action                                                                           |
| --------------------- | -------------------------------------------------------------------------------- |
| style()               | Display as dialog or bottom-sheet.                                               |
| title()               | Set the title text.                                                              |
| titleColor()          | Set the title text color.                                                        |
| titleColorRes()       | Set the title text color by a resource.                                          |
| withCoverImage()      | Add a cover image.                                                               |
| topStyle()            | Specify the style of the cover image and top bar.                                |
| positiveButtonStyle() | Define the style of the positive button (Text, Filled, Outlined).                |
| negativeButtonStyle() | Define the style of the negative button (Text, Filled, Outlined).                |
| withIconButton()      | Add up to 3 icon buttons to the top bar.                                         |
| closeIconButton()     | Set a custom close icon button.                                                  |
| displayHandle()       | Display the handle.                                                              |
| displayCloseButton()  | Display close icon button.                                                       |
| displayToolbar()      | Display toolbar. (Close icon button, title, divider and icon buttons)            |
| peekHeight()          | Set the peek height. (Only bottom-sheet)                                         |
| cornerRadius()        | Set corner radius.                                                               |
| cornerFamily()        | Set corner family. (Cut or rounded)                                              |
| borderWidth()         | Set the border width.                                                            |
| borderColor()         | Set the border color.                                                            |
| cancelableOutside()   | Make sheet cancelable outside of the dialog view.                                |
| onNegative()          | Set the negative button text and listener.                                       |
| onPositive()          | Set the positive button text and listener.                                       |
| onDismiss()           | Set a listener that is invoked when the sheet is dismissed.                      |
| onCancel()            | Set a listener that is invoked when the sheet is cancelled (only if cancelable). |
| onClose()             | Set a listener that is invoked when the sheet is closed.                         |
| show()                | show the sheet.                                                                  |

Each sheet has an extension function called `build` and `show`.<br/>

Use `build` to build a sheet and display it later.

    val sheet = NDInfoDialog().build(context) {
      // build sheet
    }

    sheet.show() // Show sheet when ready

Use `show` if you want to build and then immediately display it.

    NDInfoDialog().show(context) {
      // build sheet
    } // Show sheet

## Info

The `Info` Sheet lets you display information or warning.

<details open>
<br/>
<br/>
<summary>Showcase as Dialog</summary>

<img src="art/InfoSheet Dialog.png" width="80%" alt="Sheets InfoSheet Dialog"><br/>
<img src="art/InfoSheet Dialog Cover TopStyle Top.png" width="80%" alt="Sheets InfoSheet Dialog"><br/>
<img src="art/InfoSheet Dialog Cover TopStyle Bottom.png" width="80%" alt="Sheets InfoSheet Dialog"><br/>
<img src="art/InfoSheet Dialog Cover TopStyle Mixed.png" width="80%" alt="Sheets InfoSheet Dialog"><br/>

</details>
<br/><br/>

<details>
<summary>Showcase as BottomSheet</summary>

<br/>
<br/>
<img src="art/InfoSheet BottomSheet.png" width="80%" alt="Sheets InfoSheet"><br/>
<img src="art/InfoSheet BottomSheet Cover TopStyle Top.png" width="80%" alt="Sheets InfoSheet BottomSheet"><br/>
<img src="art/InfoSheet BottomSheet Cover TopStyle Bottom.png" width="80%" alt="Sheets InfoSheet BottomSheet"><br/>
<img src="art/InfoSheet BottomSheet Cover TopStyle Mixed.png" width="80%" alt="Sheets InfoSheet BottomSheet"><br/>
</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialog:info:<latest-version>'
}
```

### Usage

For the default info sheet use it as following:

    NDInfoDialog().show(context) {
      title("Do you want to install Awake?")
      content("Awake is a beautiful alarm app with morning challenges, advanced alarm management and more.")
      onNegative("No") {
        // Handle event
      }
      onPositive("Install") {
        // Handle event
      }
    }

| Function        | Action              |
| --------------- | ------------------- |
| content()       | Set content text.   |
| drawable()      | Set drawable.       |
| drawableColor() | Set drawable color. |

## Options

The `Options` Sheet lets you display a grid or list of options.

<details open>
<br/><br/>
<summary>Showcase as Dialog</summary>

<img src="art/OptionsSheet Dialog Grid Middle.png" width="80%" alt="Sheets OptionsSheet Dialog">
</details>
<br/><br/>

<details>
<br/><br/>
<summary>Showcase as BottomSheet</summary>

<img src="art/OptionsSheet BottomSheet Grid Middle.png" width="80%" alt="Sheets OptionsSheet BottomSheet">
</details>

<br/>
<details>
<br/><br/>
<summary>Showcase some variants as Dialogs</summary>

<img src="art/OptionsSheet Dialog List.png" width="80%" alt="Sheets OptionsSheet Dialog"><br/>
<img src="art/OptionsSheet Dialog Grid Small.png" width="80%" alt="Sheets OptionsSheet" Dialog><br/>
<img src="art/OptionsSheet Dialog Grid Large Horizontal.png" width="80%" alt="Sheets OptionsSheet" Dialog><br/>

</details>
<br/><br/>

<details>
<br/><br/>
<summary>Showcase some variants as BottomSheets</summary>

<img src="art/OptionsSheet BottomSheet List.png" width="80%" alt="Sheets OptionsSheet BottomSheet"><br/>
<img src="art/OptionsSheet BottomSheet Grid Small.png" width="80%" alt="Sheets OptionsSheet" BottomSheet><br/>
<img src="art/OptionsSheet BottomSheet Grid Large Horizontal.png" width="80%" alt="Sheets OptionsSheet" BottomSheet><br/>

</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:info:<latest-version>'
}
```

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:options:<latest-version>'
}
```

### Usage

For the default options sheet use it as following:

    NDOptionsDialog().show(context) {
      title("Text message")
      with(
        Option(R.drawable.ic_copy, "Copy"),
        Option(R.drawable.ic_translate, "Translate"),
        Option(R.drawable.ic_paste, "Paste")
      )
      onPositive { index: Int, option: Option ->
        // Handle selected option
      }
    }

| Function                     | Action                                                                                 |
| ---------------------------- | -------------------------------------------------------------------------------------- |
| multipleChoices()            | Allow multiple choices content.                                                        |
| displayMultipleChoicesInfo() | Display info of the multiple choices.                                                  |
| maxChoicesStrictLimit()      | Specify that the max choices is strict and more choices can't be selected temporarily. |
| minChoices()                 | Set the minimum amount of choices.                                                     |
| maxChoices()                 | Set the maximum amount of choices.                                                     |
| onPositiveMultiple()         | Set listener for multiple choices.                                                     |
| displayButtons()             | Display buttons and require a positive button click for selection.                     |
| displayMode()                | Display options in a list or a vertical/ horizontal growing scrollable grid.           |

**Option**

| Function   | Action               |
| ---------- | -------------------- |
| selected() | Preselect an option. |
| disable()  | Disable an option.   |

**Note**: Preselected options automatically increase the current selection while disabled options decrease the maximum amount of choices.

## Clock Time

The `Clock Time` Sheet lets you quickly pick a time.

<details>
<br/><br/>
<summary>Showcase as Dialog</summary>

<img src="art/ClockTimeSheet Dialog.png" width="80%" alt="Sheets OptionsSheet Dialog">
</details>
<br/><br/>

<details open>
<br/><br/>
<summary>Showcase as BottomSheet</summary>

<img src="art/ClockTimeSheet BottomSheet.png" width="80%" alt="Sheets OptionsSheet BottomSheet">
</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:time-clock:<latest-version>'
}
```

### Usage

For the default clock time sheet, in 24-hours format, use it as following:

    NDClockTimeDialog().show(context) {
      title("Wake-up time")
      onPositive { clockTimeInMillis: Long ->
        // Handle selected time
      }
    }

| Function        | Action                                |
| --------------- | ------------------------------------- |
| format24Hours() | Use 24-hours or 12-hours format.      |
| currentTime()   | Set the current time in milliseconds. |

## Time

The `Time` Sheet lets you pick a duration time in a specific format.

<details open>
<br/><br/>
<summary>Showcase as Dialog</summary>

<img src="art/TimeSheet Dialog.png" width="80%" alt="Sheets TimeSheet Dialog">
</details>
<br/><br/>

<details>
<br/><br/>
<summary>Showcase as BottomSheet</summary>

<img src="art/TimeSheet BottomSheet.png" width="80%" alt="Sheets TimeSheet BottomSheet">
</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:time:<latest-version>'
}
```

### Usage

For the default time sheet use it as following:

    NDTimeDialog().show(context) {
      title("Snooze time")
      onPositive { durationTimeInMillis: Long ->
        // Handle selected time
      }
    }

| Function      | Action                                          |
| ------------- | ----------------------------------------------- |
| format()      | Select the time format. (hh:mm:ss, mm:ss, ...) |
| currentTime() | Set the current time in seconds.                |
| minTime()     | Set the minimum time.                           |
| maxTime()`    | Set the maximum time.                          |

## Input

The `Input` Sheet lets you display a form consisting of various inputs.

<details>
<br/><br/>
<summary>Showcase as Dialog</summary>

<img src="art/InputSheet Dialog Short.png" width="80%" alt="Sheets InputSheet Dialog">
</details>
<br/><br/>

<details open>
<br/><br/>
<summary>Showcase as BottomSheet</summary>

<img src="art/InputSheet BottomSheet Short.png" width="80%" alt="Sheets InputSheet BottomSheet">
</details>

<br/>
<details>
<br/><br/>
<summary>Showcase some variants as Dialogs</summary>

<img src="art/InputSheet Dialog Long.png" width="80%" alt="Sheets InputSheet Dialog"><br/>

</details>
<br/><br/>

<details>
<br/><br/>
<summary>Showcase some variants as BottomSheets</summary>

<img src="art/InputSheet BottomSheet Long.png" width="80%" alt="Sheets InputSheet BottomSheet">
</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:input:<latest-version>'
}
```

### Usage

For the default input sheet use it as following:

    NDInputDialog()).show(context) {
        title("Short survey")
        content("We would like to ask you some questions reading your streaming platform usage.")
      with(InputEditText {
        required())
        label("Your favorite TV-Show")
        hint("The Mandalorian, ...")
        validationListener { value -> } // Add custom validation logic
        changeListener { value -> } // Input value changed
        resultListener { value -> } // Input value changed when form finished
      })
      with(InputCheckBox("binge_watching") { // Read value later by index or custom key from bundle
        label("Binge Watching")
        text("I'm regularly binge watching shows on Netflix.")
        // ... more options
      })
      with(InputRadioButtons() {
        required()
        label("Streaming service of your choice")
        options(mutableListOf("Netflix", "Amazon", "Other"))
      })
      // ... more input options
      onNegative { showToast("InputSheet cancelled", "No result") }
      onPositive { result ->
          showToastLong("InputSheet result", result.toString())
          val text = result.getString("0") // Read value of inputs by index
          val check = result.getBoolean("binge_watching") // Read value by passed key
      }
    }

| Function  | Action                                        |
| --------- | --------------------------------------------- |
| with()    | Add an input. (see input options)             |
| content() | Set content text. (e. g. to explain a survey) |

**Input options:**

- `InputEditText`
- `InputCheckBox`
- `InputRadioButtons`
- `InputSpinner`

**Input**<br/>

| Function         | Action                           |
| ---------------- | -------------------------------- |
| label()          | Set the label text.              |
| drawable()       | Set the drawable.                |
| required()       | Mark input as required.          |
| changeListener() | Set listener to observe changes. |
| resultListener() | Set listener for final value.    |

**InputEditText**<br/>

| Function             | Action                                            |
| -------------------- | ------------------------------------------------- |
| hint()               | Set the hint text.                                |
| defaultValue()       | Set default text.                                 |
| inputType()          | Set the `android.text.InputType`'s.               |
| inputFilter()        | Set the `android.text.inputFilter`                |
| maxLines()           | Set the max amount of lines.                      |
| endIconMode()        | Set TextInputLayout.EndIconMode.                  |
| endIconActivated()   | Set the EndIcon activated.                        |
| passwordVisible()    | Make the password initially visible or invisible. |
| validationListener() | Validate the text input with your own logic.      |

**InputCheckBox** <br/>

| Function       | Action             |
| -------------- | ------------------ |
| text()         | Set the text.      |
| defaultValue() | Set default value. |

**InputRadioButtons** <br/>

| Function   | Action                             |
| ---------- | ---------------------------------- |
| options()  | Set a list of RadioButton options. |
| selected() | Set a selected index.              |

**InputSpinner** <br/>

| Function          | Action                                                    |
| ----------------- | --------------------------------------------------------- |
| noSelectionText() | Set the text that is displayed, when nothing is selected. |
| options()         | Set a list of options.                                    |
| selected()        | Set a selected index.                                     |

## Calendar

The `Calendar` Sheet lets you pick a date or date range. This type was build using the library [CalendarView](https://github.com/kizitonwose/CalendarView).

<details open>
<br/><br/>
<summary>Showcase as Dialog</summary>

<img src="art/CalendarSheet Dialog Period.png" width="80%" alt="Sheets CalendarSheet Dialog">
</details>
<br/><br/>

<details>
<br/><br/>
<summary>Showcase as BottomSheet</summary>

<img src="art/CalendarSheet BottomSheet Period.png" width="80%" alt="Sheets CalendarSheet BottomSheet">
</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:calendar:<latest-version>'
}
```

### Usage

For the default time sheet use it as following:

    NDCalendarDialog().show(this) { // Build and show
      title("What's your date of birth?") // Set the title of the sheet
      onPositive { dateStart, dateEnd ->
        // Handle date or range
      }

| Function          | Action                                                           |
| ----------------- | ---------------------------------------------------------------- |
| selectionMode()   | Choose the selection mode (date or range).                       |
| calendarMode()    | Choose the calendar mode (week with various rows or month-view). |
| disableTimeline() |  Disable either past or future dates.                            |
| rangeYears()      | Set the range of years into past and future.                     |
| disable()         | Pass a `Calendar` object to disable various dates for selection. |
| displayButtons()  | Show or hide the buttons view.                                   |

## Color

The `Color` Sheet lets you pick a color. Display the default material colors or specify which colors can be choosen from. You can allow to chose a custom color as well.

<details>
<br/><br/>
<summary>Showcase as Dialog</summary>

<img src="art/ColorSheet Dialog Templates.png" width="80%" alt="Sheets ColorSheet Dialog"><br/>
<img src="art/ColorSheet Dialog Custom.png" width="80%" alt="Sheets ColorSheet Dialog">

</details>
<br/><br/>

<details open>
<br/><br/>
<summary>Showcase as BottomSheet</summary>

<img src="art/ColorSheet BottomSheet Templates.png" width="80%" alt="Sheets ColorSheet BottomSheet"><br/>
<img src="art/ColorSheet BottomSheet Custom.png" width="80%" alt="Sheets ColorSheet BottomSheet">

</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:color:<latest-version>'
}
```

### Usage

For the default color sheet use it as following:

    NDColorDialog().show(context) {
      title("Background color")
      onPositive { color ->
        // Use color
      }
    }

| Function                 | Action                                                           |
| ------------------------ | ---------------------------------------------------------------- |
| defaultView()            | Select the default color view (Colors from templates or custom). |
| disableSwitchColorView() | Disable to switch between color views.                           |
| defaultColor()           | Set default selected color.                                      |
| colors()                 | Pass all colors to be displayed in the color templates view.     |
| disableAlpha()           | Disable alpha colors for custom colors.                          |

## Custom

With just the 'core' module you are able to create your own sheet based on this library. You can use some components and styles within your own custom sheet automatically. By default the buttons and toolbar view with logic is ready to be used by your own implementation.

<details>
<br/><br/>
<summary>Showcase as Dialog</summary>

<img src="art/Custom Sheet Dialog.png" width="80%" alt="Sheets Custom Dialog">
</details>
<br/><br/>

<details open>
<br/><br/>
<summary>Showcase as BottomSheet</summary>

<img src="art/Custom Sheet BottomSheet.png" width="80%" alt="Sheets Custom BottomSheet">
</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:core:<latest-version>'
}
```

### Get started

You can find a custom sheet implementation in the sample module.

1.  Step: Create a class and extend from the class `Sheet`.

    class NDCustomDialog : Sheet() {

2.  Step: Implement the method: `onCreateLayoutView` and pass your custom layout.

    override fun onCreateLayoutView(): View {
    return LayoutInflater.from(activity).inflate(R.layout.sheets_custom, null)
    }

All of the base functionality can be used and on top of that you can extend the logic and behavior as you wish.

### Components

You are free to use the components this library uses for it's sheet types.

- `NDDialogTitle`
- `NDDialogContent`
- `NDDialogDigit`
- `NDDialogNumericalInput`
- `NDDialogDivider`
- `NDDialogButton`
- `NDDialogEdit`
- `NDDialogRecyclerView`
- `NDDialogValue`

## Lottie

The `Lottie` modules gives you the ability to use a [Lottie animations](https://airbnb.design/lottie/) as cover view.

<details open>
<br/>
<br/>
<summary>Showcase as Dialog</summary>

<img src="art/InfoSheet Dialog Cover Lottie Animation.png" width="80%" alt="Sheets InfoSheet">
</details>


<details>
<summary>Showcase as BottomSheet</summary>

<br/>
<br/>
<img src="art/InfoSheet BottomSheet Cover Lottie Animation.png" width="80%" alt="Sheets InfoSheet">
</details>

```gradle
dependencies {
  ...
  implementation 'com.github.NareshDhakecha.NDDialogs:lottie:<latest-version>'
}
```

### Usage

You can use the Lottie animation as a cover for any type of sheet.

    NDInfoDialog().show(this) {
      title("Team Collaboration")
      content("In the world of software projects, it is inevitable...")
      ...
      withCoverLottieAnimation(LottieAnimation {
        setAnimation(R.raw.anim_lottie_business_team)
        ... Setup Lottie animation
      })
      ...
    }

| Function               | Action                |
| ---------------------- | --------------------- |
| playCoverAnimation()   | Play the animation.   |
| resumeCoverAnimation() | Resume the animation. |
| pauseCoverAnimation()  | Pause the animation.  |
| cancelCoverAnimation() | Cancel the animation. |

## Appearance

By default, the library switches to either day or night mode depending on the attr `textColorPrimary`.
By default it uses the activity's colorPrimary. The default `highlightColor` is generated based on the color `sheetPrimaryColor`, or if not available `colorPrimary`.

### Base

You want a different sheet background shape?
Then just override the corner family and radius.

    <item name="ndDialogCornerRadius">12dp</item>
    <item name="ndDialogCornerFamily">cut</item>

Just overwrite the base colors, if you want to achieve a different look of the sheets than your app.

    <item name="ndDialogPrimaryColor">@color/customPrimaryColor</item>
    <item name="ndDialogHighlightColor">@color/customHighlightColor</item>
    <item name="ndDialogBackgroundColor">@color/customBackgroundColor</item>
    <item name="ndDialogDividerColor">@color/customDividerColor</item>
    <item name="ndDialogIconsColor">@color/customIconsColor</item>

You can override the basic style of a sheet. Instead of displaying the toolbar, you can just hide it and display the typical handle.

    <item name="ndDialogDisplayHandle">true</item>
    <item name="ndDialogDisplayToolbar">false</item>
    <item name="ndDialogDisplayCloseButton">false</item>

Change the appearance of the title.

    <item name="ndDialogTitleColor">@color/customTitleTextColor</item>
    <item name="ndDialogTitleFont">@font/font</item>
    <item name="ndDialogTitleLineHeight">@dimen/dimen</item>
    <item name="ndDialogTitleLetterSpacing">value</item>

Change the appearance of the content text.

    <item name="ndDialogContentColor">@color/customContentTextColor</item>
    <item name="ndDialogContentInverseColor">@color/customContentTextInverseColor</item>
    <item name="ndDialogContentFont">@font/font</item>
    <item name="ndDialogContentLineHeight">@dimen/dimen</item>
    <item name="ndDialogContentLetterSpacing">value</item>

Change the appearance of the value texts. (e.g. the time in the TimeSheet & ClockTimeSheet or the selected date & period in the Calendarsheet.)

    <item name="ndDialogValueTextActiveColor">@color/customValueTextColor</item>
    <item name="ndDialogValueFont">@font/font</item>
    <item name="ndDialogValueLineHeight">@dimen/dimen</item>
    <item name="ndDialogValueLetterSpacing">value</item>

Change the appearance of the digit keys on the numerical input.

    <item name="ndDialogDigitColor">@color/customDigitTextColor</item>
    <item name="ndDialogDigitFont">@font/font</item>
    <item name="ndDialogDigitLineHeight">@dimen/dimen</item>
    <item name="ndDialogDigitLetterSpacing">value</item>

### Buttons

Override the appearance of the button text.

    <item name="ndDialogButtonTextFont">@font/font</item>
    <item name="ndDialogButtonTextLetterSpacing">value</item>

Override the general appearance of the buttons (negative and positive button).

    <item name="ndDialogButtonColor">@color/customButtonColor<item>
    <item name="ndDialogButtonTextFont">@font/font<item>
    <item name="ndDialogButtonTextLetterSpacing">value<item>
    <item name="ndDialogButtonCornerRadius">12dp<item>
    <item name="ndDialogButtonCornerFamily">cut<item>
    <item name="ndDialogButtonWidth">match_content/wrap_content<item>

Override the appearance of the negative button.

    <item name="ndDialogNegativeButtonType">text_button/outlined_button/button<item>
    <item name="ndDialogNegativeButtonCornerRadius">12dp<item>
    <item name="ndDialogNegativeButtonCornerFamily">cut<item>

Override the appearance of the positive button.

    <item name="ndDialogPositiveButtonType">text_button/outlined_button/button<item>
    <item name="ndDialogPositiveButtonCornerRadius">12dp<item>
    <item name="ndDialogPositiveButtonCornerFamily">cut<item>

Override the border appearance of the outlined button.

    <item name="ndDialogButtonOutlinedButtonBorderColor">@color/borderColor<item>
    <item name="ndDialogButtonOutlinedButtonBorderWidth">1dp<item>

The corner family and radius is applied to the button shape or in the case of a outlined or text button, to the ripple background shape.

**Fine control**
You can even define the corner family and radius of the negative and positive button for each corner.

    <item name="ndDialogNegativeButtonBottomLeftCornerRadius">4dp<item>
    <item name="ndDialogNegativeButtonBottomLeftCornerFamily">cut<item>
    ...
    <item name="ndDialogPositiveButtonBottomRightCornerRadius">8dp<item>
    <item name="ndDialogPositiveButtonBottomRightCornerFamily">rounded<item>

### Handle

The size and the appearance of the handle can be changed like this:

    <item name="ndDialogHandleCornerRadius">8dp</item>
    <item name="ndDialogHandleCornerFamily">rounded</item>
    <item name="ndDialogHandleFillColor">?sheetPrimaryColor</item>
    <item name="ndDialogHandleBorderColor">?sheetPrimaryColor</item>
    <item name="ndDialogHandleBorderWidth">1dp</item>
    <item name="ndDialogHandleWidth">42dp</item>
    <item name="ndDialogHandleHeight">4dp</item>

### OptionsSheet

Override appearance of selected options.

    <item name="ndDialogOptionSelectedImageColor">@color/customSelectedOptionImageColor</item>
    <item name="ndDialogOptionSelectedTextColor">@color/customSelectedOptionTextColor</item>

Override appearance of disabled options.

    <item name="ndDialogOptionDisabledImageColor">@color/customDisabledOptionImageColor</item>s
    <item name="ndDialogOptionDisabledTextColor">@color/customDisabledOptionImageColor</item>
    <item name="ndDialogOptionDisabledBackgroundColor">@color/customDisabledOptionBackgColor</item>

### InputSheet

Override the appearance of the TextInputLayout (used for the InputEditText).

    <item name="ndDialogTextInputLayoutCornerRadius">12dp</item>
    <item name="ndDialogTextInputLayoutBottomLeftCornerRadius">12dp</item>
    ... and for all other corners
    <item name="ndDialogTextInputLayoutEndIconColor">@color/customEndIconColor</item>
    <item name="ndDialogTextInputLayoutHelperTextColor">@color/customHelperTextColor</item>
    <item name="ndDialogTextInputLayoutBoxStrokeColor">@color/customBoxStrokeColor</item>
    <item name="ndDialogTextInputLayoutHintTextColor">@color/customHintTextColor</item>
    <item name="ndDialogTextInputLayoutBoxStrokeErrorColor">@color/customBoxStrokeErrorColor</item>
    <item name="ndDialogTextInputLayoutErrorTextColor">@color/customErrorTextColor</item>

# Misc
## Credits

- Thanks to [Maximilian Keppeler](https://github.com/maxkeppeler) for the project [Sheets](https://github.com/maxkeppeler/sheets) that is base of this project.
