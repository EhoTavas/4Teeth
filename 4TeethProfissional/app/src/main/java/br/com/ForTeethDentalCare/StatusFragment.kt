package br.com.ForTeethDentalCare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import br.com.ForTeethDentalCare.databinding.FragmentSignUpBinding

class StatusFragment : Fragment() {
    private lateinit var switchButton: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_status, container, false)

        switchButton = view.findViewById(R.id.BtnSwitch)

        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                //quando o botão de switch estiver ligado
            } else {
                //quando o botão de switch estiver desligado
            }
        }
        return null
    }
}