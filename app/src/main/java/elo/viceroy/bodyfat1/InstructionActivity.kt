package elo.viceroy.bodyfat1

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.droidsonroids.gif.GifImageView

class InstructionActivity: AppCompatActivity() {
    val point1:String="1. Choose two photos from gallery or make by camera.\n"+ " First photo should show the front of body,\nthe second profile (side photo).\nFollow the next points of this instruction."
    val point2:String="2. After selecting appropriate photos trim the first like on picture below and click OK.\n"+ "The trimmed photo should show:\n" + "a) vertically:  body from beard to hips line.\n"+ "b) horizontally: body from left side of stomach to right side."
    val point3:String="3. After clicking OK appear previously chosen side photo (profile photo)\n" +"If stomach and chest/breast are from left side of this photo make an reflection by clicking REFLECTION button (picture below)"
    val point4:String="4. Next trim the profile photo like on the picture below. It should show:\n"+ "a) vertically:  body from shoulder line to waist line.\n"+ "b) horizontally: body from back to stomach with some free space from both sides."
    val point5:String="5. At the end mark the line of stomach and chest/breast on trimmed profile photo- look at picture below"
    var list= mutableListOf<elements>()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.recycler_activity)
        val recycler = findViewById<RecyclerView>(R.id.recycler1)
        val button = findViewById<Button>(R.id.buttonGO)
        val gif=findViewById<GifImageView>(R.id.gifImageView_loading)
        gif.isVisible=true
        Thread {
            val image_point_1: Bitmap = ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.instr_gotow_1
                ), 1300, 1200
            )
            val image_point_2: Bitmap =  ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.ad_intr_natty_2
                ), 800, 761
            )

            val image_point_3: Bitmap =ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.instr_gotow_4
                ), 2150, 800
            )
            val image_point_4: Bitmap = ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.instr_gotow_5
                ), 800, 761
            )
            val image_point_5: Bitmap =ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.instr_gotow_6
                ), 800, 761
            )



            list.add(elements(point1, null))
            list.add(elements(point2, image_point_2))
            list.add(elements(point3, image_point_3))
            list.add(elements(point4, image_point_4))
            list.add(elements(point5, image_point_5))
            val adapter1 = adapter(list, this)
        runOnUiThread{
            gif.isVisible=false
            recycler.layoutManager =LinearLayoutManager(this)
            recycler.adapter = adapter1
            button.setOnClickListener {
                val intent1 = Intent(this@InstructionActivity, MainActivity::class.java)
                intent1.putExtra("factor", intent.getStringExtra("factor"))
                    .putExtra("gender", intent.getStringExtra("gender"))
                startActivity(intent1)
            }
        }
        }.start()

    }
}