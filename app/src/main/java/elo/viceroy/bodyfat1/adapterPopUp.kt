package elo.viceroy.bodyfat1

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.ThumbnailUtils
import android.os.SystemClock
import android.view.*
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlin.math.pow
import kotlin.math.sqrt

class adapterPopUp(private val listofpoint: MutableList<elements>, val context: Context):RecyclerView.Adapter<adapterPopUp.neVievHolder>() {

    var basicbitmapwidth=IntArray(listofpoint.size)
    var basicbitmapheight=IntArray(listofpoint.size)
    var  doubleclick=false
    var firstclickmoment:Long=0
    var scalebig=false
    class neVievHolder(ItemView: View) : ViewHolder(ItemView){
        var ekstview =ItemView.findViewById<TextView>(R.id.tekstrecycler1)
        val mageview =ItemView.findViewById<ImageView>(R.id.photorecycler1)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): neVievHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerpopup, parent, false)

        return neVievHolder(view)
    }

    override fun getItemCount()= listofpoint.size

    override fun onBindViewHolder(holder: neVievHolder, @SuppressLint("RecyclerView") position: Int) {
         if (position==0) {
             holder.ekstview.textSize=50f
         }
        else
         {
             holder.ekstview.textSize=20f
         }
        holder.ekstview.text = listofpoint[position].tekst
        holder.mageview.setImageBitmap(listofpoint[position].image)
        if (listofpoint[position].image!=null)
        {
        basicbitmapwidth[position] = listofpoint[position].image!!.width
        basicbitmapheight[position] = listofpoint[position].image!!.height
        holder.mageview.setOnClickListener {
            if(!doubleclick)
            {
                doubleclick=true
                firstclickmoment=SystemClock.elapsedRealtime()
            }
            else if (SystemClock.elapsedRealtime()-firstclickmoment<900)
            {
                doubleclick=false
                if (scalebig)
                {
                    scalebig=false
                    holder.mageview.setImageBitmap(listofpoint[position].image)

                }
                else
                {
                    scalebig=true
                    holder.mageview.setImageBitmap(ThumbnailUtils.extractThumbnail(listofpoint[position].image!!,2*basicbitmapwidth[position],2*basicbitmapheight[position]))

                }
            }
            else
            {
                doubleclick=true
                firstclickmoment=SystemClock.elapsedRealtime()
            }
        }

        }

    }

}