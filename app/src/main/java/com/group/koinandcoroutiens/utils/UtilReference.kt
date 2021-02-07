package com.group.koinandcoroutiens.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.group.koinandcoroutiens.R

object UtilReference {

     fun showSnackbar(mView: View, txt: String) {

        val snackbar = Snackbar.make(mView, txt, Snackbar.LENGTH_SHORT)

        val snackbarView = snackbar.view

        snackbarView.setBackgroundColor(ContextCompat.getColor(mView.context, R.color.teal_200))

        val snackbarTextView = snackbarView.findViewById(R.id.snackbar_text) as TextView

        snackbarTextView.setTextColor(ContextCompat.getColor(mView.context, R.color.white))

        snackbarTextView.text = txt

        snackbar.show()

    }

/*

     fun showSnackbar(mView: View, txt: String, snackbarBackgroundColorId: Int) {

        val snackbar = Snackbar.make(mView, txt, Snackbar.LENGTH_SHORT)

        val snackbarView = snackbar.view

        snackbarView.setBackgroundColor(
            ContextCompat.getColor(
                mView.context,
                snackbarBackgroundColorId
            )
        )

        val snackbarTextView = snackbarView.findViewById(R.id.snackbar_text) as TextView

        snackbarTextView.setTextColor(ContextCompat.getColor(mView.context, R.color.white))

        snackbarTextView.text = txt

        snackbar.show()

    }

     fun showSnackbar(
        mView: View,
        txt: String,
        snackbarBackgroundColorId: Int,
        snackbarTextColorId: Int
    ) {

        val snackbar = Snackbar.make(mView, txt, Snackbar.LENGTH_SHORT)

        val snackbarView = snackbar.view

        snackbarView.setBackgroundColor(
            ContextCompat.getColor(
                mView.context,
                snackbarBackgroundColorId
            )
        )

        val snackbarTextView = snackbarView.findViewById(R.id.snackbar_text) as TextView

        snackbarTextView.setTextColor(ContextCompat.getColor(mView.context, snackbarTextColorId))

        snackbarTextView.text = txt

        snackbar.show()

    }
*/

}