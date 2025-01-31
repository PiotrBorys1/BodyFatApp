package elo.viceroy.bodyfat1

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*
import kotlin.math.log
import kotlin.math.roundToInt

class adapteroneRepMax(private val listofpoint: MutableList<elements?>, val context: OneRepMaxActivity):RecyclerView.Adapter<adapteroneRepMax.neVievHolder>() {

    var bottomstuffvisible=false
    var weight_uniit=0
    var lastweight_unit=0
    class neVievHolder(ItemView: View) : ViewHolder(ItemView){
        var ekstview =ItemView.findViewById<TextView>(R.id.textexercise)
        val mageview =ItemView.findViewById<ImageView>(R.id.photoexercise)
        var edittextReps =ItemView.findViewById<EditText>(R.id.edittextreps)
        val edittextWeight =ItemView.findViewById<EditText>(R.id.edittextweight)
        var buttoncalc =ItemView.findViewById<Button>(R.id.Calculatereps)
        val textresult =ItemView.findViewById<TextView>(R.id.textresultreps)
        val linearLayut=ItemView.findViewById<LinearLayout>(R.id.linearlayoutreps)
        val spinneronerepmweight=ItemView.findViewById<Spinner>(R.id.spinneronerepmweight)
        init {
            edittextReps.isVisible=false
            edittextWeight.isVisible=false
            buttoncalc.isVisible=false
            textresult.isVisible=false
            edittextWeight.setImeOptions(EditorInfo.IME_ACTION_DONE)
            linearLayut.setBackgroundResource(R.drawable.framevector)
            spinneronerepmweight.isVisible=false



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): neVievHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_one_rep_max, parent, false)

        return neVievHolder(view)
    }

    override fun getItemCount()= listofpoint.size

    override fun onBindViewHolder(holder: neVievHolder, position: Int) {


        holder.spinneronerepmweight.adapter=context.adapter_weight_unit
        holder.spinneronerepmweight.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                try{
                    if (position==0)
                    {
                        weight_uniit=0
                        if (lastweight_unit==0)
                        {

                        }
                        else
                        {

                            holder.edittextWeight.text= Editable.Factory.getInstance().newEditable(((((((holder.edittextWeight.text.toString().toDouble())*0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))

                        }
                        lastweight_unit=0
                    }
                    else {
                        weight_uniit=1
                        if (lastweight_unit==0)
                        {

                            holder.edittextWeight.text= Editable.Factory.getInstance().newEditable(((((((holder.edittextWeight.text.toString().toDouble())/0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }
                        else
                        {

                        }
                        lastweight_unit=1
                    }

                }
                catch(e:Exception)
                {

                }



                    weight_uniit=position
                    lastweight_unit=position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        holder.ekstview.text=listofpoint[position]!!.tekst

        holder.ekstview.setCompoundDrawables(null,null,null,context.resources.getDrawable(R.drawable.arrowdown,null).apply {
            setBounds(0, 0, 100, 100)
        })
        holder.ekstview.setOnClickListener {
            if (!bottomstuffvisible)
            {
                holder.edittextReps.isVisible=true
                holder.edittextWeight.isVisible=true
                holder.buttoncalc.isVisible=true
                holder.textresult.isVisible=true
                holder.spinneronerepmweight.isVisible=true
                holder.ekstview.setCompoundDrawables(null,null,null,context.resources.getDrawable(R.drawable.arrowup,null).apply {
                    setBounds(0, 0, 100, 100)
                })
                bottomstuffvisible=true
                Thread.sleep(100)
                holder.linearLayut.setBackgroundResource(R.drawable.framevector)
            }
            else
            {
                holder.edittextReps.isVisible=false
                holder.edittextWeight.isVisible=false
                holder.buttoncalc.isVisible=false
                holder.textresult.isVisible=false
                holder.spinneronerepmweight.isVisible=false
                holder.ekstview.setCompoundDrawables(null,null,null,context.resources.getDrawable(R.drawable.arrowdown,null).apply {
                    setBounds(0, 0, 100, 100)
                })
                bottomstuffvisible=false
                Thread.sleep(100)
                holder.linearLayut.setBackgroundResource(R.drawable.framevector)
            }
        }
        /* holder.mageview.scaleType=ImageView.ScaleType.CENTER*/
        holder.mageview.setImageBitmap(listofpoint[position]!!.image)
        holder.linearLayut.setOnClickListener {
            val focucedview=context.currentFocus
            focucedview?.clearFocus()
        }
        holder.buttoncalc.setOnClickListener {

            try{
                var weight:Double
                if (weight_uniit==0)
                {
                    weight=holder.edittextWeight.text.toString().toDouble()
                }
                else
                {
                    weight=holder.edittextWeight.text.toString().toDouble()*0.453
                }

                val reps=holder.edittextReps.text.toString().toDouble()

                var result=0.0
                if (reps>0) {
                    if (reps<10.0) {
                         result = ((((log(reps, 2.0) * 0.080 * weight + weight) * 10.0).toInt()
                            .toDouble()) / 10.0)
                    }
                    else
                    {
                        result = ((((log(reps, 2.0) * 0.080 * weight+ (reps/10.0)*0.05*weight + weight) * 10.0).toInt()
                            .toDouble()) / 10.0)
                    }
                    holder.textresult.text = "One rep: " + result.toString()+" kg\n       "+(((result/0.453)*10.0).toInt().toDouble()/10.0).toString()+" lbs"
                }
                else
                {
                    val text = "Reps value must be grater than 0"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }

            }
            catch(e:Exception)
            {

                val text = "Put all information"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }


    }

}