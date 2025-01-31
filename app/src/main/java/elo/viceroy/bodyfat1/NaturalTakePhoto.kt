package elo.viceroy.bodyfat1

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

class NaturalTakePhoto : AppCompatActivity()  {

    var heightUnitArray=arrayOf<String>("cm","inch")
    var weighttUnitArray=arrayOf<String>("kg","lbs")
    var genderArray=arrayOf<String>("Male","Female")
    var weight:Double=0.0
    var height:Double=0.0
    var gender:String?=genderArray[1]
    var weightUnit:String=weighttUnitArray[0]
    var heightUnit:String=heightUnitArray[0]
    lateinit var photoUri: Uri
    lateinit var handler:Handler
    var text_photo_name=""
    var orientation=0
    @SuppressLint("Range")
    val launcher_pickphoto=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data?.data != null) {
            photoUri=it.data?.data!!
            val contres=applicationContext.contentResolver
            val cursor=contres.query(photoUri,arrayOf(MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.ORIENTATION),null,null,null)
            cursor!!.moveToLast()
            text_photo_name=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
            orientation=cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION))
            val msg=Message.obtain(handler,2,1,1)
            handler.sendMessage(msg)
        }

    }
    val launcher_permission_gallery=registerForActivityResult(ActivityResultContracts.RequestPermission())
    {
        if (it)
        {
            val msg=Message.obtain(handler,1,1,1)
            handler.sendMessage(msg)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.natural_take_photo)
        val uptekst=findViewById<TextView>(R.id.tekstview1)
        val spinnerWeightUnits=findViewById<Spinner>(R.id.weight_unit1)
        val spinnerHeightUnits=findViewById<Spinner>(R.id.height_unit1)
        val spinnerGender=findViewById<Spinner>(R.id.gender_list1)
        val heightEditText=findViewById<EditText>(R.id.height1)
        val weightEditText=findViewById<EditText>(R.id.weight1)
        val buttonOK=findViewById<Button>(R.id.OK1)
        val naturaltakephotolayout=findViewById<ConstraintLayout>(R.id.naturaltakephotolayout)
        val button_take_photo=findViewById<Button>(R.id.choose_picture1)
        val teksviewphoto=findViewById<TextView>(R.id.tekstviewphoto)

        handler=object:Handler(Looper.getMainLooper())
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what==1)
                {
                    val inte=Intent(Intent.ACTION_PICK)
                    inte.setType("image/*")
                    launcher_pickphoto.launch(inte)
                }
                else if (msg.what==2)
                {
                    teksviewphoto.text=text_photo_name
                }
            }
        }

        button_take_photo.setOnClickListener {

            when {
                ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.READ_MEDIA_IMAGES"
                ) == PackageManager.PERMISSION_GRANTED -> {
                    val inte=Intent(Intent.ACTION_PICK)
                    inte.setType("image/*")
                    launcher_pickphoto.launch(inte)
                }
                else ->
                {
                    launcher_permission_gallery.launch("android.permission.READ_MEDIA_IMAGES")
                }
            }
        }
        val drawable_sp_icons = ContextCompat.getDrawable(this,R.drawable.bodyb)?.apply {
            setBounds(0, 0, 300, 300)}

            uptekst.setCompoundDrawables(null,drawable_sp_icons,null,null)
        val adapter_height_unit=object: ArrayAdapter<Any>(this,
            R.layout.vievfordropdownlist)
        {


            override fun getCount(): Int {
                return heightUnitArray.size
            }


            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val viewrt=this@NaturalTakePhoto.layoutInflater.inflate(R.layout.viueworarraydapter,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                val arrowdown=viewrt.findViewById<ImageView>(R.id.arrowforspinner)
                val arrowicon= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.arrowderty),60,60)
                arrowdown.setImageBitmap(arrowicon)
                textr.text=heightUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.heigtcm),50,50)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.heightinch),50,50)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {


                val viewrt=this@NaturalTakePhoto.layoutInflater.inflate(R.layout.vievfordropdownlist,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                textr.text=heightUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.heigtcm),50,50)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.heightinch),50,50)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


        }


        val adapter_weight_unit=object: ArrayAdapter<Any>(this,
            R.layout.vievfordropdownlist)
        {


            override fun getCount(): Int {
                return weighttUnitArray.size
            }


            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val viewrt=this@NaturalTakePhoto.layoutInflater.inflate(R.layout.viueworarraydapter,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                val arrowdown=viewrt.findViewById<ImageView>(R.id.arrowforspinner)
                val arrowicon= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.arrowderty),60,60)
                arrowdown.setImageBitmap(arrowicon)
                textr.text=weighttUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.kgwei),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.lbswei),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {


                val viewrt=this@NaturalTakePhoto.layoutInflater.inflate(R.layout.vievfordropdownlist,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                textr.text=weighttUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.kgwei),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.lbswei),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


        }

        val adapter_wgender=object: ArrayAdapter<Any>(this,
            R.layout.vievfordropdownlist)
        {


            override fun getCount(): Int {
                return genderArray.size
            }


            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val viewrt=this@NaturalTakePhoto.layoutInflater.inflate(R.layout.viueworarraydapter,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                val arrowdown=viewrt.findViewById<ImageView>(R.id.arrowforspinner)
                val arrowicon= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.arrowderty),60,60)
                arrowdown.setImageBitmap(arrowicon)
                textr.text=genderArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.male),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.female),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {


                val viewrt=this@NaturalTakePhoto.layoutInflater.inflate(R.layout.vievfordropdownlist,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                textr.text=genderArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.male),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@NaturalTakePhoto.resources,R.drawable.female),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


        }



        spinnerHeightUnits.adapter=adapter_height_unit
        spinnerWeightUnits.adapter=adapter_weight_unit
        spinnerGender.adapter=adapter_wgender

        var lastHeightUnit=0
        var lastWeightUnit=0


        spinnerHeightUnits.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {


                try{
                    val actualHeight=heightEditText.text.toString().toDouble()
                    if (position==0)
                    {
                        if (lastHeightUnit==0)
                        {
                        }
                        else
                        {
                            heightEditText.text = Editable.Factory.getInstance().newEditable((((((actualHeight*2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }
                    else if(position==1)
                    {
                        if (lastHeightUnit==1)
                        {
                        }
                        else
                        {
                            heightEditText.text = Editable.Factory.getInstance().newEditable((((((actualHeight/2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }

                }
                catch (e:Exception)
                {
                }

                    heightUnit=heightUnitArray[position]
                    lastHeightUnit=position


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerWeightUnits.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                try{
                    val actualWeight=weightEditText.text.toString().toDouble()
                    if (position==0)
                    {
                        if (lastWeightUnit==0)
                        {
                        }
                        else
                        {
                            weightEditText.text = Editable.Factory.getInstance().newEditable((((((actualWeight*0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }
                    else if(position==1)
                    {
                        if (lastWeightUnit==1)
                        {
                        }
                        else
                        {
                            weightEditText.text = Editable.Factory.getInstance().newEditable((((((actualWeight/0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }
                }
                catch (e:Exception)
                {

                }

                    weightUnit=weighttUnitArray[position]
                    lastWeightUnit=position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerGender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                gender=genderArray[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        naturaltakephotolayout.setOnClickListener {
            val focucedview=this.currentFocus
            focucedview?.clearFocus()
        }

        buttonOK.setOnClickListener {
            try {

                height=heightEditText.text.toString().toDouble()
                val intent1 = Intent(this@NaturalTakePhoto, NattyOrNotActivity::class.java)
                if (weightUnit==weighttUnitArray[0])
                {
                    weight=weightEditText.text.toString().toDouble()
                }
                else
                {
                    weight=weightEditText.text.toString().toDouble()*0.453
                }

                if (heightUnit==heightUnitArray[0])
                {
                    height=heightEditText.text.toString().toDouble()
                }
                else
                {
                    height=heightEditText.text.toString().toDouble()*2.54
                }
                intent1.data=photoUri
                intent1.putExtra("height", height).putExtra("gender",gender).putExtra("orientation",orientation).putExtra("weight",weight).putExtra("factor",(weight/(height/100).pow(2)).toString())
                startActivity(intent1)
            }
            catch (e:Exception)
            {
                val text = "Put all information and choose photo"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this, text, duration)
                toast.show()
            }

        }
    }
}