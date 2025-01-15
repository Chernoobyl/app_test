package com.example.appgesuas.ui.user.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appgesuas.R
import com.example.appgesuas.databinding.FragmentRegisterBinding
import com.example.appgesuas.extensions.calculateAge
import com.example.appgesuas.extensions.navigateWithAnimations
import com.example.appgesuas.extensions.showCustomDialogAsk
import com.example.appgesuas.extensions.showCustomDialogInfo
import com.example.appgesuas.widgets.CustomBottomSheetForwarder
import com.example.appgesuas.widgets.CustomBottomSheetMotive

class RegisterFragment : Fragment() {

    private val args: RegisterFragmentArgs by navArgs()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    private val navController: NavController by lazy {
        findNavController()
    }

    private val bottomSheetForwarder = CustomBottomSheetForwarder()
    private val bottomSheetMotive = CustomBottomSheetMotive()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initConfigs()
        initListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initConfigs() {
        // Corrige o bug do layout quando abre o teclado.
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        viewModel = ViewModelProvider(this)[RegisterViewModel()::class.java]

        args?.let {
            it.formModel?.let { form ->
                binding.apply {
                    tvUserName.text = form.name
                    tvUserCpf.text = form.cpf
                    tvUserBirthDate.text = form.birtDate

                    val age = form.birtDate?.calculateAge()
                    tvUserAge.text = if (age == -1) "" else age.toString()
                }
            }
            it.serviceModel?.let {}
        }
    }

    private fun initListeners() {
        binding.buttonForwardTo.setOnClickListener {
            showBottomSheetForwarder()
        }

        binding.buttonMotive.setOnClickListener {
            showBottomSheetMotive()
        }

        bottomSheetForwarder.listener = { forwarder ->
            viewModel.setForwarder(forwarder)
        }

        bottomSheetMotive.listener = { motive ->
            viewModel.setMotive(motive)
        }

        binding.buttonForwarder.setOnClickListener {
            sendRegister()
        }
    }

    private fun showBottomSheetForwarder() {
        val tag = CustomBottomSheetForwarder.TAG
        childFragmentManager.let {
            bottomSheetForwarder.show(it, tag)
        }
    }

    private fun showBottomSheetMotive() {
        val tag = CustomBottomSheetMotive.TAG
        childFragmentManager.let {
            bottomSheetMotive.isCancelable = false
            bottomSheetMotive.show(it, tag)
        }
        if (bottomSheetMotive.isVisible) {
            bottomSheetMotive.submitMotive(viewModel.getMotive())
        }
    }

    private fun sendRegister() {
        requireActivity().showCustomDialogAsk(
            textTitle = R.string.text_confirm_forward,

            textButtonYes = R.string.text_confirm,
            actionButtonYes = {
                successSending()
            },

            textButtonNo = R.string.text_cancel,
            actionButtonNo = null,

            isCancelable = false,
        )
    }

    private fun successSending() {
        requireActivity().showCustomDialogInfo(
            textTitle = R.string.text_success_sending,

            textButtonYes = R.string.text_back_to_form,
            actionButtonYes = {
                navigateToForm()
            },

            isCancelable = false,
        )
    }

    private fun navigateToForm() {
        navController.navigateWithAnimations(
            RegisterFragmentDirections.actionRegisterFragmentToFormFragment(
                clearForm = true,
            ),
        )
    }
}
