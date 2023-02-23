

@file:Suppress("unused")

package com.ndsoftwares.dialogs.lottie

import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.ndsoftwares.dialogs.core.AddOnComponent
import com.ndsoftwares.dialogs.core.NDDialog
import com.ndsoftwares.dialogs.core.TopStyle
import com.ndsoftwares.dialogs.core.utils.clipTopCorners

private const val EXCEPTION_MESSAGE_NOT_SETUP = "AnimationView was not setup yet."

/** Set a cover lottie animation. */
fun NDDialog.withCoverLottieAnimation(lottieAnimation: LottieAnimation) {
    val componentLottie: AddOnComponent = {
        useCover() /* Indicate to setup the top bar style. */
        addOnCreateViewListener { binding ->
            val coverContainer = binding.top.cover
            val coverImage = binding.top.coverImage
            val coverParams = coverImage.layoutParams as ViewGroup.LayoutParams
            val coverAnimation = LottieAnimationView(requireContext()).apply {
                setupCoverSource(lottieAnimation,  this)
                layoutParams = coverParams
                lottieAnimation.lottieAnimationViewBuilder.invoke(this)
                if (getTopStyle() != TopStyle.ABOVE_COVER) {
                    clipTopCorners(getActualCornerFamily(), getActualCornerRadius())
                }
            }
            coverContainer.removeView(coverImage)
            coverContainer.addView(coverAnimation)
            setCoverAnimationView(coverAnimation)
            coverAnimation.playAnimation()
        }
    }
    componentLottie()
    addAddOnComponent(componentLottie)
}

/**
 * Play the cover animation.
 * @throws IllegalStateException If animation view was not setup yet.
 */
fun NDDialog.playCoverAnimation() {
    getCoverLottieAnimationView().playAnimation()
}

/**
 * Resume the cover animation.
 * @throws IllegalStateException If animation view was not setup yet.
 */
fun NDDialog.resumeCoverAnimation() {
    getCoverLottieAnimationView().playAnimation()
}

/**
 * Pause the cover animation.
 * @throws IllegalStateException If animation view was not setup yet.
 */
fun NDDialog.pauseCoverAnimation() {
    getCoverLottieAnimationView().pauseAnimation()
}

/**
 * Cancel the cover animation.
 * @throws IllegalStateException If animation view was not setup yet.
 */
fun NDDialog.cancelCoverAnimation() {
    getCoverLottieAnimationView().cancelAnimation()
}

private fun NDDialog.getCoverLottieAnimationView(): LottieAnimationView {
   return getCoverAnimationView<LottieAnimationView>()
        ?: throw IllegalStateException(EXCEPTION_MESSAGE_NOT_SETUP)
}