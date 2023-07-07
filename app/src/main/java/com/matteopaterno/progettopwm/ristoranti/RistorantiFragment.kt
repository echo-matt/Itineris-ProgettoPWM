package com.matteopaterno.progettopwm.ristoranti

import RistorantiData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentRistorantiBinding
import kotlin.random.Random

class RistorantiFragment : Fragment(), RistorantiAdapter.OnItemClickListener {
    private lateinit var binding: FragmentRistorantiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRistorantiBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.ristorantiRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.ristorantiRecyclerView.adapter = createRistorantiList()
        return binding.root
    }

    fun createRistorantiList(): RistorantiAdapter {
        val data = ArrayList<RistorantiData>()
        for (i in 1..20) {
            data.add(
                RistorantiData(
                    R.drawable.photo_1506905925346_21bda4d32df4, R.drawable.tag,
                    "Ristorante $i",
                    "Via roma $i",
                    Random.nextFloat() * (5 - 0)
                )
            )
        }
        RistorantiDataListHolder.RistorantiDataList.addAll(data)

        val adapter = RistorantiAdapter(data)
        adapter.setOnItemClickListener(this)
        return adapter

    }

    override fun onItemClick(ristorente: RistorantiData) {
        binding = FragmentRistorantiBinding.inflate(layoutInflater)
        val fragment = DettagliRistorantiFragment.newInstance(ristorente)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.slide_in_right, // Animazione di transizione in entrata per DettagliRistorantiFragment
            R.anim.slide_out_left, // Animazione di transizione in uscita per RistorantiFragment
            R.anim.slide_in_left, // Animazione di transizione in entrata per RistorantiFragment
            R.anim.slide_out_right // Animazione di transizione in uscita per DettagliRistorantiFragment
        )

        transaction.replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
