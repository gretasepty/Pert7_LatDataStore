package binar.greta.pert7_latdatastore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener{
            if (edt_usernameReg.text.isEmpty() &&
                    edt_emailReg.text.isEmpty() &&
                    edt_passwordReg.text.isEmpty()){
                if(edt_passwordReg.text.toString() !=
                    edt_UlangPasswordReg.text.toString()){
                    Toast.makeText(requireContext(), "Konfirmasi password salah", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}