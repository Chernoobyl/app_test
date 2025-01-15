package com.example.appgesuas.ui.user.form

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appgesuas.R
import com.example.appgesuas.databinding.FragmentFormBinding
import com.example.appgesuas.extensions.hideKeyboard
import com.example.appgesuas.extensions.navigateWithAnimations
import com.example.appgesuas.extensions.setMask
import com.example.appgesuas.model.StateFields
import com.example.appgesuas.util.Masks

class FormFragment : Fragment() {

    private val TAG: String = "FormFragment"

    private val args: FormFragmentArgs by navArgs()

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FormViewModel

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initConfigs()
        initObservers()
        initListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initConfigs() {
        viewModel = ViewModelProvider(this)[FormViewModel()::class.java]

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        args?.let {
            if (it.clearForm == true) {
                clearForm()
            }
        }

        binding.apply {
            etCpf.setMask(Masks.CPF)
            etBirthDate.setMask(Masks.DATE)
            etCellPhone.setMask(Masks.CELLPHONE)
        }
    }

    private fun popBackStackFrags() {
//        navController.popBackStack(R.id.action_RegisterFragment_to_FormFragment, inclusive = false)
    }

    private fun initObservers() {
        viewModel.stateFields.observe(
            viewLifecycleOwner,
            Observer { state ->
                when (state) {
                    is StateFields.Default -> {
                        Log.i(TAG, "initObservers: StateFields.Default")
                    }

                    is StateFields.Validated -> {
                        viewModel.refuseValidation()
                        navigateToServices()
                    }

                    is StateFields.InvalidFields -> {
                        state.fields.forEach { fieldError ->
                            val mapper = mapperValidationFields()
                            mapper[fieldError.first]?.error = getString(fieldError.second)
                        }
                    }
                }
            },
        )
    }

    private fun mapperValidationFields() = mapOf(
        FormViewModel.INPUT_NAME.first to binding.etName,
        FormViewModel.INPUT_CPF.first to binding.etCpf,
        FormViewModel.INPUT_CELLPHONE.first to binding.etCellPhone,
        FormViewModel.INPUT_BIRTH_DATE.first to binding.etBirthDate,
    )

    private fun initListeners() {
        binding.buttonSave.setOnClickListener {
            binding.root.hideKeyboard()

            val name: String = binding.etName.text.toString().trim()
            val cpf: String = binding.etCpf.text.toString()
            val birthDate: String = binding.etBirthDate.text.toString()
            val cellPhone: String = binding.etCellPhone.text.toString()

            viewModel.validation(name, cpf, birthDate, cellPhone)
        }
    }

    private fun navigateToServices() {
        navController.navigateWithAnimations(
            FormFragmentDirections.actionFormFragmentToServicesFragment(
                formModel = viewModel.getFormModel(),
            ),
        )
    }

    private fun clearForm() {
        binding.apply {
            etName.text = null
            etCpf.text = null
            etBirthDate.text = null
            etCellPhone.text = null
        }
        viewModel.refuseValidation()
    }
}
