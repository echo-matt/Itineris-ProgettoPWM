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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRistorantiBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.ristorantiRecyclerView.layoutManager = LinearLayoutManager(context)

        val data = ArrayList<RistorantiData>()
        for (i in 1..20){
            data.add(
                RistorantiData(R.drawable.photo_1506905925346_21bda4d32df4,
                    "Ristorante $i",
                    "Via roma $i",
                    Random.nextFloat() * (5 - 0))
            )
        }

        val adapter = RistorantiAdapter(data)
        adapter.setOnItemClickListener(this)
        binding.ristorantiRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onItemClick(ristorente: RistorantiData) {
        val fragment = DettagliRistorantiFragment.newInstance(ristorente)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
