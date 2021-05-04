package com.always.rateuslib

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.dialog_rate.view.*

class RateDialog(private val context: Context)  {
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var rateDialog: AlertDialog
    private var basePref: BasePreference = BasePreference.Cache(context).builder()

    private var isHandleClose = false
    private var onCloseDialog: () -> Unit = {}

    fun setOnCloseDialogListener(onCloseDialog: () -> Unit) : RateDialog {
        this.onCloseDialog = onCloseDialog
        this.isHandleClose = true
        return this
    }

    fun setupNumbersTimesShowRate(vararg numbers: Int) : RateDialog {
        ShowRateCountManager.setup(numbers.toMutableList())
        return this
    }

    fun create(applicationId: String) : RateDialog {
        if(isShowRate()) {
            alertDialogBuilder = AlertDialog.Builder(context, R.style.DialogThemeCustome)
            val rateInflater = LayoutInflater.from(context).inflate(R.layout.dialog_rate, null)
            alertDialogBuilder.setView(rateInflater)

            rateDialog = getAlertDialog(rateInflater)

            val ratingView = rateInflater.rating
            val btnSubmit = rateInflater.btnSubmit
            val edtFeedBack = rateInflater.edtFeedBack

            ratingView.setOnRatingBarChangeListener { simpleRatingBar, rating, fromUser ->
                if(rating <= 3f) { // Show FeedBack
                    if(!edtFeedBack.isVisible) {
                        edtFeedBack.visibility = View.VISIBLE
                    }
                    rateInflater.btnSubmit.text = context.getString(R.string.submit)
                    rateInflater.btnSubmit.setBackgroundColor(Color.parseColor("#E57373"))
                    rateInflater.btnSubmit.setTextColor(Color.WHITE)
                } else { // Goto Play Store
//                    edtFeedBack.visibility = View.GONE
//                    rateInflater.btnSubmit.text = context.getString(R.string.maybe_later)
//                    rateInflater.btnSubmit.setTextColor(Color.parseColor("#757575"))
//                    rateInflater.btnSubmit.setBackgroundColor(Color.parseColor("#EEEEEE"))
                    rateDialog.dismiss()
                    openStoreLink(applicationId)
                }
            }

            btnSubmit.setOnClickListener {
                if(ratingView.rating <= 3) {
                    if(edtFeedBack.text.toString().trim().isBlank()) {
                        Toast.makeText(context, "Not empty text", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_LONG).show()
                        if(isHandleClose) {
                            onCloseDialog.invoke()
                        } else {
                            if(context is AppCompatActivity) {
                                context.finishAffinity()
                            }
                        }
                    }
                } else {
                    if(context is AppCompatActivity) {
                        context.finishAffinity()
                    }
                }
            }
        }

        return this
    }

    private fun openStoreLink(applicationId: String) {
        kotlin.runCatching {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$applicationId")))
        }.onFailure {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$applicationId")))
        }
    }

    fun getCount() = basePref.countShowRate

    fun isShowRate() : Boolean {
        return ShowRateCountManager.check(basePref.countShowRate)
    }

    fun setCancelable(isCancelAble: Boolean) {
        rateDialog.setCancelable(isCancelAble)
    }

    private fun getAlertDialog(dialogView: View) : AlertDialog {
        alertDialogBuilder.setView(dialogView)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return alertDialog
    }

    fun show() {
        if(this::rateDialog.isInitialized && !rateDialog.isShowing) {
            rateDialog.show()
            basePref.plusCount()
            val window = rateDialog.window
            val params = window?.attributes
            params?.width = getScreenWidth() - context.resources.getDimension(R.dimen._32sdp).toInt()
            window?.attributes = params
        }
    }

    private fun getScreenWidth(): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.x
    }

    private fun getScreenHeight(): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.y
    }
}