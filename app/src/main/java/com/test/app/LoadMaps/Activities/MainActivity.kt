package com.test.app.LoadMaps.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.github.angads25.toggle.LabeledSwitch
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.animation_loader
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.iv_weather_image
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.layout_empty_holder
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.layout_weather_holder
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.recyclerView
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.selectedLatitude
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.selectedLongitude
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.temparature_unit
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_cloud
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_date
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_description
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_feels_like
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_ground_level
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_gust
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_humidity
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_location
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_pressure
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_sea_level
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_temp_max
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_temp_min
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_temperature
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_weather_main
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.tv_wind_speed
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.units
import com.test.app.LoadMaps.Activities.MainActivity.Functionalities.wind_unit
import com.test.app.LoadMaps.ApiCall.WeatherApi
import com.test.app.LoadMaps.DataSets.Features
import com.test.app.LoadMaps.DataSets.PlacesSearchApi
import com.test.app.LoadMaps.DataSets.WeatherResponseApi
import com.test.app.LoadMaps.Utils.UtilClass
import com.test.app.LoadMaps.Utils.UtilClass.AppFunctions.context
import com.test.app.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    var context: Context? = null
    private val USER_LOCATION_PERMISSION_CODE = 41
    var progress_bar: ProgressBar? = null
    var util: UtilClass? = null

    var activity: Activity? = null


    var etSearchPlaces:EditText? = null
    var placesAdapter:PlacesAdapter? = null

    //var btnSearch: Button? = null
    var btnUnitSwitch: LabeledSwitch? = null
    //var btnImperial: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        activity = this@MainActivity
        UtilClass.AppFunctions.context = context

        btnUnitSwitch = findViewById(R.id.btnUnitSwitch)
        etSearchPlaces = findViewById(R.id.et_searchplace)
        recyclerView = findViewById(R.id.someRecyclerView)

        animation_loader = findViewById(R.id.animation_loader)
        animation_loader.visibility = View.GONE

        layout_weather_holder = findViewById(R.id.layout_weather_holder)
        layout_empty_holder = findViewById(R.id.layout_empty_holder)

        tv_date = findViewById(R.id.tv_date)
        tv_weather_main = findViewById(R.id.tv_weather_main)
        tv_wind_speed = findViewById(R.id.tv_wind_speed)
        iv_weather_image = findViewById(R.id.iv_weather_image)
        tv_humidity = findViewById(R.id.tv_humidity)
        tv_pressure = findViewById(R.id.tv_pressure)
        tv_cloud = findViewById(R.id.tv_cloud)
        tv_temperature = findViewById(R.id.tv_temperature)
        tv_feels_like = findViewById(R.id.tv_feels_like)
        tv_sea_level = findViewById(R.id.tv_sea_level)
        tv_ground_level = findViewById(R.id.tv_ground_level)
        tv_description = findViewById(R.id.tv_description)
        tv_gust = findViewById(R.id.tv_gust)
        tv_temp_min = findViewById(R.id.tv_temp_min)
        tv_temp_max = findViewById(R.id.tv_temp_max)
        tv_location = findViewById(R.id.tv_location)


        btnUnitSwitch?.setOnToggledListener { labeledSwitch, isOn ->
            Log.e("Test","btnToggleSwith $isOn")

            if(isOn){
                units = "imperial"
                temparature_unit = " \u2109"
                wind_unit = " Mph"

                if(selectedLatitude != 0.0 && selectedLongitude != 0.0){
                    recyclerView?.visibility = View.GONE
                    layout_weather_holder.visibility = View.VISIBLE
                    layout_empty_holder.visibility = View.GONE
                    Functionalities.getWeatherData(selectedLatitude!!, selectedLongitude!!,units)
                } else {
                    showAlertDialog("Warning","Please select Location")
                }
            } else {
                units = "metric"
                temparature_unit = " ℃"
                wind_unit = " m/s"

                if(selectedLatitude != 0.0 && selectedLongitude != 0.0){
                    recyclerView?.visibility = View.GONE
                    layout_weather_holder.visibility = View.VISIBLE
                    layout_empty_holder.visibility = View.GONE
                    Functionalities.getWeatherData(selectedLatitude!!, selectedLongitude!!,units)
                } else {
                    showAlertDialog("Warning","Please select Location")
                }
            }

        }

        if(selectedLatitude != 0.0 && selectedLongitude != 0.0){
            layout_weather_holder.visibility = View.VISIBLE
            layout_empty_holder.visibility = View.GONE
        } else {
            layout_weather_holder.visibility = View.GONE
            layout_empty_holder.visibility = View.VISIBLE
        }

