package com.matteopaterno.progettopwm.attrazioni

import AttrazioniData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentDettagliAttrazioniBinding

class DettagliAttrazioniFragment : Fragment() {
    private lateinit var binding: FragmentDettagliAttrazioniBinding
    private var attrazione: AttrazioniData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDettagliAttrazioniBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attrazione?.let { fillAttrazioneDetails(it) }
    }

    private fun fillAttrazioneDetails(attrazione: AttrazioniData) {
        binding.textNome.text = attrazione.nome
        binding.textPosizione.text = attrazione.posizione
    }

    fun setAttrazione(attrazione: AttrazioniData) {
        this.attrazione = attrazione
    }

    companion object {
        fun newInstance(attrazione: AttrazioniData): DettagliAttrazioniFragment {
            val fragment = DettagliAttrazioniFragment()
            fragment.setAttrazione(attrazione)
            return fragment
        }
    }
}
