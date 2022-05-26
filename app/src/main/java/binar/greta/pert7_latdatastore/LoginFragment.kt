package binar.greta.pert7_latdatastore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import binar.greta.pert7_latdatastore.model.GetAllUserItem
import binar.greta.pert7_latdatastore.network.ApiClient
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private var userManager: UserManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())

        userManager!!.userName.asLiveData().observe(viewLifecycleOwner) {
            if (it != "") {
                Navigation.findNavController(requireView()).navigate(R.id.loginKeHome)
            }
        }

        btn_login.setOnClickListener {
            if (edt_username.text.isNotEmpty() && edt_password.text.isNotEmpty()) {
                val usrnmLog = edt_username.text.toString()
                val passLog = edt_password.text.toString()
                dataLogin(usrnmLog, passLog)
            } else {
                Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun dataLogin(username: String, password: String) {
        ApiClient.instance.getAllUser(username)
            .enqueue(object : Callback<List<GetAllUserItem>> {
                override fun onResponse(
                    call: Call<List<GetAllUserItem>>,
                    response: Response<List<GetAllUserItem>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!![0].password == password) {
                            GlobalScope.launch {
                                userManager!!.saveData(username)
                            }
                            Navigation.findNavController(view!!).navigate(R.id.loginKeHome)
                        } else {
                            Toast.makeText(requireContext(), "Salah", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                override fun onFailure(call: Call<List<GetAllUserItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}