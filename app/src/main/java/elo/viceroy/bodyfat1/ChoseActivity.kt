package elo.viceroy.bodyfat1

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class ChoseActivity: AppCompatActivity() {

    val fulllistOFButtons=mutableListOf("BMI","Body Fat Estimation","Calorie Burn","Calorie Need", "Check Natty","One Rep Max")
    val listOfButtons= mutableListOf("BMI","Body Fat Estimation","Calorie Burn","Calorie Need", "Check Natty","One Rep Max")
    val listofViewholders=mutableListOf<RecyclerView.ViewHolder>()
    val listOfActivities=mutableListOf<Class<*>>(BmiActivity::class.java,StartActivity::class.java,BurnCalorieActivity::class.java,CalorieNeedActivity::class.java,InstructionNattyAcivity::class.java,OneRepMaxActivity::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chose_activity)
        val recyclerForButtons=findViewById<RecyclerView>(R.id.chosebuttons)
        val text_chose=findViewById<TextView>(R.id.tekstChoose)
        val calcDrawabl= ContextCompat.getDrawable(this,R.drawable.calculator)?.apply {
            setBounds(0, 0, 340, 200)
        }


        text_chose.setCompoundDrawables(null, calcDrawabl,null,null)


        recyclerForButtons.layoutManager= LinearLayoutManager(this)

        recyclerForButtons.adapter =adapterForChoseButtons(listOfButtons, this@ChoseActivity, listOfActivities)
        /*recyclerForButtons.itemAnimator=ChooseRecyclerAnimator(this)*/


    }
}