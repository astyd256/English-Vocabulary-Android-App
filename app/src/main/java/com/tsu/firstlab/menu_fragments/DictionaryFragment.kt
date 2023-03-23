package com.tsu.firstlab.menu_fragments

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tsu.firstlab.DictionaryRecyclerViewAdapter
import com.tsu.firstlab.database.Meaning
import com.tsu.firstlab.database.Word
import com.tsu.firstlab.database.WordDao
import com.tsu.firstlab.database.WordDatabase
import com.tsu.firstlab.databinding.FragmentDictionaryBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

const val URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
class DictionaryFragment : Fragment() {
    private lateinit var binding: FragmentDictionaryBinding
    private lateinit var adapter : DictionaryRecyclerViewAdapter
    private lateinit var curWord : Word
    private var curMeanings : ArrayList<Meaning> = ArrayList()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var dao : WordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDictionaryBinding.inflate(layoutInflater)
        mediaPlayer = MediaPlayer()
        dao = WordDatabase.getDatabase(requireContext()).wordDao
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

        binding.button.setOnClickListener{

            GlobalScope.launch{
                dao.insertWord(curWord)
                curMeanings.forEach { dao.insertMeaning(it) }
            }
        }
    }

    private fun sendRequest(word: String){
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            URL + word,
            {
                result ->
                val arr = JSONArray(result)

                var wordHeader = ""
                var phonetic = ""
                var soundURL = ""
                var partOfSpeech = ""

                if (arr.getJSONObject(0).has("word"))
                 wordHeader = arr.getJSONObject(0).getString("word")

                if (arr.getJSONObject(0).has("phonetics") && arr.getJSONObject(0).getJSONArray("phonetics").length() > 0)
                {
                    if (arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).has("text"))
                    {
                         phonetic = "[${arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).getString("text").replace("/", "")}]"
                    }
                    else if (arr.getJSONObject(0).has("phonetic"))
                        phonetic = "[${arr.getJSONObject(0).getString("phonetic").replace("/", "")}]"

                    if (arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).has("audio"))
                        soundURL = arr.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).getString("audio")
                }
                else if (arr.getJSONObject(0).has("phonetic"))
                phonetic = "[${arr.getJSONObject(0).getString("phonetic").replace("/", "")}]"

                if (arr.getJSONObject(0).getJSONArray("meanings").getJSONObject(0).has("partOfSpeech"))
                    partOfSpeech = arr.getJSONObject(0).getJSONArray("meanings")
                    .getJSONObject(0).getString("partOfSpeech")

                curWord = Word(wordHeader, phonetic, partOfSpeech, soundURL, 0)

                var definition : String
                var example : String

                curMeanings.clear()
                if (arr.getJSONObject(0).getJSONArray("meanings")
                        .getJSONObject(0).has("definitions")) {
                    val defJSON = arr.getJSONObject(0).getJSONArray("meanings")
                        .getJSONObject(0).getJSONArray("definitions")
                    for (i in 0 until defJSON.length()) {
                        definition = if (defJSON.getJSONObject(i).has("definition")) {
                            defJSON.getJSONObject(i).getString("definition")
                        } else {
                            ""
                        }

                        example = if (defJSON.getJSONObject(i).has("example")) {
                            defJSON.getJSONObject(i).getString("example")
                        } else {
                            ""
                        }

                        curMeanings.add(Meaning(0, definition, example, wordHeader))
                    }
                }

                viewUpdate()
            },
            {
                error ->
                if (!checkForInternet())
                {
                    suspend {
                        curWord = dao.findByWord(word)
                        val curWordWithMeanings = dao.findWordWithMeanings(word)

                        if (!curWord.word.isNullOrEmpty()) {
                            curMeanings.clear()
                            for (i in 0 until curWordWithMeanings[0].meanings.size) {
                                curMeanings.add(Meaning(0, curWordWithMeanings[0].meanings[i].definition,
                                    curWordWithMeanings[0].meanings[i].example, curWordWithMeanings[0].meanings[i].word))
                            }
                            activity?.runOnUiThread{viewUpdate()}
                        } else {
                            val dialogBuilder = AlertDialog.Builder(requireContext())
                            dialogBuilder.setTitle("Error!")
                            dialogBuilder.setMessage("Your device is not connected to internet. Please try again with active internet connection.")

                            activity?.runOnUiThread{
                                dialogBuilder.show()
                            }

                        }
                    }
                } else {
                    val dialogBuilder = AlertDialog.Builder(requireContext())
                    dialogBuilder.setTitle("Error!")
                    dialogBuilder.setMessage("There's happen to be some unknown error, please try again!\nError: $error")
                    dialogBuilder.show()
                }
            }
        )
        queue.add(request)
    }
    private fun checkForInternet(): Boolean{

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
    private fun viewUpdate(){

        binding.wordHeader.text = curWord.word

        if (curWord.soundURL != "")
            binding.soundImage.setOnClickListener{playAudio(curWord.soundURL)}
        else
            binding.soundImage.visibility = View.GONE

        if (curWord.phonetic != "")
            binding.phonetic.text
        else
            binding.phonetic.visibility = View.GONE

        if (curWord.partOfSpeech != "")
            binding.partOfSpeech.text
        else
            binding.partOfSpeech.visibility = View.GONE

        var definitions : ArrayList<String> = ArrayList()
        var examples : ArrayList<String> = ArrayList()
        for (i in 0 until curMeanings.size)
        {
            definitions.add(curMeanings[i].definition)
            examples.add(curMeanings[i].example)
        }
        adapter.setData(definitions, examples)
    }
    private fun playAudio(url: String){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
    }

}