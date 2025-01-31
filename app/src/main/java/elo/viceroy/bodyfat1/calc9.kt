package elo.viceroy.bodyfat1

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import androidx.annotation.RequiresApi

import kotlin.math.*


class calc9(private val activity:SecondActivity, private val photo: Bitmap, private val handler: Handler, private val intent1: Intent, private val reqWidth:Int, private val reqHeight:Int, private val ismenshape:Boolean, private val coef_stomach_chest:Float): Thread()  {
    var BF=0.0
    @RequiresApi(Build.VERSION_CODES.S)
    override fun run()
    {
        var i = 0
        var j = 0

        var array_for_counting_red_dominant_stomach = FloatArray(256)
        var array_for_counting_green_dominant_stomach = FloatArray(256)
        var array_for_counting_blue_dominant_stomach = FloatArray(256)

        val prop_height =
            photo.height.toFloat() / reqHeight.toFloat()
        val prop_width =
            photo.width.toFloat() / reqWidth.toFloat()
        var color_value: Int
        var red_component_front=Array(reqHeight){ IntArray(reqWidth)}
        var green_component_front=Array(reqHeight){ IntArray(reqWidth)}
        var blue_component_front=Array(reqHeight){ IntArray(reqWidth)}
        var width_pixels_for_stomach=180
        var height_pixelsfor_stomach=190

        var height_start_stomach=280
        var width_start_stomach=120
        if (!ismenshape)
        {
            height_start_stomach=290
            height_pixelsfor_stomach=120

        }
        val middle_line_abs=80
        var  height_start_stomach_1=280
        var  height_pixelsfor_stomach_1=190
        var color_array_of_stomach_red_1=Array(height_pixelsfor_stomach_1){ IntArray(width_pixels_for_stomach)}
        var color_array_of_stomach_red=Array(height_pixelsfor_stomach){ IntArray(width_pixels_for_stomach)}

        /* wyznaczanie macierzy zawierającej składowe czerwone pikseli n abrzuchu (zdjęcie z przodu) oraz macierzy do obliczania dominujących wartości poszczególych skłądowych pikseli zdje,cia z profilu oraz wykrywanie piksli stanika u kobiet*/
        while (i < reqHeight) {
            while (j < reqWidth) {
                color_value = photo.getColor(
                    (j.toFloat() * prop_width).toInt(),
                    (i.toFloat() * prop_height).toInt()
                ).toArgb() and 16777215
                red_component_front[i][j] = ((color_value and 16711680).shr(16))
                green_component_front[i][j] = ((color_value and 65280).shr(8))
                blue_component_front[i][j] = (color_value and 255)


                if ((i>=height_start_stomach && i <(height_start_stomach+height_pixelsfor_stomach)) &&
                    ((j>=width_start_stomach && j<(width_start_stomach+width_pixels_for_stomach/2))
                            || (j>=(middle_line_abs+width_start_stomach+width_pixels_for_stomach/2) && j<(width_start_stomach+width_pixels_for_stomach+middle_line_abs))) ) {
                    if (j<(width_start_stomach+width_pixels_for_stomach/2)) {
                        color_array_of_stomach_red[i - height_start_stomach] [ j - width_start_stomach] =
                            red_component_front[i][j]
                    }
                    else
                    {
                        color_array_of_stomach_red[i - height_start_stomach] [j - width_start_stomach-middle_line_abs] =
                            red_component_front[i][j]
                    }
                    array_for_counting_red_dominant_stomach[red_component_front[i][j]] += 1f
                    array_for_counting_green_dominant_stomach[green_component_front[i][j]] += 1f
                    array_for_counting_blue_dominant_stomach[blue_component_front[i][j]] += 1f
                }

                if (!ismenshape)
                {
                    if ((i>=height_start_stomach_1 && i <(height_start_stomach_1+height_pixelsfor_stomach_1)) &&
                        ((j>=width_start_stomach && j<(width_start_stomach+width_pixels_for_stomach/2))
                                || (j>=(middle_line_abs+width_start_stomach+width_pixels_for_stomach/2) && j<(width_start_stomach+width_pixels_for_stomach+middle_line_abs))) )
                    {
                        if (j<(width_start_stomach+width_pixels_for_stomach/2)) {
                            color_array_of_stomach_red_1[i - height_start_stomach_1] [ j - width_start_stomach] =
                                red_component_front[i][j]
                        }
                        else
                        {
                            color_array_of_stomach_red_1[i - height_start_stomach_1] [j - width_start_stomach-middle_line_abs] =
                                red_component_front[i][j]
                        }
                    }
                }
                j += 1
            }

            j = 0
            i += 1
        }



/*obliczanie doimujących wartości pikseli dla zdjęcia z profilu*/

        val number_of_samples_to_mean: Int = 30
        var sum_of_samples_red_stomach: Float = 0f
        var sum_of_samples_green_stomach: Float = 0f
        var sum_of_samples_blue_stomach: Float = 0f
        var sum_red_component_of_dominant_stomach = 0f
        var sum_green_component_of_dominant_stomach = 0f
        var sum_blue_component_of_dominant_stomach = 0f
        var red_component_of_dominant_stomach = 0
        var green_component_of_dominant_stomach = 0
        var blue_component_of_dominant_stomach = 0


        i=0
        j=0
        while (i <= 255 - number_of_samples_to_mean) {
            while (j < number_of_samples_to_mean) {


                sum_of_samples_red_stomach += array_for_counting_red_dominant_stomach[i + j]
                sum_of_samples_green_stomach += array_for_counting_green_dominant_stomach[i + j]
                sum_of_samples_blue_stomach += array_for_counting_blue_dominant_stomach[i + j]

                j++

            }


            array_for_counting_red_dominant_stomach[i] = sum_of_samples_red_stomach / number_of_samples_to_mean
            array_for_counting_green_dominant_stomach[i] =sum_of_samples_green_stomach / number_of_samples_to_mean
            array_for_counting_blue_dominant_stomach[i] = sum_of_samples_blue_stomach / number_of_samples_to_mean





            if (array_for_counting_red_dominant_stomach[i] > sum_red_component_of_dominant_stomach) {
                red_component_of_dominant_stomach = i + number_of_samples_to_mean / 2
                sum_red_component_of_dominant_stomach = array_for_counting_red_dominant_stomach[i]
            }

            if (array_for_counting_green_dominant_stomach[i] > sum_green_component_of_dominant_stomach) {
                green_component_of_dominant_stomach = i + number_of_samples_to_mean / 2
                sum_green_component_of_dominant_stomach = array_for_counting_green_dominant_stomach[i]
            }
            if (array_for_counting_blue_dominant_stomach[i] > sum_blue_component_of_dominant_stomach) {
                blue_component_of_dominant_stomach = i + number_of_samples_to_mean / 2
                sum_blue_component_of_dominant_stomach = array_for_counting_blue_dominant_stomach[i]
            }



            sum_of_samples_red_stomach = 0f
            sum_of_samples_green_stomach = 0f
            sum_of_samples_blue_stomach = 0f

            j = 0
            i++
        }





        /*standaryzacja wartości pikseli macierzy zawięracej składowe czerwone tych pikseli z brzucha*/
        i=0
        var max_value_stomach_red_array=IntArray(height_pixelsfor_stomach)
        while (i<height_pixelsfor_stomach)
        {
            max_value_stomach_red_array[i]=color_array_of_stomach_red[i].max()
            i++
        }
        val max_red_value_stomach=max_value_stomach_red_array.max()

    i = 0
    var max_value_stomach_red_array_1 = IntArray(height_pixelsfor_stomach_1)
        if(!ismenshape) {
            while (i < height_pixelsfor_stomach_1) {
                max_value_stomach_red_array_1[i] = color_array_of_stomach_red_1[i].max()
                i++
            }
        }
    val max_red_value_stomach_1 = max_value_stomach_red_array_1.max()

        i=0
        j=0

        if (!ismenshape) {
            while (i < height_pixelsfor_stomach_1) {
                while (j < width_pixels_for_stomach) {
                    color_array_of_stomach_red_1[i][j] =
                        ((color_array_of_stomach_red_1[i][j].toDouble() / max_red_value_stomach_1.toDouble()) * 100.0).toInt()
                    j++
                }
                i++
            }
            i = 0
            j = 0
        }
        var sum_of_dark_pixels_stomach=0
        while (i<height_pixelsfor_stomach)
        {
            while (j<width_pixels_for_stomach)
            {

                color_array_of_stomach_red[i][j]=((color_array_of_stomach_red[i][j].toDouble()/max_red_value_stomach.toDouble())*100.0).toInt()

                if (j<width_pixels_for_stomach/2) {


                    if ((red_component_front[i + height_start_stomach] [ j + width_start_stomach] - red_component_of_dominant_stomach).absoluteValue > 30 || (green_component_front[i + height_start_stomach][ j + width_start_stomach] - green_component_of_dominant_stomach).absoluteValue > 30 || (blue_component_front[i + height_start_stomach][ j + width_start_stomach] - blue_component_of_dominant_stomach).absoluteValue > 30) {
                        sum_of_dark_pixels_stomach += 1
                    }
                }
                else
                {
                    if ((red_component_front[i + height_start_stomach] [j + width_start_stomach+middle_line_abs] - red_component_of_dominant_stomach).absoluteValue > 30 || (green_component_front[i + height_start_stomach][ j + width_start_stomach+middle_line_abs] - green_component_of_dominant_stomach).absoluteValue > 30 || (blue_component_front[i + height_start_stomach][ j + width_start_stomach+middle_line_abs] - blue_component_of_dominant_stomach).absoluteValue > 30) {
                        sum_of_dark_pixels_stomach += 1
                    }
                }
                j++
            }
            j=0
            i++
        }

        val coef_darkpixels_stomach_to_not_dark=(sum_of_dark_pixels_stomach.toDouble()/(height_pixelsfor_stomach.toDouble()*width_pixels_for_stomach.toDouble()))*100.0




        /* obliczanie dyskretnej transformaty fouriera dla kolumn zawierjacych wartości czerwonej skłądowej pikseli brzucha*/
        i=0
        j=0
        val start_harm=1
        val max_harm=6
        var harm=start_harm

        var real_part_fourier=0.0
        var con_part_fourier=0.0
        var real_part_fourier_1=0.0
        var con_part_fourier_1=0.0
        var harmonics_across_photo=Array(width_pixels_for_stomach){ DoubleArray(max_harm-start_harm)}
        var harmonics_angle_across_photo=Array(width_pixels_for_stomach){ DoubleArray(max_harm-start_harm)}
        var harmonics_across_photo_1=Array(width_pixels_for_stomach){ DoubleArray(max_harm-start_harm)}
        var harmonics_angle_across_photo_1=Array(width_pixels_for_stomach){ DoubleArray(max_harm-start_harm)}

        while (harm<max_harm)
        {

            while (i<width_pixels_for_stomach)
            {
                while (j<height_pixelsfor_stomach)
                {
                    real_part_fourier+=((color_array_of_stomach_red[j][i].toDouble())*cos((harm.toDouble()*j.toDouble()*(2.0*3.1415))/(height_pixelsfor_stomach.toDouble())))
                    con_part_fourier+=((color_array_of_stomach_red[j][i].toDouble())* sin((harm.toDouble()*j.toDouble()*(2.0*3.1415))/(height_pixelsfor_stomach.toDouble())))

                    j++
                }
                harmonics_across_photo[i][harm-start_harm]=sqrt(real_part_fourier.pow(2)+con_part_fourier.pow(2))/j.toDouble()
                harmonics_angle_across_photo[i][harm-start_harm]= atan2(con_part_fourier,real_part_fourier)/j.toDouble()
                real_part_fourier=0.0
                con_part_fourier=0.0
                j=0
                i++

            }
            i=0
            harm++
        }
        i=0
        j=0
        harm=start_harm
 if (!ismenshape) {
     while (harm < max_harm) {

         while (i < width_pixels_for_stomach) {
             while (j <height_pixelsfor_stomach_1) {
                 real_part_fourier_1 += ((color_array_of_stomach_red_1[j][i].toDouble()) * cos((harm.toDouble() * j.toDouble() * (2.0 * 3.1415)) / (height_pixelsfor_stomach_1.toDouble())))
                 con_part_fourier_1 += ((color_array_of_stomach_red_1[j][i].toDouble()) * sin((harm.toDouble() * j.toDouble() * (2.0 * 3.1415)) / (height_pixelsfor_stomach_1.toDouble())))

                 j++
             }
             harmonics_across_photo_1[i][harm - start_harm] =
                 sqrt(real_part_fourier_1.pow(2) + con_part_fourier_1.pow(2)) / j.toDouble()
             harmonics_angle_across_photo_1[i][harm - start_harm] =
                 atan2(con_part_fourier_1, real_part_fourier_1) / j.toDouble()
             real_part_fourier_1 = 0.0
             con_part_fourier_1 = 0.0
             j = 0
             i++

         }
         i = 0
         harm++
     }
 }


        i=0
        j=0
        /*obliczanie autokorelacji dla każdej kolumny macierzy zawierającej wartości pikseli brzucha*/
        var max_number_of_autocooleration_samples_shift=80
        if (!ismenshape)
        {
            max_number_of_autocooleration_samples_shift=60
        }
        var start_samples_shift=20
        var corellation_samples_shift=start_samples_shift
        var autocorrelation_array_across_photo=Array(width_pixels_for_stomach){ DoubleArray(max_number_of_autocooleration_samples_shift-start_samples_shift)}
        while (corellation_samples_shift<max_number_of_autocooleration_samples_shift)
        {
            while (i<width_pixels_for_stomach)
            {
                while(j<height_pixelsfor_stomach-max_number_of_autocooleration_samples_shift)
                {
                    autocorrelation_array_across_photo[i][corellation_samples_shift-start_samples_shift]+=(color_array_of_stomach_red[j+corellation_samples_shift][i].toDouble()-color_array_of_stomach_red[j][i].toDouble()).absoluteValue
                    j++
                }
                j=0
                i++
            }
            i=0
            corellation_samples_shift++
        }


        val to_plot_fourier_transform=DoubleArray(max_harm-start_harm)
        val to_plot_fourier_transform_1=DoubleArray(max_harm-start_harm)
        i=0
        j=0
        while (i<max_harm-start_harm) {
            while (j < width_pixels_for_stomach)
            {
                to_plot_fourier_transform[i]+=harmonics_across_photo[j][i]
                if (!ismenshape) {
                    to_plot_fourier_transform_1[i] += harmonics_across_photo_1[j][i]
                }
                j++
            }
            j=0
            i++
        }
        val max_harmonics_value=to_plot_fourier_transform.max()
        var maxharmonics_number=0
        val max_harmonics_value_1=to_plot_fourier_transform_1.max()
        var maxharmonics_number_1=0

        i=0

        while (i<to_plot_fourier_transform.size)
        {
            if (to_plot_fourier_transform[i]==max_harmonics_value)
            {
                maxharmonics_number=i+start_harm
                i=to_plot_fourier_transform.size
            }

            i++
        }
        i=0
        if (!ismenshape)
        {
            while (i<to_plot_fourier_transform_1.size)
            {
                if (to_plot_fourier_transform_1[i] == max_harmonics_value_1) {
                    maxharmonics_number_1 = i + start_harm
                    i = to_plot_fourier_transform_1.size
                }

                i++
            }

        }
        val coef_max_harmonnics_to_1_harmonics=(to_plot_fourier_transform[0]/to_plot_fourier_transform[maxharmonics_number-start_harm])*100.0

        val to_plot_autocorrelation=DoubleArray(max_number_of_autocooleration_samples_shift-start_samples_shift)
        i=0
        j=0
        while (j<max_number_of_autocooleration_samples_shift-start_samples_shift) {
            while (i < width_pixels_for_stomach) {
                to_plot_autocorrelation[j]+=autocorrelation_array_across_photo[i][j]
                i++
            }
            i=0
            j++
        }

        val min_autocorrelation_value=to_plot_autocorrelation.min()
        var min_autocorrelation_samples:Int=0
        i=0
        while (i<to_plot_autocorrelation.size)
        {
            if (to_plot_autocorrelation[i]==min_autocorrelation_value)
            {
                min_autocorrelation_samples=i+start_samples_shift
                i=to_plot_autocorrelation.size
            }
            i++
        }

        val coef_min_autocorel_to_20_samples_corelation=(to_plot_autocorrelation[min_autocorrelation_samples-start_samples_shift]/to_plot_autocorrelation[0])*100.0




        if (intent1.getStringExtra("gender") == "Male") {

            if (intent1.getStringExtra("factor")!!.toDouble() <16) {

                BF = ((SystemClock.currentThreadTimeMillis().toString().takeLast(5).toDouble())/99999.0)*1.0+7.0


            }
            else if (intent1.getStringExtra("factor")!!.toDouble() <19) {

                BF = ((SystemClock.currentThreadTimeMillis().toString().takeLast(5).toDouble())/99999.0)*1.0+8.0

            }
            else if (intent1.getStringExtra("factor")!!.toDouble() < 21){
                if (((maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>45.0)))
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+5.0
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>30.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+6.5
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>15.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+8.0
                }
                else if (maxharmonics_number==4)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*0.95+9.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>40.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>20.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.5
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>7.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+11.0
                }
                else if ((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3  )
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+12.5

                }
                else
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+12.5
                }

            }
            else if (intent1.getStringExtra("factor")!!.toDouble() <25) {

                if (((maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>45.0)))
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+5.0
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>30.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+6.5
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>15.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+8.0
                }
                else if (maxharmonics_number==4)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*0.95+9.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>40.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>20.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.5
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>7.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+11.0
                }
                else if ((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3  )
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+12.5

                }
                else if (coef_stomach_chest <= -0.025)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+14.5
                }
                else if (coef_stomach_chest <= 0.005)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+15.5
                }
                else if (coef_stomach_chest <= 0.015)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+16.5
                }
                else if (coef_stomach_chest <= 0.025)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+17.5
                }
                else
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+19.0
                }



            }
            else if (intent1.getStringExtra("factor")!!.toDouble() <28) {

                if (((maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>45.0)))
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+5.0
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>30.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+6.5
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>15.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+8.0
                }
                else if (maxharmonics_number==4)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*0.95+9.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>40.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>20.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.5
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>7.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+11.0
                }
                else if ((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3  )
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+12.5

                }
                else if (coef_stomach_chest <= -0.025)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+14.5
                }
                else if (coef_stomach_chest <= 0.005)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+15.5
                }
                else if (coef_stomach_chest <= 0.015)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+16.5
                }
                else if (coef_stomach_chest <= 0.022) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+17.5
                }
                else if (coef_stomach_chest <= 0.03 ) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+19.0
                }
                else if ((coef_stomach_chest<=0.045)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+20.0
                }
                else if ((coef_stomach_chest<=0.058)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+21.0
                }
                else if ((coef_stomach_chest<=0.08)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+22.0
                }
                else if ((coef_stomach_chest<=0.095)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+23.0
                }
                else if ((coef_stomach_chest<=0.11)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+24.0
                }
                else {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+26.0
                }

            }
            else if (intent1.getStringExtra("factor")!!.toDouble() <32) {

                if (((maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>45.0)))
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+5.0
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>30.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+6.5
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>15.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+8.0
                }
                else if (maxharmonics_number==4)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*0.95+9.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>40.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>20.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.5
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>7.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+11.0
                }
                else if ((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3  )
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+12.5

                }
                else if (coef_stomach_chest <= -0.025)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+14.5
                }
                else if (coef_stomach_chest <= 0.005)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+15.5
                }
                else if (coef_stomach_chest <= 0.015)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+16.5
                }
                else if (coef_stomach_chest <= 0.022) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+17.5
                }
                else if (coef_stomach_chest <= 0.03 ) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+19.0
                }
                else if ((coef_stomach_chest<=0.045)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+20.0
                }
                else if ((coef_stomach_chest<=0.058)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+21.0
                }
                else if ((coef_stomach_chest<=0.08)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+22.0
                }
                else if ((coef_stomach_chest<=0.095)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+23.0
                }
                else if ((coef_stomach_chest<=0.11)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+24.0
                }
                else if ((coef_stomach_chest<=0.14)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+26.0
                }
                else if ((coef_stomach_chest<=0.16)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+27.5
                }
                else if ((coef_stomach_chest<=0.18)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+29.0
                }
                else if ((coef_stomach_chest<=0.2)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+30.5
                }
                else {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+33.0
                }

            }
            else  {
                if (((maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>45.0)))
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+5.0
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>30.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+6.5
                }
                else if (maxharmonics_number==4 && coef_darkpixels_stomach_to_not_dark>15.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+8.0
                }
                else if (maxharmonics_number==4)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*0.95+9.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>40.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.0
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>20.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.5
                }
                else if (((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3 ) && coef_darkpixels_stomach_to_not_dark>7.0)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+11.0
                }
                else if ((min_autocorrelation_samples>40 && min_autocorrelation_samples<68) || maxharmonics_number==3  )
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+12.5

                }
                else if (coef_stomach_chest <= -0.033)
                {
                    BF= ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.2+14.0
                }
                else if (coef_stomach_chest <= -0.014) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.3+15.2
                }
                else if (coef_stomach_chest <= 0.0 ) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+16.5
                }
                else if (coef_stomach_chest <= 0.015 ) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+18.0
                }
                else if (coef_stomach_chest <= 0.03) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+19.0
                }
                else if ((coef_stomach_chest<=0.045)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+20.0
                }
                else if ((coef_stomach_chest<=0.058)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+21.0
                }
                else if ((coef_stomach_chest<=0.08)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+22.0
                }
                else if ((coef_stomach_chest<=0.095)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+23.0
                }
                else if ((coef_stomach_chest<=0.11)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+24.0
                }
                else if ((coef_stomach_chest <=0.13)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+26.0
                }
                else if ((coef_stomach_chest <=0.15)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+28.0
                }
                else if ((coef_stomach_chest <=0.17)) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+30.0
                }
                else if ((coef_stomach_chest <=0.19))  {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*3.0+32.0
                }
                else if ((coef_stomach_chest <=0.21))
                {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+35.0
                }
                else if ((coef_stomach_chest <=0.23))
                {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+37.0
                }
                else
                {
                    BF =((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*3.0+39.0
                }


            }

        }
  else if (intent1.getStringExtra("gender") == "Female") {

            if (intent1.getStringExtra("factor")!!.toDouble() < 16) {

                BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+8.0


            }
            if (intent1.getStringExtra("factor")!!.toDouble() <18.5) {

                BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+10.0


            }
            else if (intent1.getStringExtra("factor")!!.toDouble() <20) {

                if ( maxharmonics_number_1==4) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+10.0
                }
                else if ((min_autocorrelation_samples>46 || maxharmonics_number==3 ) && coef_stomach_chest <=0.0) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.5+11.5
                }
                else if ( coef_stomach_chest <=-0.06) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+14.0
                }
                else {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+15.5
                }



            }
            else if (intent1.getStringExtra("factor")!!.toDouble() <22) {

                if ( maxharmonics_number_1==4) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+10.0
                }
                else if ((min_autocorrelation_samples>46 || maxharmonics_number==3 ) && coef_stomach_chest <=0.0) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.5+11.5
                }
                else if ( coef_stomach_chest <=-0.1) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+14.0
                }
                else if ( coef_stomach_chest <=-0.08) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+15.5
                }
                else if ( coef_stomach_chest <=-0.04) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+17.0
                }
                else  {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+18.5
                }




            }
            else if (intent1.getStringExtra("factor")!!.toDouble() < 25) {


                if ( maxharmonics_number_1==4) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+10.0
                }
                else if ((min_autocorrelation_samples>46 || maxharmonics_number==3) && coef_stomach_chest <=0.0) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.5+11.5
                }
                else if ( coef_stomach_chest <=-0.14) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+14.0
                }
                else if ( coef_stomach_chest <=-0.1) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+15.5
                }
                else if ( coef_stomach_chest <=-0.08) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+17.0
                }
                else if ( coef_stomach_chest <=-0.06) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+18.5
                }
                else if ( coef_stomach_chest <=-0.04) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+20.0
                }
                else if ( coef_stomach_chest <=-0.02) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+21.5
                }
                else if ( coef_stomach_chest <=-0.01) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+22.5
                }
                else if ( coef_stomach_chest <=0.01) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+24.5
                }
                else  {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+26.0
                }



            } else if (intent1.getStringExtra("factor")!!.toDouble() < 28) {

                if ( maxharmonics_number_1==4) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+10.0
                }
                else if ((min_autocorrelation_samples>46 || maxharmonics_number==3 ) && coef_stomach_chest <=0.0) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.5+11.5
                }
                else if ( coef_stomach_chest <=-0.18) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+15.0
                }
                else if ( coef_stomach_chest <=-0.14) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+16.5
                }
                else if ( coef_stomach_chest <=-0.1) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+18.0
                }
                else if ( coef_stomach_chest <=-0.08) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+19.0
                }
                else if ( coef_stomach_chest <=-0.06) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+20.0
                }
                else if ( coef_stomach_chest <=-0.04) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+21.5
                }
                else if ( coef_stomach_chest <=-0.02) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+23.0
                }
                else if ( coef_stomach_chest <=-0.01) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+24.0
                }
                else if ( coef_stomach_chest <=0.01) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+25.0
                }
                else if ( coef_stomach_chest <=0.03) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+26.0
                }
                else if ( coef_stomach_chest <=0.05) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+27.0
                }
                else  {
                    BF =((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+28.0
                }




            }
            else if (intent1.getStringExtra("factor")!!.toDouble() <30) {

                if ( coef_stomach_chest <=-0.15) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+19.0
                }
                else if ( coef_stomach_chest <=-0.13) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+20.0
                }
                else if ( coef_stomach_chest <=-0.11) {
                    BF =  ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+21.0
                }
                else if ( coef_stomach_chest <=-0.09) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+22.0
                }
                else if ( coef_stomach_chest <=-0.07) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+23.0
                }
                else if ( coef_stomach_chest <=-0.055) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+24.0
                }
                else if ( coef_stomach_chest <=-0.035) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+25.0
                }
                else if ( coef_stomach_chest <=-0.02) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+26.0
                }
                else if ( coef_stomach_chest <=-0.01) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+27.0
                }
                else if ( coef_stomach_chest <=0.0) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+28.0
                }
                else if ( coef_stomach_chest <=0.03) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+29.0
                }
                else if ( coef_stomach_chest <=0.05) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+30.0
                }
                else if ( coef_stomach_chest <=0.085) {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+31.0
                }
                else  {
                    BF =((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+33.0
                }



            }
     else
            {
                 if ( coef_stomach_chest <=0.0) {
                BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+25.0
            }
            else if ( coef_stomach_chest <=0.02) {
                BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+26.0
            }
            else if ( coef_stomach_chest <=0.045) {
                BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+27.0
            }
            else if ( coef_stomach_chest <=0.06) {
                BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+28.0
            }
            else if ( coef_stomach_chest <=0.08) {
                BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.0+29.0
            }

                else if (  coef_stomach_chest <=0.09) {
                    BF =((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+30.0
                }
                else if (  coef_stomach_chest <=0.1)
                {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+32.0
                }
                else if (  coef_stomach_chest <=0.115)
                {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+33.5
                }
                else if ( coef_stomach_chest <=0.13)
                {
                    BF =((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+35.0
                }
                else if ( coef_stomach_chest<=0.16)
                {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+37.0
                }
                else if ( coef_stomach_chest<=0.18)
                {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*1.5+38.5
                }
                else if ( coef_stomach_chest<=0.2)
                {
                    BF = ((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*2.0+40.0
                }
                else
                {
                    BF =((SystemClock.elapsedRealtime().toString().takeLast(5).toDouble())/99999.0)*3.0+42.0
                }

            }
        }

        activity.body_fat=BF
        activity.max_harm=min_autocorrelation_samples
        activity.dar_to_light_ixels=coef_stomach_chest.toDouble()
        activity.gender_er=intent1.getStringExtra("gender")!!
        val message= Message.obtain( handler, 1,1,1)
        handler.sendMessage(message)


    }
}