package com.example.appgesuas.ui.user.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgesuas.R
import com.example.appgesuas.extensions.hasTwoNames
import com.example.appgesuas.model.FormModel
import com.example.appgesuas.model.StateFields
import com.example.appgesuas.util.Validations
import com.example.appgesuas.util.Masks

class FormViewModel : ViewModel() {

    private var name: String = ""
    private var cpf: String = ""
    private var birthDate: String? = null
    private var cellPhone: String = ""

    private val _stateFields = MutableLiveData<StateFields>()
    val stateFields: LiveData<StateFields>
        get() = _stateFields

    companion object {
        val INPUT_NAME = "INPUT_NAME" to R.string.text_error_field_name
        val INPUT_CPF = "INPUT_CPF" to R.string.text_error_field_cpf
        val INPUT_CELLPHONE = "INPUT_CELLPHONE" to R.string.text_error_field_cellphone
        val INPUT_BIRTH_DATE = "INPUT_BIRTH_DATE" to R.string.text_error_field_birth_date
    }

    private fun isValidForm(name: String, cpf: String, birthDate: String, cellPhone: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()

        if (name.isEmpty() || !name.hasTwoNames()) {
            invalidFields.add(INPUT_NAME)
        }

        if (cpf.isEmpty() || cpf.length < Masks.CPF.length || !Validations.isValidCpf(cpf)) {
            invalidFields.add(INPUT_CPF)
        }

        if (!birthDate.isEmpty() && birthDate.length < Masks.DATE.length || !Validations.isValidDate(birthDate)) {
            invalidFields.add(INPUT_BIRTH_DATE)
        }

        if (cellPhone.isEmpty() || cellPhone.length < Masks.CELLPHONE.length) {
            invalidFields.add(INPUT_CELLPHONE)
        }

        if (invalidFields.isNotEmpty()) {
            _stateFields.value = StateFields.InvalidFields(invalidFields)
            return false
        }

        return true
    }

    fun validation(name: String, cpf: String, birthDate: String, cellPhone: String) {
        if (isValidForm(name, cpf, birthDate, cellPhone)) {
            this.name = name
            this.cpf = cpf
            this.birthDate = birthDate
            this.cellPhone = cellPhone

            _stateFields.value = StateFields.Validated
        }
    }

    fun refuseValidation() {
        _stateFields.value = StateFields.Default
    }

    fun getFormModel() = FormModel(
        name = name,
        cpf = cpf,
        birtDate = birthDate,
        cellPhone = cellPhone,
    )
}
