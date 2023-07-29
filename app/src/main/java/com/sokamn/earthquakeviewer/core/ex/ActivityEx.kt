package com.sokamn.earthquakeviewer.core.ex

import android.app.Activity
import android.graphics.Paint
import android.graphics.Typeface
import android.text.*
import android.text.style.TypefaceSpan
import android.widget.Toast

fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun spanBold(
    activity: Activity,
    first: String,
    secondSpanned: String,
    third: String
): SpannableStringBuilder {
    val completedText = SpannableStringBuilder("$first$secondSpanned$third")
    val bold = Typeface.createFromAsset(activity.applicationContext.assets, "font/bold.ttf")

    completedText.apply {
        setSpan(
            CustomTypefaceSpan("",bold),
            first.length,
            (first + secondSpanned).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return completedText
}

fun spanSemiBold(
    activity: Activity,
    publishedBy: String,
    userName: String
): SpannableString{
    val completedText = SpannableString("$publishedBy$userName")
    val semibold = Typeface.createFromAsset(activity.applicationContext.assets, "font/semibold.ttf")

    completedText.apply {
        setSpan(
            CustomTypefaceSpan("",semibold),
            publishedBy.length,
            (publishedBy + userName).length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return completedText
}

fun spanMedium(
    activity: Activity,
    text: String
): SpannableString{
    val completedText = SpannableString(text)
    val medium = Typeface.createFromAsset(activity.applicationContext.assets, "font/bold.ttf")

    completedText.apply {
        setSpan(
            CustomTypefaceSpan("",medium),
            1,
            completedText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return completedText
}

class CustomTypefaceSpan(family: String?, private val newType: Typeface) : TypefaceSpan(family) {
    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    companion object {
        private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
            val oldStyle: Int
            val old = paint.typeface
            oldStyle = old?.style ?: 0
            val fake = oldStyle and tf.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }
            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }
            paint.typeface = tf
        }
    }
}