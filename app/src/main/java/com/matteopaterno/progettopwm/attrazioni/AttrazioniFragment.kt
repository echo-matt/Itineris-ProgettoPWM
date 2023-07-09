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

class AttrazioniFragment : Fragment(), AttrazioniAdapter.OnItemClickListener {
    private lateinit var binding: FragmentAttrazioniBinding
    private lateinit var attrazioniAdapter: AttrazioniAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAttrazioniBinding.inflate(layoutInflater)

        binding.attrazioniRecyclerView.layoutManager = LinearLayoutManager(context)
        attrazioniAdapter = AttrazioniAdapter(AttrazioniDataListHolder.AttrazioniDataList)
        attrazioniAdapter.setOnItemClickListener(this)
        binding.attrazioniRecyclerView.adapter = attrazioniAdapter

        return binding.root
    }

    override fun onItemClick(attrazione: AttrazioniData) {
        binding = FragmentAttrazioniBinding.inflate(layoutInflater)
        val fragment = DettagliAttrazioniFragment.newInstance(attrazione)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )


        transaction.replace(R.id.fragment_container, fragment)
            .addToBackStack("Dettagli Attrazioni")
            .commit()
    }
}
