package com.dirror.lyricviewx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lyricViewX = findViewById<LyricViewX>(R.id.lyricViewX)
        val drawTranslationCheckBox = findViewById<SwitchCompat>(R.id.switchTranslation)
        val enableBlurEffectCheckBox = findViewById<SwitchCompat>(R.id.enableBlurEffect)
        val buttonPanel = findViewById<AppCompatButton>(R.id.buttonPanel)

        drawTranslationCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            lyricViewX.setIsDrawTranslation(isChecked)
        }
        enableBlurEffectCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            lyricViewX.setIsEnableBlurEffect(isChecked)
        }

        buttonPanel.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

        val lyric = """
            [ti:失恋战线联盟]
            [ar:草蜢]
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
        lyricViewX.loadLyric(lyric)
        lyricViewX.setTextGravity(GRAVITY_CENTER)
        lyricViewX.setNormalTextSize(32f)
        lyricViewX.setCurrentTextSize(60f)
//        lyricViewX.setTranslateTextScaleValue(0.8f)
//        lyricViewX.setHorizontalOffset(-50f)
//        lyricViewX.setHorizontalOffsetPercent(0.5f)
//        lyricViewX.setItemOffsetPercent(0.5f)

        var position = 0L
        lyricViewX.setDraggable(false, object : OnPlayClickListener {
            override fun onPlayClick(time: Long): Boolean {
                position = time
                lyricViewX.updateTime(position)
                return true
            }
        })

        fun lyricUpdateLoop() {
            title = "LyricViewX: ${position / 1000L}"
            lyricViewX.updateTime(position)
            position += 200L
            lyricViewX.postDelayed({ lyricUpdateLoop() }, 200)
        }

        lyricViewX.postDelayed({ lyricUpdateLoop() }, 1000)
    }



}