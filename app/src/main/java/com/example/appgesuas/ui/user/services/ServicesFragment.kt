package com.example.appgesuas.ui.user.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appgesuas.R
import com.example.appgesuas.databinding.FragmentServicesBinding
import com.example.appgesuas.extensions.getIndexSelectedRadioButton
import com.example.appgesuas.extensions.navigateWithAnimations
import com.example.appgesuas.extensions.showCustomDialogInfo
import com.example.appgesuas.model.ServiceModel

class ServicesFragment : Fragment() {

    private val args: ServicesFragmentArgs by navArgs()

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initListeners() {
        binding.buttonContinue.setOnClickListener {
            checkSelectService()
        }
    }

    private fun checkSelectService() {
        if (binding.radioGroup.checkedRadioButtonId == -1) {
            unselectedService()
        } else {
            navigateToRegister()
        }
    }

    private fun unselectedService() {
        requireActivity().showCustomDialogInfo(
            textTitle = R.string.text_select_service,

            textButtonYes = R.string.text_understood,
            actionButtonYes = null,

            isCancelable = false,
        )
    }

    private fun navigateToRegister() {
        navController.navigateWithAnimations(
            ServicesFragmentDirections.actionServicesFragmentToRegisterFragment(
                formModel = getFormModel(),
                serviceModel = getServiceModel(),
            ),
        )
    }

    private fun getFormModel() = args.formModel

    private fun getServiceModel(): ServiceModel {
        val index: Int = binding.radioGroup.getIndexSelectedRadioButton()

        val radioButton: RadioButton = binding.radioGroup[index] as RadioButton

        val description: String = radioButton.text.toString()

        return ServiceModel(description)
    }
}
