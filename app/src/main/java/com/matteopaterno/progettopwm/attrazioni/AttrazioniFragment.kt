package com.matteopaterno.progettopwm.attrazioni

import AttrazioniData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentAttrazioniBinding
import kotlin.random.Random

class AttrazioniFragment : Fragment(), AttrazioniAdapter.OnItemClickListener {
    private lateinit var binding: FragmentAttrazioniBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttrazioniBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.attrazioniRecyclerView.layoutManager = LinearLayoutManager(context)

        val data = ArrayList<AttrazioniData>()
        for (i in 1..20){
            data.add(
                AttrazioniData(R.drawable.photo_1506905925346_21bda4d32df4,
                    "Attrazione $i",
                    "Via Milano $i",
                    Random.nextFloat() * (5 - 0))
            )
        }

        val adapter = AttrazioniAdapter(data)
        adapter.setOnItemClickListener(this)
        binding.attrazioniRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onItemClick(attrazione: AttrazioniData) {
        val fragment = DettagliAttrazioniFragment.newInstance(attrazione)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )


        transaction.replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