//        btnImperial?.setOnClickListener {
////            recyclerView?.visibility = View.GONE
////            Functionalities.getWeatherData(placesList?.get(position)?.center?.get(1)?.toDouble()!!,placesList?.get(position)?.center?.get(0)?.toDouble()!!,"metric")
//        }

        getSupportActionBar()?.hide();

        var latitude:Double? = 0.0
        var longitude:Double? = 0.0

        //GeoCoding
//        try {
//            val geocoder = Geocoder(applicationContext, Locale.getDefault())
//            var geoResults: List<Address> = geocoder.getFromLocationName("chennai", 5)
//            while (geoResults.size == 0) {
//                geoResults = geocoder.getFromLocationName("chennai", 5)
//            }
//            if (geoResults.size > 0) {
////                val addr = geoResults[0]
////                latitude = addr.latitude
////                longitude = addr.longitude
////                Log.e("Test","Latitude = $latitude Longitude = $longitude")
//
//                for(i in 0..geoResults.size){
//                    val addr = geoResults[i]
//                    latitude = addr.latitude
//                    longitude = addr.longitude
//                    Log.e("Test","Latitude = $latitude Longitude = $longitude")
//                }
//
//            }
//        } catch (e: Exception) {
//            Log.e("Test","Exception = ${e.message}")
//        }
//
//
//        // Initializing Reverse-GeoCoding
//        val mGeocoder = Geocoder(applicationContext, Locale.getDefault())
//        var addressString= ""
//        // Reverse-Geocoding starts
//        try {
//            val addressList: List<Address> = mGeocoder.getFromLocation(latitude!!, longitude!!, 1)
//
//            // use your lat, long value here
//            if (addressList != null && addressList.isNotEmpty()) {
//                val address = addressList[0]
//                val sb = StringBuilder()
//                for (i in 0 until address.maxAddressLineIndex) {
//                    sb.append(address.getAddressLine(i)).append("\n")
//                }
//
//                // Various Parameters of an Address are appended
//                // to generate a complete Address
//                if (address.premises != null)
//                    sb.append(address.premises).append(", ")
//
//                sb.append(address.subAdminArea).append("\n")
//                sb.append(address.locality).append(", ")
//                sb.append(address.adminArea).append(", ")
//                sb.append(address.countryName).append(", ")
//                sb.append(address.postalCode)
//
//                // StringBuilder sb is converted into a string
//                // and this value is assigned to the
//                // initially declared addressString string.
//                addressString = sb.toString()
//                Log.e("Test","Address = $addressString")
//            }
//        } catch (e: IOException) {
//            Toast.makeText(applicationContext,"Unable connect to Geocoder",Toast.LENGTH_LONG).show()
//        }


        etSearchPlaces?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.e("Test","afterTextChanged Called $s")
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                Log.e("Test","beforeTextChanged Called $s")
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                Log.e("Test","onTextChanged Called $s")
                if(s.length>0){
                    recyclerView?.visibility = View.VISIBLE
                    placesSearchApiCall(s.toString())
                }
            }
        })



