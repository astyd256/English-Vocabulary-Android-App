package com.tsu.firstlab.menu_fragments

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.tsu.firstlab.TrainingActivity
import com.tsu.firstlab.database.WordDao
import com.tsu.firstlab.database.WordDatabase
import com.tsu.firstlab.databinding.FragmentTrainingBinding
import kotlinx.coroutines.*

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch {
            val size = dao.getSize()
            if (size == 0) {
                activity?.runOnUiThread {
                    binding.textView.text = "The are no words\nin your Dictionary.\n\nYou should add words to Dictionary."
                }
            } else {
                val spannable: Spannable = SpannableString("There are $size words\nin your Dictionary.\n\nStart the Training?")
                spannable.setSpan(ForegroundColorSpan(Color.parseColor("#E3562A")), 10, 10 + size.toString().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                activity?.runOnUiThread {
                    binding.textView.text = spannable
                }


                binding.startButton.setOnClickListener {
                    binding.progressText.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.startButton.visibility = View.GONE
                    startTimer()
                }
            }
        }
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, action: () -> Unit) =scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (isActive) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }
    private fun startTimer(){
        var count = 6

        val countTimer: Job = startCoroutineTimer(0, 1050) {
            count--
            activity?.runOnUiThread(java.lang.Runnable {

                when(count){
                    5->{
                        binding.progressBar.setIndicatorColor(Color.parseColor("#FFE3562A"))
                        binding.progressBar.trackColor = Color.parseColor("#FFFFFFFF")

                    }
                    4,2,0->{

                        binding.progressBar.setIndicatorColor(Color.parseColor("#FF03DAC5"))
                        binding.progressBar.trackColor = Color.parseColor("#FFE3562A")
                        binding.progressBar.progress = 0

                    }
                    3,1,-1->{

                        binding.progressBar.setIndicatorColor(Color.parseColor("#FFE3562A"))
                        binding.progressBar.trackColor = Color.parseColor("#FF03DAC5")
                        binding.progressBar.progress = 0

                    }
                }

                binding.progressText.text = count.toString()

                when(count){
                    5->{
                        binding.progressText.setTextColor(Color.parseColor("#E3562A"))
                    }
                    4->{
                        binding.progressText.setTextColor(Color.parseColor("#65AAEA"))
                    }
                    3->{
                        binding.progressText.setTextColor(Color.parseColor("#5BA092"))
                    }
                    2->{
                        binding.progressText.setTextColor(Color.parseColor("#F2A03F"))
                    }
                    1->{
                        binding.progressText.setTextColor(Color.parseColor("#EF4949"))
                    }
                    else->{
                        binding.progressText.setTextColor(Color.parseColor("#E3562A"))
                        binding.progressText.text = "GO!"
                    }
                }

                var animator = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, 100)
                animator.startDelay = 0
                animator.duration = 1000
                animator.repeatCount = 0
                animator.start()
            })
        }
        viewLifecycleOwner.lifecycleScope.launch {

            delay(6300)
            countTimer.cancelAndJoin()
            val intent = Intent(requireContext(), TrainingActivity::class.java)
            startActivity(intent)
            binding.progressText.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.startButton.visibility = View.VISIBLE
        }
    }
}