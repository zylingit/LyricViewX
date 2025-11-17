package com.dirror.lyricviewx

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dirror.lyricviewx.view.ILrcBuilder
import com.dirror.lyricviewx.view.ILrcView
import com.dirror.lyricviewx.view.impl.DefaultLrcBuilder
import com.dirror.lyricviewx.view.impl.LrcRow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Timer
import java.util.TimerTask

class MainActivity2 : AppCompatActivity() {

    val TAG = "MainActivity"

    /**
     * 自定义LrcView，用来展示歌词
     */
    var mLrcView: ILrcView? = null

    /**
     * 更新歌词的频率，每100ms更新一次
     */
    private val mPlayerTimerDuration = 100

    /**
     * 更新歌词的定时器
     */
    private var mTimer: Timer? = null

    /**
     * 更新歌词的定时任务
     */
    private var mTask: TimerTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取自定义的LrcView
        setContentView(R.layout.activity_main2)
        mLrcView = findViewById<View>(R.id.lrcView) as ILrcView

        //从assets目录下读取歌词文件内容
//        val lrc = getFromAssets("test.lrc")
        val lrc = """
            [by:花丸的蜜柑面包]
            [00:00.00]作词 : Akira Sunset
            [00:00.27]作曲 : 馬場龍樹
            [00:00.54]编曲 : 縄田寿志
            [00:00.81]手手相牵 心心相连
            [00:04.15]终有一天
            [00:06.38]离别将至
            [00:09.08]但我已经 不再哭泣
            [00:12.05]昂首挺胸 展露笑颜
            [00:17.06]创造出那最美好的回忆吧
            [00:43.07]窗外已被夕阳浸润
            [00:48.55]迟暮之时已经到来
            [00:53.29]晚霞浸染之空终将消散
            [00:58.58]却终成了无法忘怀之美
            [01:03.50]此时此刻 所生之事
            [01:06.75]大概会被人们称之为 永远
            [01:13.81]和大家一起编织的回忆
            [01:18.83]永远相连
            [01:25.80]那紧紧握住的双手
            [01:29.23]誓言于胸前
            [01:31.93]在心中默默起誓 友情Forever
            [01:37.13]谢谢大家与我的相会
            [01:42.05]这将是最珍贵的宝物
            [02:08.15]那日声泪俱下之事
            [02:13.54]如今已成过往云烟
            [02:18.27]回忆涌现 无论何时
            [02:24.03]你都在身旁鼓励着我
            [02:28.67]不经意的日常
            [02:31.83]不知何时也成为了青春中特别的一页
            [02:38.98]和大家一起度过的时日 不论何时都会
            [02:44.09]熠熠生辉
            [02:50.87]手手相牵 心心相连
            [02:54.31]终有一天
            [02:56.91]离别将至
            [02:59.33]但我已经 不再哭泣
            [03:02.30]昂首挺胸 展露笑颜
            [03:07.03]创造出那最美好的回忆吧
            [03:11.03]要是能一直这样该多好啊
            [03:21.52]但是我早已知晓 时光荏苒
            [03:29.23]因为不想去后悔
            [03:35.18]所以全力奔跑吧
            [03:58.21]那紧紧握住的双手
            [04:01.37]誓言于胸前
            [04:03.97]在心中默默起誓 友情Forever
            [04:09.36]谢谢大家与我的相会
            [04:14.56]想将时间定格于此
            [04:19.20]这永远的一瞬
        """.trimIndent()
        //解析歌词构造器
        val builder: ILrcBuilder = DefaultLrcBuilder()
        //解析歌词返回LrcRow集合
        val rows: List<LrcRow> = builder.getLrcRows(lrc)
        //将得到的歌词集合传给mLrcView用来展示
        mLrcView!!.setLrc(rows)

        //开始播放歌曲并同步展示歌词
        beginLrcPlay()

        //设置自定义的LrcView上下拖动歌词时监听
        mLrcView!!.setListener { newPosition, row ->
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 从assets目录下读取歌词文件内容
     * @param fileName
     * @return
     */
    fun getFromAssets(fileName: String?): String {
        try {
            val inputReader = InputStreamReader(resources.assets.open(fileName!!))
            val bufReader = BufferedReader(inputReader)
            var line = ""
            var result = ""
            while (bufReader.readLine().also { line = it } != null) {
                if (line.trim { it <= ' ' } == "") continue
                result += """
                $line""".trimIndent()
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }


    /**
     * 开始播放歌曲并同步展示歌词
     */
    fun beginLrcPlay() {
        try {
            if (mTimer == null) {
                mTimer = Timer()
                mTask = LrcTask()
                mTimer!!.scheduleAtFixedRate(mTask, 0, mPlayerTimerDuration.toLong())
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    var timePassed = 10L
    /**
     * 展示歌曲的定时任务
     */
    inner class LrcTask : TimerTask() {
        override fun run() {
            //获取歌曲播放的位置
            this@MainActivity2.runOnUiThread { //滚动歌词
                mLrcView!!.seekLrcToTime(timePassed)
                timePassed+=500
            }
        }
    }
}