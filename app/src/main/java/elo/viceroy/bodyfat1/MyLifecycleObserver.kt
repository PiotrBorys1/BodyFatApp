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
class MyLifecycleObserver(private val registry : ActivityResultRegistry, private val activity1: MainActivity)
    : DefaultLifecycleObserver {

    lateinit var launcher_pickPhoto:ActivityResultLauncher<Intent>
    lateinit var launcher_pickPhoto_side:ActivityResultLauncher<Intent>
    lateinit var launcher_start_for_activity_result:ActivityResultLauncher<Uri>
    lateinit var launcher_start_for_activity_result_side:ActivityResultLauncher<Uri>
    lateinit var launcher_permission_camera:ActivityResultLauncher<String>
    lateinit var launcher_permission_images_pick:ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        launcher_pickPhoto=
            registry.register("1",owner,ActivityResultContracts.StartActivityForResult())
            {
                if (owner is MainActivity) {
                    if (it.data?.data != null) {
                        val index = it.data?.data.toString().takeLast(10).toLong()
                        val contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            index
                        )
                        val cursor = owner.contentResolver.query(
                            contentUri,
                            arrayOf(MediaStore.Images.Media.ORIENTATION),
                            null,
                            null,
                            null
                        )
                        if (cursor != null) {
                            cursor.moveToLast()
                            owner.orientation_front =
                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION))
                            cursor.close()
                            owner.uri_front_photo = it.data?.data!!
                            owner.sendmessagetoHandler(1)


                        }
                    }
                }
            }


        launcher_pickPhoto_side=registry.register("2",owner,ActivityResultContracts.StartActivityForResult())
        {if (owner is MainActivity) {
            if (it.data?.data != null) {
                owner.id_side = it.data?.data.toString().takeLast(10).toLong()
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    owner.id_side
                )
                val cursor = owner.contentResolver.query(
                    contentUri,
                    arrayOf(MediaStore.Images.Media.ORIENTATION),
                    null,
                    null,
                    null
                )
                if (cursor != null) {
                    cursor.moveToLast()
                    owner.orientation_side =
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION))
                    cursor.close()
                    owner.sendmessagetoHandler(2)

                }
            }
        }
        }


        launcher_start_for_activity_result=registry.register("3",owner,ActivityResultContracts.TakePicture())
        {if (owner is MainActivity) {
            if (it) {
                try {
                    sleep(100)
                        val cursor =owner.contentResolver.query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            arrayOf(
                                MediaStore.Images.Media._ID,
                                MediaStore.Images.Media.ORIENTATION
                            ),
                            null,
                            null,
                            null
                        )
                        if (cursor != null) {
                            cursor.moveToLast()
                            val id =
                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                            owner.orientation_front =
                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION))
                            cursor.close()
                            owner.uri_front_photo =
                                ContentUris.withAppendedId(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    id
                                )



                            owner.sendmessagetoHandler(1)
                        }

                }
                catch (e:Exception)
                {

                    Toast.makeText(owner,"Error, try again",Toast.LENGTH_SHORT).show()

                }
            }
        }
        }


        launcher_start_for_activity_result_side=registry.register("4",owner,ActivityResultContracts.TakePicture())
        {if (owner is MainActivity) {
            if (it) {
                try {
                    sleep(100)
                        val cursor1 = owner.contentResolver.query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            arrayOf(
                                MediaStore.Images.Media._ID,
                                MediaStore.Images.Media.ORIENTATION
                            ),
                            null,
                            null,
                            null
                        )
                        if (cursor1 != null) {
                            cursor1.moveToLast()
                            owner.id_side =
                                cursor1.getLong(cursor1.getColumnIndex(MediaStore.Images.Media._ID))
                            owner.orientation_side =
                                cursor1.getInt(cursor1.getColumnIndex(MediaStore.Images.Media.ORIENTATION))
                            cursor1.close()





                            owner.sendmessagetoHandler(2)




                        }


                } catch (e: Exception) {
                    Toast.makeText(owner, "Error, try again", Toast.LENGTH_SHORT)
                        .show()
                }


            }
        }
        }



        launcher_permission_camera=registry.register("5",owner,ActivityResultContracts.RequestPermission())
        {
            if (it==true)
            {
                if (owner is  MainActivity) {
                    owner.sendmessagetoHandler(3)
                }
            }
        }

        launcher_permission_images_pick=registry.register("6",owner,ActivityResultContracts.RequestPermission())
        {
            if (it==true)
            {
                if (owner is  MainActivity) {
                    owner.sendmessagetoHandler(4)
                }
            }
        }
    }

    fun launcher_pickPhoto()
    {
        when {
            ContextCompat.checkSelfPermission(
                activity1,
                "android.permission.READ_MEDIA_IMAGES"
            ) == PackageManager.PERMISSION_GRANTED -> {
                val inte=Intent(Intent.ACTION_PICK)
                inte.setType("image/*")
                launcher_pickPhoto.launch(inte)


            }

            else -> {
                launcher_permission_images_pick.launch("android.permission.READ_MEDIA_IMAGES")
            }
        }

    }
    fun launcher_pickPhoto_side()
    {
        when {
            ContextCompat.checkSelfPermission(
                activity1,
                "android.permission.READ_MEDIA_IMAGES"
            ) == PackageManager.PERMISSION_GRANTED -> {
                val inte=Intent(Intent.ACTION_PICK)
                inte.setType("image/*")
                launcher_pickPhoto_side.launch(inte)


            }

            else -> {
                launcher_permission_images_pick.launch("android.permission.READ_MEDIA_IMAGES")
            }
        }

    }

    fun launcher_start_for_activity_result()
    {
        when {
            ContextCompat.checkSelfPermission(
                activity1,
                "android.permission.CAMERA"
            ) == PackageManager.PERMISSION_GRANTED -> {
                activity1.create_new_photo_row()

                launcher_start_for_activity_result.launch(activity1.take_uri_photo)

            }

            else -> {
                launcher_permission_camera.launch("android.permission.CAMERA")
            }
        }
    }

    fun launcher_start_for_activity_result_side()
    {
        when {
            ContextCompat.checkSelfPermission(
                activity1,
                "android.permission.CAMERA"
            ) == PackageManager.PERMISSION_GRANTED -> {
                activity1.create_new_photo_row_side()
                launcher_start_for_activity_result_side.launch(activity1.take_uri_photo_side)

            }

            else -> {
                launcher_permission_camera.launch("android.permission.CAMERA")
            }

        }


    }


}