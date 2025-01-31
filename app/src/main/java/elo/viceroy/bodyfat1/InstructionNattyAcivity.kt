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

class InstructionNattyAcivity: AppCompatActivity() {
    val point1:String="1. Read the manual below and click OK button\nThen you must put gender, weight, height\n"+ "and choose the photo from gallery looking like picture below"
    val point2:String="2. Next trim chosen photo by dragging red frame\n"+ "The trimmed photo should show:\n" + "a) vertically:  body from beard to hips line.\n"+ "b) horizontally: body from left side of stomach to right side."
    val point3:String="3. After clicking OK button, appear two ''rulers'' to designate width of waist and shoulders\nLook at picture below"
    val point4:String="4. If job is done click OK button again to see result\n"
    var list= mutableListOf<elements>()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.natty_instruction)
        val recycler = findViewById<RecyclerView>(R.id.recycler11)
        val button = findViewById<Button>(R.id.buttonGO1)
        val gif=findViewById<GifImageView>(R.id.gifImageView_loading1)
        gif.isVisible=true
        Thread {
            val image_point_1: Bitmap = ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.zd_intr_natty_1
                ), 800, 761
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
                    R.drawable.zd_intr_natty_3
                ), 800, 761
            )





            list.add(elements(point1, image_point_1))
            list.add(elements(point2, image_point_2))
            list.add(elements(point3, image_point_3))
            list.add(elements(point4, null))

            val adapter1 = adapter(list, this)
            runOnUiThread{
                gif.isVisible=false
                recycler.layoutManager =LinearLayoutManager(this)
                recycler.adapter = adapter1
                button.setOnClickListener {
                    val intent1 = Intent(this@InstructionNattyAcivity, NaturalTakePhoto::class.java)
                    startActivity(intent1)
                }
            }
        }.start()

    }
}