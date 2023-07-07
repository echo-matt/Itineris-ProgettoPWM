package com.matteopaterno.progettopwm.ristoranti

import RistorantiData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentDettagliRistorantiBinding

class DettagliRistorantiFragment : Fragment() {
    private lateinit var binding: FragmentDettagliRistorantiBinding
    private var ristorante: RistorantiData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentDettagliRistorantiBinding.inflate(inflater, container, false)

        binding.menu.setOnClickListener {
            val menuFragment = parentFragmentManager.findFragmentByTag("MenuRistorante")

            if (menuFragment == null) {
                val startFragmentIsMenu = MenuRistorante()
                parentFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainerView.id, startFragmentIsMenu, "MenuRistorante")
                    .commit()
            }
        }

        binding.recensioni.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(binding.fragmentContainerView.id, RecensioniRistorante())?.commit()
        }

        binding.info.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(binding.fragmentContainerView.id, InfoRistorante())?.commit()
        }

        binding.menu.callOnClick()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ristorante?.let { fillRistoranteDetails(it) }
    }

    private fun fillRistoranteDetails(ristorante: RistorantiData) {
        binding.textNome.text = ristorante.nome
        binding.textPosizione.text = ristorante.posizione
        binding.ratingBar.rating = ristorante.rating
        // Aggiungi altre informazioni del ristorante se necessario
    }

    fun setRistorante(ristorante: RistorantiData) {
        this.ristorante = ristorante
    }

    companion object {
        fun newInstance(ristorante: RistorantiData): DettagliRistorantiFragment {
            val fragment = DettagliRistorantiFragment()
            fragment.setRistorante(ristorante)
            return fragment
        }
    }
}
