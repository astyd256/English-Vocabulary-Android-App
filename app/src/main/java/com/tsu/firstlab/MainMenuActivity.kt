package com.tsu.firstlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tsu.firstlab.databinding.ActivityMainMenuBinding
import com.tsu.firstlab.databinding.FragmentDictionaryBinding
import com.tsu.firstlab.fragments.DictionaryFragment
import com.tsu.firstlab.fragments.TrainingFragment
import com.tsu.firstlab.fragments.VideoFragment

class MainMenuActivity : AppCompatActivity() {
    private lateinit var mainMenuBinding: ActivityMainMenuBinding
    private lateinit var dictionaryBinding: FragmentDictionaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainMenuBinding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = mainMenuBinding.root
        setContentView(view)
        replaceFragment(DictionaryFragment())

        mainMenuBinding.bottomMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dictionary -> replaceFragment(DictionaryFragment())
                R.id.training -> replaceFragment(TrainingFragment())
                R.id.video -> replaceFragment(VideoFragment())
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}