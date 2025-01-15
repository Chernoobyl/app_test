package com.example.appgesuas.widgets

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.appgesuas.R
import com.example.appgesuas.databinding.CustomBottomSheetMotiveBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetMotive(
    var listener: (motive: String) -> Unit = {},
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CustomBottomSheetMotive"
    }

    private lateinit var binding: CustomBottomSheetMotiveBinding

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogFragmentNoBackground
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CustomBottomSheetMotiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMarginTop(view)
        dialog?.let {
            // Exibir todos os itens do layout (se possivel).
            val sheet = it as BottomSheetDialog
            sheet.behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels

            // Full screen
            val sheetContainer = requireView().parent as? ViewGroup ?: return
            sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvSave.setOnClickListener {
            val motive = binding.etMotive.text.toString()
            if (!motive.isNullOrEmpty()) {
                listener(motive)
                dismiss()
            }
        }

        binding.etMotive.setOnTouchListener { v, event ->
            if (v.id == R.id.etMotive) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
            false
        }
    }

    private fun addMarginTop(view: View) {
        val layoutParams: FrameLayout.LayoutParams = view.layoutParams as FrameLayout.LayoutParams
        val marginTop = 50.toPx().toInt()
        layoutParams.setMargins(0, marginTop, 0, 0)
        view.layoutParams = layoutParams
    }

    private fun Number.toPx() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics,
    )

    internal fun submitMotive(motive: String = "") {
        binding.etMotive.setText(motive)
    }
}
