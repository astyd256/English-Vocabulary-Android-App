package com.tsu.firstlab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.tsu.firstlab.database.Word
import com.tsu.firstlab.database.WordDao
import com.tsu.firstlab.database.WordDatabase
import com.tsu.firstlab.database.WordWithMeanings
import com.tsu.firstlab.databinding.ActivityTrainingBinding
import kotlinx.coroutines.*
import kotlin.random.Random

class TrainingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainingBinding
    private lateinit var dao: WordDao
    private var size = 0
    private var rightAnswers = 0
    private var curpos = 0
    private lateinit var words : ArrayList<WordWithMeanings>
    private lateinit var wrongWords : ArrayList<Pair<String, String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = WordDatabase.getDatabase(this).wordDao
        binding = ActivityTrainingBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.var1Button.setOnClickListener {
            checkAnswer(binding.var1Button.text.toString())
        }
        binding.var2Button.setOnClickListener {
            checkAnswer(binding.var2Button.text.toString())
        }
        binding.var3Button.setOnClickListener {
            checkAnswer(binding.var3Button.text.toString())
        }
        setContentView(binding.root)
        wrongWords = ArrayList<Pair<String, String>>()
        words = ArrayList<WordWithMeanings>()
        startTest()
    }

    private fun startTest(){
        rightAnswers = 0
        curpos = 0
        wrongWords.clear()
        GlobalScope.launch{
            words = ArrayList(dao.getTenWords())
            size = dao.getSize()
            if (size > 2) {
                val allWords = ArrayList(dao.getStringWords())
                for (i in 0 until size) {
                    allWords.drop(allWords.indexOf(words[i].word.word))
                    wrongWords.add(Pair(allWords[(0 until size).random()], allWords[(0 until size).random()]))
                }
            }
            drawTest()
        }
    }

    private fun drawTest(){
        startTimer()
        if (curpos < size) {
            binding.curPosHeader.text = "${curpos + 1} of $size"
            binding.definitionHeader.text = words[curpos].meanings[(0 until words[curpos].meanings.size).random()].definition

            var variants = listOf("Smile", "Cooking", wrongWords[curpos].second)
            if (size > 2) {variants = listOf(words[curpos].word.word, wrongWords[curpos].first, wrongWords[curpos].second) }

            var index = (0 ..2).random()
            binding.var1Button.text = "A. ${variants[index]}"
            variants = variants.filterIndexed { i, _ -> i != index}
            index = (0..1).random()
            binding.var2Button.text = "B. ${variants[index]}"
            variants = variants.filterIndexed { i, _ -> i != index}
            binding.var3Button.text = "C. ${variants[0]}"
        }
        else {
            val intent = Intent(this@TrainingActivity, TrainingFinishedActivity::class.java)
            intent.putExtra("correct", rightAnswers.toString())
            intent.putExtra("incorrect", (size - rightAnswers).toString())
            startActivity(intent)
            finish()
        }
    }

    private fun checkAnswer(word : String){
        val temp = curpos
        if (word.removeRange(0, 3).lowercase() == words[curpos].word.word.lowercase()){
            rightAnswers++
            GlobalScope.launch { dao.increaseLearningProgress(words[temp].word.word) }
        } else
            GlobalScope.launch { dao.decreaseLearningProgress(words[temp].word.word) }
        curpos++
        drawTest()
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, action: () -> Unit) =scope.launch(
        Dispatchers.IO) {
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
        binding.progressBar2.max = 8
        binding.progressBar2.min = 0
        var count = 0
        var countTimer = startCoroutineTimer(0, 1000) {
            this.runOnUiThread(java.lang.Runnable {
                binding.progressBar2.progress = count
            })
            count++
        }

        this.lifecycleScope.launch {

            delay(8000)
            countTimer.cancelAndJoin()
            checkAnswer("   ")
        }
    }
}