//        var recyclerView:RecyclerView
//        var placesAdapter:PlacesAdapter
//        recyclerView = findViewById(R.id.someRecyclerView)
//        placesAdapter = PlacesAdapter(getActivity)
//        recyclerView.setAdapter(placesAdapter)


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()

    }

    fun placesSearchApiCall(searchword:String){
        //Places Search Api

        var searchWord = searchword

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(PlacesSearchUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        animation_loader.visibility = View.VISIBLE
        val service = retrofit.create(WeatherApi::class.java)
        val call = service.getPlaces("$searchWord.json", PlacesSearchAccessToken)
        call.enqueue(object : Callback<PlacesSearchApi> {
            override fun onResponse(call: Call<PlacesSearchApi>, response: Response<PlacesSearchApi>) {
                if (response.isSuccessful) {
                    animation_loader.visibility = View.GONE
                    val searchResponse = response.body()!!
//                    Log.e("Test","Search Places Api Response size = ${searchResponse.features?.size}");
//                    Log.e("Test","Search Places Api PlaceName = ${searchResponse.features?.get(0)?.place_name} " +
//                            "latitude and longitude = ${searchResponse.features?.get(0)?.center?.get(0)}   ${searchResponse.features?.get(0)?.center?.get(1)}");


                    placesAdapter = PlacesAdapter(searchResponse.features, activity as MainActivity)
                    recyclerView?.layoutManager = LinearLayoutManager(context)
                    recyclerView?.setAdapter(placesAdapter)

                } else {
                    animation_loader.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<PlacesSearchApi>, t: Throwable) {
                animation_loader.visibility = View.GONE
                Log.e("Test","Search Places Api OnFailure Error= "+t.message);
            }
        })
    }

    class PlacesAdapter(placesList:ArrayList<Features>?,activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var placesList = placesList
        var activity = activity

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View
            return if (viewType == 2) {
                view = LayoutInflater.from(activity)
                    .inflate(R.layout.layoutplacesearch, parent, false)
                ViewHolderHome(view)
            } else {
                view = LayoutInflater.from(activity).inflate(R.layout.layoutempty, parent, false)
                ViewHolderEmpty(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder.itemViewType == 2) {
                val holder1 = holder as ViewHolderHome

                Log.e("Test","PlacesSearch onBindViewHolder Called")

                holder1.mTvPlacename.setText(placesList?.get(position)?.place_name)
                holder1.mLayoutPlaceNameHolder.setOnClickListener { v: View? ->
                    Log.e("Test","mLayoutPlaceNameHolder Clicked Called")
                    Log.e("Test","latitude ${placesList?.get(position)?.center?.get(1)} " +
                            " longitude ${placesList?.get(position)?.center?.get(0)}")
                    recyclerView?.visibility = View.GONE
                    layout_weather_holder.visibility = View.VISIBLE
                    layout_empty_holder.visibility = View.GONE
                    Functionalities.getWeatherData(placesList?.get(position)?.center?.get(1)?.toDouble()!!,placesList?.get(position)?.center?.get(0)?.toDouble()!!,
                        units)
                    selectedLatitude = placesList?.get(position)?.center?.get(1)?.toDouble()!!
                    selectedLongitude = placesList?.get(position)?.center?.get(0)?.toDouble()!!
                }

            } else {
                val viewHolderEmpty = holder as ViewHolderEmpty
                viewHolderEmpty.tv_MenuListEmpty.setText("There is no place found")
            }
        }

        override fun getItemCount(): Int {
            return if (placesList != null) {
                if (placesList!!.size > 0) {
                    placesList!!.size
                } else {
                    1
                }
            } else {
                1
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (placesList != null) {
                if (placesList!!.size > 0) {
                    2
                } else {
                    1
                }
            } else {
                1
            }
        }

        private inner class ViewHolderHome internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            var mTvPlacename: TextView
            var mLayoutPlaceNameHolder: LinearLayout

            init {
                mTvPlacename = itemView.findViewById<TextView>(R.id.tv_placename)
                mLayoutPlaceNameHolder = itemView.findViewById<LinearLayout>(R.id.layout_searchplace)
            }
        }

        private inner class ViewHolderEmpty internal constructor(view: View) :
            RecyclerView.ViewHolder(view) {
            var tv_MenuListEmpty: TextView

            init {
                tv_MenuListEmpty = view.findViewById<TextView>(R.id.tv_empty)
            }
        }
    }

    object Functionalities{

        var recyclerView:RecyclerView? = null
        lateinit var tv_date:TextView
        lateinit var tv_weather_main:TextView
        lateinit var tv_wind_speed:TextView
        lateinit var iv_weather_image:ImageView
        lateinit var tv_humidity:TextView
        lateinit var tv_pressure:TextView
        lateinit var tv_cloud:TextView
        lateinit var tv_temperature:TextView
        lateinit var tv_feels_like:TextView
        lateinit var tv_sea_level:TextView
        lateinit var tv_ground_level:TextView
        lateinit var tv_description:TextView
        lateinit var tv_gust:TextView
        lateinit var tv_temp_min:TextView
        lateinit var tv_temp_max:TextView
        lateinit var tv_location:TextView
        lateinit var animation_loader: LottieAnimationView
        lateinit var layout_weather_holder: RelativeLayout
        lateinit var layout_empty_holder: RelativeLayout

        var selectedLatitude:Double? = 0.0
        var selectedLongitude:Double? = 0.0

        var units:String = "metric"
        var temparature_unit = " \u2103"
        var wind_unit = " m/s"
        var percentage = " %"
        var hpa_unit = " hPa"


        public fun getWeatherData(latitude:Double,longitude:Double,units:String){
            //Get Weather Data
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            animation_loader.visibility = View.VISIBLE
            val service = retrofit.create(WeatherApi::class.java)
            val call = service.getCurrentWeatherData(latitude.toString(), longitude.toString(),units, AppId)
            call.enqueue(object : Callback<WeatherResponseApi> {
                override fun onResponse(call: Call<WeatherResponseApi>, response: Response<WeatherResponseApi>) {
                    if (response.code() == 200) {
                        animation_loader.visibility = View.GONE
                        val weatherResponse = response.body()!!
                        try{
                            //Get Current Date
                            val c: Date = Calendar.getInstance().getTime()
                            Log.e("Test","Current time => $c")
                            val df = SimpleDateFormat("dd MMM", Locale.getDefault())
                            val formattedDate: String = df.format(c)
                            tv_date.text = "Today," + formattedDate

                            if(weatherResponse.weather?.size!! >0){
                                tv_weather_main.text = weatherResponse.weather?.get(0)?.main
                                Glide.with(context!!).load(WeatherImageUrl+"${weatherResponse.weather.get(0).icon}@2x.png").into(iv_weather_image!!)
                                tv_description.text = weatherResponse.weather?.get(0)?.description
                            }

                            tv_humidity.text = weatherResponse.main?.humidity.toString() + percentage
                            tv_temperature.text = weatherResponse.main?.temp.toString() + temparature_unit
                            tv_feels_like.text = weatherResponse.main?.feels_like.toString() + temparature_unit
                            tv_pressure.text = weatherResponse.main?.pressure.toString() + hpa_unit
                            tv_sea_level.text = weatherResponse.main?.sea_level.toString() + hpa_unit
                            tv_ground_level.text = weatherResponse.main?.grnd_level.toString() + hpa_unit

                            tv_wind_speed.text = weatherResponse.wind?.speed.toString() + wind_unit
                            tv_gust.text = weatherResponse.wind?.gust.toString() + wind_unit //metric - meter/sec  imperial miles/hour

                            tv_cloud.text = weatherResponse.clouds?.all.toString() + percentage

                            tv_temp_min.text = weatherResponse.main?.temp_min.toString() + temparature_unit
                            tv_temp_max.text = weatherResponse.main?.temp_max.toString() + temparature_unit

                            tv_location.text = weatherResponse.name

                            Log.e("Test","Weather Api Response = "+weatherResponse.main!!.temp);
                        } catch (e:Exception){
                            animation_loader.visibility = View.GONE
                        }
                    } else {
                        animation_loader.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<WeatherResponseApi>, t: Throwable) {
                    animation_loader.visibility = View.GONE
                    Log.e("Test","Weather Api OnFailure Error= "+t.message);
                }
            })
        }
    }

    fun showAlertDialog(title:String,msg:String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(msg)
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }

//        builder.setNeutralButton("Maybe") { dialog, which ->
//            Toast.makeText(context,
//                "Maybe", Toast.LENGTH_SHORT).show()
//        }
        builder.show()
    }

    companion object{
        private var BaseUrl:String = "https://api.openweathermap.org/"
        private var PlacesSearchUrl:String = "https://api.mapbox.com/"
        private var AppId:String = "120eca041a7f88494dc871e500861ff3"
        private var PlacesSearchAccessToken:String = "pk.eyJ1IjoiYW50b255anVzdGluMTIzIiwiYSI6ImNsaDJ5bTcyajFpNXYzbnBjM2pwcmFlbHIifQ.Jr4FsfekJqwurOnzo-83KA"
        private var WeatherImageUrl = "https://openweathermap.org/img/wn/"
    }

}