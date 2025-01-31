package elo.viceroy.bodyfat1



import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.media.ExifInterface
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.lang.Thread.sleep
import java.util.Map
import kotlin.math.absoluteValue


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("Range")
class MainActivity : AppCompatActivity() {
    lateinit var take_uri_photo:Uri
    lateinit var take_uri_photo_side:Uri
    var values=ContentValues()
    var values_side=ContentValues()
    lateinit var image_capture:Intent
    lateinit var observer : MyLifecycleObserver
    var two_photos_chosen=0

    var handler1=Handler(Looper.getMainLooper())

    lateinit var uri_front_photo:Uri
    var orientation_front:Int=0
    var id_side:Long=0
    var orientation_side:Int=0






    @SuppressLint("SuspiciousIndentation", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.plot)*/
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        val button_pick=findViewById<Button>(R.id.choose_image)
        val button_take=findViewById<Button>(R.id.take_picture)
        val image=findViewById<ImageView>(R.id.image_view)
        val text=findViewById<TextView>(R.id.tekst6)
        val next_button=findViewById<Button>(R.id.nextstep)
        next_button.isVisible=false
        text.text="Choose front photo"
        observer = MyLifecycleObserver(this.activityResultRegistry,this)
        lifecycle.addObserver(observer)
        handler1=object: Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what==1)
                {
                    text.text="Choose side photo"
                    two_photos_chosen = 1
                }
                else if (msg.what==2)
                {
                    next_button.isVisible=true
                    button_pick.isVisible=false
                    button_take.isVisible=false
                    text.text="Go to next step"
                    two_photos_chosen = 2
                }
                else if (msg.what==3) {

                    if (two_photos_chosen == 0) {
                        observer.launcher_start_for_activity_result()
                    } else if (two_photos_chosen == 1) {
                        observer.launcher_start_for_activity_result_side()
                    }
                }
                else if (msg.what==4) {

                    if (two_photos_chosen==0) {
                        observer.launcher_pickPhoto()
                    }
                    else if (two_photos_chosen==1)
                    {
                        observer.launcher_pickPhoto_side()
                    }

                }

            }
        }






        button_pick.setOnClickListener {

            if (two_photos_chosen==0) {
                observer.launcher_pickPhoto()
            }
            else if (two_photos_chosen==1)
            {
                observer.launcher_pickPhoto_side()
            }



        }


        button_take.setOnClickListener{

            if (two_photos_chosen == 0) {
                observer.launcher_start_for_activity_result()
            } else if (two_photos_chosen == 1) {
                observer.launcher_start_for_activity_result_side()
            }


        }

next_button.setOnClickListener {
    val intent1 = Intent(this@MainActivity, SecondActivity::class.java)
    intent1.setData(uri_front_photo).putExtra("orientation", orientation_front)
        .putExtra("factor", intent.getStringExtra("factor"))
        .putExtra("gender", intent.getStringExtra("gender"))
    intent1.putExtra("orientation_side", orientation_side).putExtra("side_photo_uri_index",id_side)

    startActivity(intent1)
    finish()  }

    }

    override fun onStart() {
        super.onStart()

    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    fun create_new_photo_row():Intent
    {


        try {
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values.put(
                MediaStore.Images.Media.DISPLAY_NAME,
                System.currentTimeMillis().toString()
            )
            values.put(
                MediaStore.Images.Media.DATE_ADDED,
                System.currentTimeMillis() / 1000
            )
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
            values.put(MediaStore.Images.Media.IS_PENDING, false)


            val contentr = applicationContext.contentResolver

            take_uri_photo= contentr.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )!!
            sleep(100)

            image_capture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            image_capture.putExtra(MediaStore.EXTRA_OUTPUT, take_uri_photo)




        } catch (e: Exception) {

        }


        return image_capture
    }

    fun create_new_photo_row_side():Intent
    {


        try {
            values_side.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values_side.put(
                MediaStore.Images.Media.DISPLAY_NAME,
                System.currentTimeMillis().toString()
            )
            values_side.put(
                MediaStore.Images.Media.DATE_ADDED,
                System.currentTimeMillis() / 1000
            )
            values_side.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values_side.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
            values_side.put(MediaStore.Images.Media.IS_PENDING, false)


            val contentr = applicationContext.contentResolver

            take_uri_photo_side= contentr.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )!!
            sleep(100)

            image_capture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            image_capture.putExtra(MediaStore.EXTRA_OUTPUT, take_uri_photo)




        } catch (e: Exception) {

        }


        return image_capture
    }
    fun sendmessagetoHandler(i:Int) {
        val mess=Message.obtain(handler1,i,1,1)
        handler1.sendMessage(mess)
    }

}


























