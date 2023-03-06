package com.tsu.firstlab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.tsu.firstlab.databinding.ActivityOnBoardBinding

class OnBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardBinding
    private var currentPageIndex = 0

    private var headerList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imageList = mutableListOf<Int>()
    private var pagList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addToList(getString(R.string.onboard_header1), getString(R.string.onboard_text1), R.drawable.intro1, R.drawable.pagination1)
        addToList(getString(R.string.onboard_header2), getString(R.string.onboard_text2), R.drawable.intro2, R.drawable.pagination2)
        addToList(getString(R.string.onboard_header3), getString(R.string.onboard_text3), R.drawable.intro3, R.drawable.pagination3)

        var viewPager = binding.ViewPager
        viewPager.adapter = ViewPagerAdapter(headerList, descList, imageList, pagList)

        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 2) {
                binding.buttonNext.text = getString(R.string.onboard_button3)
            }
            else{
                binding.buttonNext.text = getString(R.string.onboard_button1)
            }
        }
        })

        binding.buttonNext.setOnClickListener {
            currentPageIndex = viewPager.currentItem
            if(currentPageIndex < 2) {
                currentPageIndex++
                viewPager.setCurrentItem(currentPageIndex, true)
            } else if (currentPageIndex == 2) {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.buttonSkip.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addToList(header: String, description: String, image: Int, pagImage: Int) {
        headerList.add(header)
        descList.add(description)
        imageList.add(image)
        pagList.add(pagImage)

    }
}