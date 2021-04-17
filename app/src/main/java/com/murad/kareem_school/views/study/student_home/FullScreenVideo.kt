package com.murad.kareem_school.views.study.student_home

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.murad.kareem_school.R
import kotlinx.android.synthetic.main.full_video_view.*
import java.lang.Exception

class FullScreenVideo :Fragment(){

    private var video_uri : String? =null
    private  val TAG = "FullScreenVideo"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.full_video_view,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            video_uri = arguments?.getString("uri")
            initVideo()

        }catch (e:Exception){
            Log.d(TAG, "onViewCreated: faild to get the uri")
        }


    }


    fun initVideo(){
        try {
            videoView3.setVideoURI(video_uri?.toUri())
            videoView3.seekTo(100)
            videoView3.setMediaController(MediaController(requireContext()))

        }catch (e:Exception){
            Log.d(TAG, "onViewCreated: faild")
        }
    }
}