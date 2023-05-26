package com.test.app.LoadMaps.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.test.app.R
import java.text.ParseException


class MainActivity2 : AppCompatActivity() {
    //var blurLayout:BlurLayout? = null

//    lateinit var tv_date:TextView
//    lateinit var tv_wind_speed:TextView
//    lateinit var iv_weather_image:ImageView
//    lateinit var tv_humidity:ImageView
//    lateinit var tv_pressure:ImageView
//    lateinit var tv_cloud:ImageView
//    lateinit var tv_temperature:ImageView
//    lateinit var tv_feels_like:ImageView
//    lateinit var tv_sea_level:ImageView
//    lateinit var tv_ground_level:ImageView
//    lateinit var tv_description:ImageView
//    lateinit var tv_gust:ImageView
//    lateinit var tv_sunrise:ImageView
//    lateinit var tv_sunset:ImageView
//    lateinit var tv_timezone:ImageView

    lateinit var animationLoader: LottieAnimationView


    var isSwitchOn = false
    //lateinit var animationSwitch: LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        getSupportActionBar()?.hide();

        animationLoader = findViewById(R.id.animation_loader)

        //animationSwitch = findViewById(R.id.animation_switch)
//        animationSwitch.setSpeed(3f); //tis will play the animation 3 times faster
//
//        animationSwitch.setOnClickListener(View.OnClickListener {
//            isSwitchOn = if (isSwitchOn) {
//                animationSwitch.setMinAndMaxProgress(
//                    0.5f,
//                    1.0f
//                ) // this will play the Switch OFF animation
//                animationSwitch.playAnimation()
//                false
//
//            } else {
//                animationSwitch.setMinAndMaxProgress(
//                    0.0f,
//                    0.5f
//                ) // this will play the Switch ON animation
//                animationSwitch.playAnimation()
//                true
//            }
//        })


        // Custom animation speed or duration.
        // Custom animation speed or duration.
//        val animator = ValueAnimator.ofFloat(0f, 1f)
//        animator
//            .addUpdateListener { animation: ValueAnimator ->
//                animationLoader
//                    .setProgress(
//                        animation
//                            .animatedValue as Float
//                    )
//            }
//        animator.start()

        // Declaring the animation view
        // Declaring the animation view
//        val animationView = findViewById<LottieAnimationView>(R.id.animation_loader)
//        animationView
//            .addAnimatorUpdateListener { animation: ValueAnimator? -> }
//        animationView
//            .playAnimation()
//
//        if (animationView.isAnimating) {
//            // Do something.
//        }


//        tv_date = findViewById(R.id.tv_date)
//        tv_wind_speed = findViewById(R.id.tv_wind_speed)
//        iv_weather_image = findViewById(R.id.iv_weather_image)
//        tv_humidity = findViewById(R.id.tv_humidity)
//        tv_pressure = findViewById(R.id.tv_pressure)
//        tv_cloud = findViewById(R.id.tv_cloud)
//        tv_temperature = findViewById(R.id.tv_temperature)
//        tv_feels_like = findViewById(R.id.tv_feels_like)
//        tv_sea_level = findViewById(R.id.tv_sea_level)
//        tv_ground_level = findViewById(R.id.tv_ground_level)
//        tv_description = findViewById(R.id.tv_description)
//        tv_gust = findViewById(R.id.tv_gust)
//        tv_sunrise = findViewById(R.id.tv_sunrise)
//        tv_sunset = findViewById(R.id.tv_sunset)
//        tv_timezone = findViewById(R.id.tv_timezone)


        // milliseconds
        // milliseconds
        val milliSec: Long = 1684110603

        // Creating date format

        // Creating date format
        try {

//            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm:ss a")
//            val currentDateAndTime = sdf.format(Date(1684242467000L))
//            Log.e("Test","CurrentDateAndTime = $currentDateAndTime")
//
//
//            var dateStr = Date(1684242467000L).toString()
//            Log.e("Test", "GetTime 0  = $dateStr")
//            dateStr = currentDateAndTime

//            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            formatter.timeZone = TimeZone.getTimeZone("UTC")
//            val value = formatter.parse(Date(1684143581000L).toString())
//
//            dateStr = "Jul 16, 2013 12:08:59 AM"
//            dateStr = value

//            val df = SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.ENGLISH)
//            df.timeZone = TimeZone.getTimeZone("UTC")
//            val date = df.parse(dateStr)
//            df.timeZone = TimeZone.getDefault()
//            val formattedDate = df.format(date)
//            Log.e("Test", "GetTime - UTC = $formattedDate")
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("Test", "Exception = ${e.message}")
        }

        //blurLayout = findViewById(R.id.blurLayout);

    }

    override fun onStart() {
        //blurLayout?.startBlur();
        super.onStart()
    }

    override fun onStop() {
        //blurLayout?.pauseBlur();
        super.onStop()
    }
}
