package com.always.rateuslib

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.dialog_rate.view.*

class RateDialog  {
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var rateDialog: AlertDialog
    private lateinit var context: Context
    private lateinit var basePref: BasePreference

    fun create(context: Context, rateLink: String) : RateDialog {
        this.context = context
        basePref = BasePreference.Cache(context).builder()
        if(check()) {
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
                    edtFeedBack.visibility = View.GONE
                    rateInflater.btnSubmit.text = context.getString(R.string.maybe_later)
                    rateInflater.btnSubmit.setTextColor(Color.parseColor("#757575"))
                    rateInflater.btnSubmit.setBackgroundColor(Color.parseColor("#EEEEEE"))
                }
            }

            btnSubmit.setOnClickListener {
                if(ratingView.rating <= 3) {
                    if(edtFeedBack.text.toString().trim().isBlank()) {
                        Toast.makeText(context, "Not empty text", Toast.LENGTH_LONG).show()
                    }
                } else {
                    rateDialog.dismiss()
                    openStoreLink(rateLink)
                }
            }
        }

        return this
    }

    private fun openStoreLink(link: String) {

    }

    fun check() : Boolean {
        return basePref.countShowRate % 2 == 0
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
            plusCount()

            val window = rateDialog.window
            val params = window?.attributes
            params?.width = getScreenWidth() - context.resources.getDimension(R.dimen._32sdp).toInt()
            window?.attributes = params
        }
    }

    private fun plusCount() {
        basePref.countShowRate = ++basePref.countShowRate
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