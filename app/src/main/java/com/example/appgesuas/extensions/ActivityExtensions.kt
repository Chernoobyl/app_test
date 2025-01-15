package com.example.appgesuas.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.example.appgesuas.databinding.CustomDialogAskBinding
import com.example.appgesuas.databinding.CustomDialogInfoBinding

fun Activity.showCustomDialogInfo(
    @StringRes textTitle: Int,

    @StringRes textButtonYes: Int,
    actionButtonYes: (() -> Unit)? = null,

    isCancelable: Boolean? = true,
) {
    val binding = CustomDialogInfoBinding.inflate(LayoutInflater.from(this))
    val view: View = binding.root
    val ctx: Context = binding.root.context

    val alert = AlertDialog.Builder(ctx)
    alert.setView(view)

    val dialog = alert.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(isCancelable!!)

    binding.apply {
        tvTitle.text = ctx.getText(textTitle)
        buttonYes.text = ctx.getText(textButtonYes)

        buttonYes.setOnClickListener {
            if (actionButtonYes != null) {
                actionButtonYes()
            }
            dialog.dismiss()
        }
    }

    dialog.show()
}

fun Activity.showCustomDialogAsk(
    @StringRes textTitle: Int,

    @StringRes textButtonYes: Int,
    actionButtonYes: (() -> Unit),

    @StringRes textButtonNo: Int,
    actionButtonNo: (() -> Unit)? = null,

    isCancelable: Boolean? = true,
) {
    val binding = CustomDialogAskBinding.inflate(LayoutInflater.from(this))
    val view: View = binding.root
    val ctx: Context = binding.root.context

    val alert = AlertDialog.Builder(ctx)
    alert.setView(view)

    val dialog = alert.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(isCancelable!!)

    binding.apply {
        tvTitle.text = ctx.getText(textTitle)
        buttonYes.text = ctx.getText(textButtonYes)
        buttonNo.text = ctx.getText(textButtonNo)

        buttonYes.setOnClickListener {
            actionButtonYes()
            dialog.dismiss()
        }

        buttonNo.setOnClickListener {
            if (actionButtonNo != null) {
                actionButtonNo()
            }
            dialog.dismiss()
        }
    }

    dialog.show()
}