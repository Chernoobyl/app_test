import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var name: String = ""
    private var cpf: String = ""
    private var phoneNumber: String = ""

    fun saveUserData(name: String, cpf: String, phoneNumber: String) {
        this.name = name
        this.cpf = cpf
        this.phoneNumber = phoneNumber

        // Aqui você pode adicionar a lógica para salvar os dados no banco de dados ou em algum outro local
    }

    fun getName(): String {
        return name
    }

    fun getCpf(): String {
        return cpf
    }

    fun getPhoneNumber(): String {
        return phoneNumber
    }
}

// Modificação simulada para commit em 2025-01-17
