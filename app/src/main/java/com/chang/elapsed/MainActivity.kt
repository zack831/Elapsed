package com.chang.elapsed

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var yearsTextView: TextView
    private lateinit var monthsTextView: TextView
    private lateinit var daysTextView: TextView
    private lateinit var hoursTextView: TextView
    private lateinit var minutesTextView: TextView
    private lateinit var secondsTextView: TextView
    private lateinit var targetDateTime: Calendar
    private lateinit var yearsTextViewTextView: TextView
    private lateinit var monthsTextViewText: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    // Specify the target start date and time here
    private val startDateTimeString = "2024-06-06 18:39:00" // Example: June 14, 2024, 00:29:00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Ensure you have activity_main.xml with all six TextViews

        yearsTextView = findViewById(R.id.yearsTextView)
        monthsTextView = findViewById(R.id.monthsTextView)
        daysTextView = findViewById(R.id.daysTextView)
        hoursTextView = findViewById(R.id.hoursTextView)
        minutesTextView = findViewById(R.id.minutesTextView)
        secondsTextView = findViewById(R.id.secondsTextView)
        yearsTextViewTextView = findViewById(R.id.yearsTextViewText)
        monthsTextViewText = findViewById(R.id.monthsTextViewText)

        try {
            val startDate = dateFormat.parse(startDateTimeString)
            targetDateTime = Calendar.getInstance()
            targetDateTime.time = startDate!!
        } catch (e: Exception) {
            yearsTextView.text = "Invalid"
            monthsTextView.text = "Date"
            daysTextView.text = "Format"
            hoursTextView.text = ""
            minutesTextView.text = ""
            secondsTextView.text = ""
            return
        }

        startTimer()
    }

    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                val currentTime = Calendar.getInstance()
                val differenceInMillis = currentTime.timeInMillis - targetDateTime.timeInMillis

                val years = TimeUnit.MILLISECONDS.toDays(differenceInMillis) / 365
                val remainingMillisAfterYears = differenceInMillis - TimeUnit.DAYS.toMillis(years * 365)
                val months = (TimeUnit.MILLISECONDS.toDays(remainingMillisAfterYears) / 30).toInt() // Approximate months
                val remainingMillisAfterMonths = remainingMillisAfterYears - TimeUnit.DAYS.toMillis(months.toLong() * 30)
                val days = TimeUnit.MILLISECONDS.toDays(remainingMillisAfterMonths)
                val remainingMillisAfterDays = remainingMillisAfterMonths - TimeUnit.DAYS.toMillis(days)
                val hours = TimeUnit.MILLISECONDS.toHours(remainingMillisAfterDays)
                val remainingMillisAfterHours = remainingMillisAfterDays - TimeUnit.HOURS.toMillis(hours)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillisAfterHours)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(differenceInMillis) % 60

                if (years > 0) {
                    yearsTextView.text = String.format("%d", years)
                    yearsTextView.visibility = View.VISIBLE
                    yearsTextView.visibility = View.VISIBLE
                } else {
                    yearsTextView.visibility = View.GONE
                    yearsTextViewTextView.visibility = View.GONE
                }

                if (months > 0) {
                    monthsTextView.text = String.format("%d", months)
                    monthsTextView.visibility = View.VISIBLE
                    monthsTextViewText.visibility = View.VISIBLE
                } else {
                    monthsTextView.visibility = View.GONE
                    monthsTextView.visibility = View.GONE
                }

                daysTextView.text = String.format("%d", days)
                hoursTextView.text = String.format("%d", hours)
                minutesTextView.text = String.format("%d", minutes)
                secondsTextView.text = String.format("%d", seconds)

                handler.postDelayed(this, 1000) // Update every 1 second
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Prevent memory leaks
    }
}