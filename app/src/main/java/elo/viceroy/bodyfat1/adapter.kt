package elo.viceroy.bodyfat1

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class adapter(private val listofpoint: MutableList<elements>, val context: Context):RecyclerView.Adapter<adapter.neVievHolder>() {
     class neVievHolder(ItemView: View) : ViewHolder(ItemView){
         var ekstview =ItemView.findViewById<TextView>(R.id.tekstrecycler)
         val mageview =ItemView.findViewById<ImageView>(R.id.photorecycler)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): neVievHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.torecyclerview, parent, false)

        return neVievHolder(view)
    }

    override fun getItemCount()= listofpoint.size

    override fun onBindViewHolder(holder: neVievHolder, position: Int) {

        holder.ekstview.text=listofpoint[position].tekst
       /* holder.mageview.scaleType=ImageView.ScaleType.CENTER*/
        holder.mageview.setImageBitmap(listofpoint[position].image)

    }

}