package code.with.cal.musicplayer

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri = Uri.parse("file:///assets/")

        val music:AssetFileDescriptor = assets.openFd("music")
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(music.fileDescriptor)
        music.close()


        mediaPlayer.prepare()
        mediaPlayer.setVolume(1f,1f)
        mediaPlayer.isLooping = false


        seekbar.progress = 0
        seekbar.max = mediaPlayer.duration

        play_btn.setOnClickListener {
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                play_btn.setImageResource(R.drawable.ic_baseline_pause_24)
            }else{
                mediaPlayer.pause()
                play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)

            }
        }
        seekbar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if (changed){
                    mediaPlayer.seekTo(pos)

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        runnable = Runnable {
            seekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
        mediaPlayer.setOnCompletionListener {
            play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            seekbar.progress = 0
        }
    }
}