package elo.viceroy.bodyfat1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class adapterForChoseButtons(private val listofbuttons: MutableList<String>,private val context:AppCompatActivity,private val list_of_activities:MutableList<Class<*>>):
    RecyclerView.Adapter<adapterForChoseButtons.myViewHolder>() {

    class outlineprovider(var context: AppCompatActivity): ViewOutlineProvider()
    {
        override fun getOutline(p0: View?, p1: Outline?) {
            try {
                p0!!.background.getOutline(p1!!)
                var rect=Rect()
                var rectpopulated=p1.getRect(rect)
                if (rectpopulated) {
                    rect.top=rect.top+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,9f,context.resources.displayMetrics).toInt()
                    rect.bottom=rect.bottom+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12f,context.resources.displayMetrics).toInt()
                    rect.left=rect.left+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,9f,context.resources.displayMetrics).toInt()
                    rect.right=rect.right+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12f,context.resources.displayMetrics).toInt()

                    p1.setRoundRect(rect,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,9f,context.resources.displayMetrics))
                    p1.alpha=0.75f

                }
            }
            catch (e:Exception)
            {

            }

        }
    }


    class myViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {
        val button=ItemView.findViewById<Button>(R.id.ChooseButton)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterForChoseButtons.myViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.buttons_for_chosen_activity, parent, false)

        return myViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.button.text=listofbuttons[position]
        val animation= AnimationUtils.loadAnimation(context,R.anim.anim_fade_in)
        animation.duration=(position*500+500).toLong()
        holder.button.outlineProvider=outlineprovider(context)
        holder.button.startAnimation(animation)
        holder.button.setOnClickListener {
        context.startActivity(Intent(context, list_of_activities[position]))

        }

    }

    override fun getItemCount(): Int {
        return listofbuttons.size
    }
}