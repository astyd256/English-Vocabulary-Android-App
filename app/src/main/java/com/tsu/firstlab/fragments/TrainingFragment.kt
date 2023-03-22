package com.tsu.firstlab.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsu.firstlab.R
import com.tsu.firstlab.database.WordDao
import com.tsu.firstlab.database.WordDatabase
import com.tsu.firstlab.databinding.FragmentDictionaryBinding
import com.tsu.firstlab.databinding.FragmentTrainingBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrainingFragment : Fragment() {
    private lateinit var binding: FragmentTrainingBinding
    private lateinit var dao: WordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTrainingBinding.inflate(layoutInflater)
        dao = WordDatabase.getDatabase(requireContext()).wordDao

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GlobalScope.launch{
            val size = dao.getSize()
            val spannable: Spannable = SpannableString("There are $size words\nin your Dictionary.\n\nStart the Training?")
            spannable.setSpan(ForegroundColorSpan(Color.parseColor("#E3562A")), 10, 10 + size.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.textView.text = spannable
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}