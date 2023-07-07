package com.matteopaterno.progettopwm

import android.app.DatePickerDialog
import kotlin.IntArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.matteopaterno.progettopwm.databinding.FragmentPrenotazioneBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PrenotazioneFragment : Fragment() {
    private lateinit var binding: FragmentPrenotazioneBinding
    private lateinit var calendar: Calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var checkInFocused: Boolean
        binding = FragmentPrenotazioneBinding.inflate(layoutInflater)
        calendar = Calendar.getInstance()

        binding.editTextCheckIn.isFocusable = false
        binding.editTextCheckIn.isClickable = true
        binding.editTextCheckIn.setOnClickListener{
            checkInFocused = true
            showDatePicker(checkInFocused)
        }

        binding.editTextCheckOut.isFocusable = false
        binding.editTextCheckOut.isClickable = true
        binding.editTextCheckOut.setOnClickListener{
            checkInFocused = false
            showDatePicker(checkInFocused)
        }

        val spinner = binding.spinnerGuest
        val guestsArray = resources.getIntArray(R.array.guests_array).toMutableList()
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinner.id, guestsArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        return binding.root
    }

    private fun showDatePicker(checkInFocused: Boolean) {
        val dateSetListener = DatePickerDialog.OnDateSetListener{_ : DatePicker, year: Int, month: Int, day: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            if (checkInFocused){
                updateCheckInDate()
            }else{
                updateCheckOutDate()
            }

        }

        DatePickerDialog(
            requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    private fun updateCheckInDate() {
        val formato = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(formato, Locale.ITALIAN)
        binding.editTextCheckIn.setText(dateFormat.format(calendar.time))
    }

    private fun updateCheckOutDate() {
        val formato = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(formato, Locale.ITALIAN)
        binding.editTextCheckOut.setText(dateFormat.format(calendar.time))
    }
}