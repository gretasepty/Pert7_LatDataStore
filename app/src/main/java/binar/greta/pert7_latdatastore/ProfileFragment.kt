package binar.greta.pert7_latdatastore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import binar.greta.pert7_latdatastore.model.GetAllUserItem
import binar.greta.pert7_latdatastore.network.ApiClient
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireContext())
        userManager.userName.asLiveData().observe(viewLifecycleOwner){
            ApiClient.instance.getAllUser(it)
                .enqueue(object : Callback<List<GetAllUserItem>>{
                    override fun onResponse(
                        call: Call<List<GetAllUserItem>>,
                        response: Response<List<GetAllUserItem>>
                    ) {
                        if (response.isSuccessful){
                            val data = response.body()!![0]
                            txt_namaProf.text = data.name
                            txt_usernameProf.text = data.username
                            txt_umurProf.text = data.umur.toString()
                            txt_alamatProf.text = data.address
                        }else{
                            Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<List<GetAllUserItem>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                    }

                })
        }

        btn_logout.setOnClickListener {
            GlobalScope.launch {
                userManager.saveData("")
                Navigation.findNavController(view).navigate(R.id.profKeHome)
            }
        }
    }

}