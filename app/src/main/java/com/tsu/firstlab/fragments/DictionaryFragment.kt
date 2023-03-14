package com.tsu.firstlab.fragments

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tsu.firstlab.DictionaryRecyclerViewAdapter
import com.tsu.firstlab.databinding.FragmentDictionaryBinding
import org.json.JSONArray

const val URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
class DictionaryFragment : Fragment() {
    private lateinit var binding: FragmentDictionaryBinding
    private lateinit var adapter : DictionaryRecyclerViewAdapter
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDictionaryBinding.inflate(layoutInflater)
        mediaPlayer = MediaPlayer()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        adapter = DictionaryRecyclerViewAdapter()
        var dictionaryRecyclerView = binding.dictionaryRecyclerView
        dictionaryRecyclerView.layoutManager = LinearLayoutManager(context)
        dictionaryRecyclerView.adapter = adapter

        binding.searchEditText.setOnKeyListener(View.OnKeyListener{ _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (binding.searchEditText.text.toString().isNotEmpty()) {
                    sendRequest(binding.searchEditText.text.toString())
                } else {
                    //TODO: Add something here
                }
            }
            return@OnKeyListener true
        })
    }

    private fun sendRequest(word: String){
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            URL + word,
            {
                result ->
                val arr = JSONArray(result)

                if (arr.getJSONObject(0).has("word"))
                binding.wordHeader.text = arr.getJSONObject(0).getString("word")
                else binding.wordHeader.text = ""

                if (arr.getJSONObject(0).has("phonetics"))
                {
                    if (arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).has("text"))
                    {
                        binding.phonetic.text = "[${arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).getString("text").replace("/", "")}]"
                    }
                    else if (arr.getJSONObject(0).has("phonetic"))
                        binding.phonetic.text = "[${arr.getJSONObject(0).getString("phonetic").replace("/", "")}]"
                    else binding.phonetic.visibility = View.GONE

                    if (arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).has("audio"))
                    {
                        binding.soundImage.setOnClickListener{playAudio(arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).getString("audio"))}
                    } else {
                        binding.soundImage.visibility = View.GONE
                    }
                }
                else if (arr.getJSONObject(0).has("phonetic"))
                binding.phonetic.text = "[${arr.getJSONObject(0).getString("phonetic").replace("/", "")}]"
                else binding.phonetic.visibility = View.GONE

                if (arr.getJSONObject(0).getJSONArray("meanings")
                        .getJSONObject(0).has("partOfSpeech"))
                binding.partOfSpeech.text = arr.getJSONObject(0).getJSONArray("meanings")
                    .getJSONObject(0).getString("partOfSpeech")
                else binding.partOfSpeech.visibility = View.GONE

                var definitions : ArrayList<String> = ArrayList<String>()
                var examples : ArrayList<String> = ArrayList<String>()

                if (arr.getJSONObject(0).getJSONArray("meanings")
                        .getJSONObject(0).has("definitions"))
                {
                    val defJSON = arr.getJSONObject(0).getJSONArray("meanings")
                        .getJSONObject(0).getJSONArray("definitions")
                    for (i in 0 until defJSON.length())
                    {
                        if (defJSON.getJSONObject(i).has("definition"))
                        {
                            definitions.add(defJSON.getJSONObject(i).getString("definition"))
                        } else {
                            definitions.add("")
                        }

                        if (defJSON.getJSONObject(i).has("example"))
                        {
                            examples.add(defJSON.getJSONObject(i).getString("example"))
                        } else {
                            examples.add("")
                        }
                    }
                }

                adapter.setData(definitions, examples)
            },
            {

                error ->
                var message = "There's happen to be some unknown error, please try again!"
                when (error) {
                    is NetworkError -> message = "Your device is not connected to internet. Please try again with active internet connection."
                    is ParseError -> message = "There was an error parsing data."
                    is ServerError -> message = "Internal server error occurred please try again."
                    is TimeoutError -> message = "Your connection has timed out, please try again."
                }

                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("Error!")
                dialogBuilder.setMessage(message)
                dialogBuilder.show()
            }
        )
        queue.add(request)
    }

    private fun playAudio(url: String){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
    }
}