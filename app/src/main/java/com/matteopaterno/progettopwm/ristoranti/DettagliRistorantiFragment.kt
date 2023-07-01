package com.matteopaterno.progettopwm.ristoranti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matteopaterno.progettopwm.R

class DettagliRistorantiFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dettagli_ristoranti, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            DettagliRistorantiFragment().apply {
                arguments = Bundle().apply {
                }
            }

        fun newInstance(ristorente: RistorantiData): DettagliRistorantiFragment {
            return DettagliRistorantiFragment()
        }
    }
}