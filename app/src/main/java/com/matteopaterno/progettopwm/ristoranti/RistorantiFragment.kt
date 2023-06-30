package com.matteopaterno.progettopwm.ristoranti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentRistorantiBinding
import kotlin.random.Random

class RistorantiFragment : Fragment() {
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
        binding.recyclerviewRistoranti.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<RistorantiData>()
        for (i in 1..20){
            data.add(
                RistorantiData(R.drawable.photo_1506905925346_21bda4d32df4,
                "Ristorante "+i,
            "Via roma "+ i,
            Random.nextFloat()* (5-0)))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}