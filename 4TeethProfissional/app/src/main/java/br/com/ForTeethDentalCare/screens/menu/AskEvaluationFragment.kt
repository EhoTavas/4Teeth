package br.com.ForTeethDentalCare.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.ForTeethDentalCare.screens.login.LoggedActivity
import br.com.ForTeethDentalCare.databinding.FragmentAskRatingBinding

class AskEvaluationFragment : Fragment() {

    private var _binding: FragmentAskRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAskRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as LoggedActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}