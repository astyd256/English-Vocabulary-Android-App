package com.tsu.firstlab.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.tsu.firstlab.databinding.FragmentVideoBinding
import android.webkit.WebViewClient

class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private class VideoWebView : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if(Uri.parse(request?.url.toString()).toString().contains("https://learnenglish.britishcouncil.org/general-english/video-zone", ignoreCase = true)) {
                return super.shouldOverrideUrlLoading(view, request)
            }
            return true
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentVideoBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webview.webViewClient = VideoWebView()
        binding.webview.loadUrl("https://learnenglish.britishcouncil.org/general-english/video-zone")
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.setSupportZoom(true)


    }
}