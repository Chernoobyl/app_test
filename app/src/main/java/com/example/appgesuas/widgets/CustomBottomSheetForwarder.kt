package com.example.appgesuas.widgets

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.example.appgesuas.R
import com.example.appgesuas.databinding.CustomBottomSheetForwarderBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetForwarder(
    var listener: (forwarder: String) -> Unit = {},
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CustomBottomSheetForwarder"
    }

    private lateinit var binding: CustomBottomSheetForwarderBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CustomBottomSheetForwarderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.let {
            val sheet = it as BottomSheetDialog
            sheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            sheet.behavior.skipCollapsed = true
        }

        binding.buttonSave.setOnClickListener {
            val viewGroup: ViewGroup = binding.containerForwarder

            viewGroup.forEach { view ->
                if (view is TextView) {
                    if (view.background is ColorDrawable) {
                        val bgColor: Int = (view.background as ColorDrawable).color
                        val bgColorSelected: Int = ContextCompat.getColor(view.context, R.color.bg_select_item)

                        if (bgColor == bgColorSelected) {
                            listener(view.text.toString())
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }

        binding.forwarderCRAS.setOnClickListener {
            selectOneViewAndUnselectOthers(it)
        }

        binding.forwarderCREAS.setOnClickListener {
            selectOneViewAndUnselectOthers(it)
        }

        binding.forwarderDefensoriaPublica.setOnClickListener {
            selectOneViewAndUnselectOthers(it)
        }

        binding.forwarderMinisterioPublico.setOnClickListener {
            selectOneViewAndUnselectOthers(it)
        }

        binding.forwarderPoderJudiciario.setOnClickListener {
            selectOneViewAndUnselectOthers(it)
        }
    }

    private fun toggleView(view: View, selected: Boolean) {
        if (selected) {
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.bg_select_item))
            if (view is TextView) {
                view.setTextColor(ContextCompat.getColor(view.context, R.color.text_black))
            }
        } else {
            view.setBackgroundColor(0)
            if (view is TextView) {
                view.setTextColor(ContextCompat.getColor(view.context, R.color.text_gray))
            }
        }
    }

    private fun selectOneViewAndUnselectOthers(viewToSelect: View) {
        val viewGroup: ViewGroup = binding.containerForwarder
        for (i in 0 until viewGroup.childCount) {
            val tmpView = viewGroup.getChildAt(i)
            if (tmpView.id == viewToSelect.id) {
                toggleView(viewToSelect, true)
            } else {
                toggleView(tmpView, false)
            }
        }
    }
}
