

@file:Suppress("unused")

package com.ndsoftwares.dialogs.lottie

import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.ndsoftwares.dialogs.core.ImageSource

/** Convenience alias for this class. */
private typealias LottieAnimationBuilder = LottieAnimation.() -> Unit

/** Convenience alias for lottie's animation view config builder. */
private typealias LottieAnimationViewBuilder = LottieAnimationView.() -> Unit

/** Used to apply image request settings. */
typealias AnimationBuilder = LottieAnimationRequest.() -> Unit

/**
 * A class that holds the animation configurations.
 */
class LottieAnimation private constructor() : ImageSource() {

    private var builder: AnimationBuilder? = null

    internal val lottieAnimationViewBuilder: LottieAnimationViewBuilder
        get() = {
            // By default restart animation infinite times
            repeatMode = LottieDrawable.RESTART
            repeatCount = LottieDrawable.INFINITE

            val request = LottieAnimationRequest().apply { builder?.invoke(this) }
            request.animationResId?.let { setAnimation(it) }
            request.animationName?.let { setAnimation(it) }
            request.animationUrl?.let { setAnimationFromUrl(it) }
            request.animationUrlWithCacheKey?.let { setAnimationFromUrl(it.first, it.second) }
            request.animation?.let { setAnimation(it.first, it.second) }
            request.startFrame?.let { setMinFrame(it) }
            request.endFrame?.let { setMaxFrame(it) }
            request.startFrameName?.let { setMinFrame(it) }
            request.endFrameName?.let { setMaxFrame(it) }
            request.startProgress?.let { setMinProgress(it) }
            request.endProgress?.let { setMaxProgress(it) }
            request.minMaxFrameName?.let { setMinAndMaxFrame(it) }
            request.minMaxFrame?.let { setMinAndMaxFrame(it.first, it.second) }
            request.speed?.let { speed = it }
            request.repeatMode?.let { repeatMode = it }
            request.repeatCount?.let { repeatCount = it }
        }

    constructor(builder: LottieAnimationBuilder) : this() {
        builder()
    }

    /** Setup the LottieAnimationView */
    fun setupAnimation(animationBuilder: AnimationBuilder) {
        this.builder = animationBuilder
    }

}