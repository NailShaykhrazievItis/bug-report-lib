package com.itis.testhelper.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itis.testhelper.R

open class BackgroundActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var lastShakeTime: Long = 0
    private var fabButton: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionButton()
        initListeners()
        initFragmentCallbacks()
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.also {
            val accelerometer = it.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
            it.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = findViewById<ViewGroup>(android.R.id.content)
        val res = findViewAt(view, ev.rawX.toInt(), ev.rawY.toInt())
        res?.also {
            try {
                val nameEntry = resources.getResourceEntryName(it.id)
                Log.e("View", nameEntry)
            } catch (ex: Resources.NotFoundException) {
                Log.e("View", "Not Found")
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    @SuppressLint("RestrictedApi")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastShakeTime > MIN_TIME_BETWEEN_SHAKES) {
                val x = event.values[0]
                val acceleration = Math.abs(x)
                Log.d("Acceleration", "$acceleration m/s^2")
                if (acceleration > SHAKE_THRESHOLD) {
                    lastShakeTime = currentTime
                    fabButton?.also {
                        val view = findViewById<ViewGroup>(android.R.id.content)
                        val temp: FloatingActionButton? = view.findViewById(it.id)
                        if (temp == null)
                            view.addView(fabButton)
                        it.visibility = if (it.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initListeners() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    @SuppressLint("RestrictedApi")
    private fun initActionButton() {
        val displayMetrics = resources.displayMetrics
        fabButton = FloatingActionButton(this).apply {
            id = View.generateViewId()
            size = FloatingActionButton.SIZE_NORMAL
            layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                bottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        MARGIN_BOTTOM, displayMetrics).toInt()
                marginEnd = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        MARGIN_END, displayMetrics).toInt()
                gravity = Gravity.BOTTOM or Gravity.END
            }
            setImageResource(R.drawable.ic_bug_report)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = 6f
            }
            isFocusable = true
            visibility = View.GONE
            setOnClickListener { navigateToReportScreen() }
        }
    }

    private fun navigateToReportScreen() {
        Intent(this, BugReportActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun initFragmentCallbacks() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                super.onFragmentCreated(fm, f, savedInstanceState)
                if (checkClassContaining(f)) {
                    Log.e("User open", f.javaClass.simpleName)
                }
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentDestroyed(fm, f)
                if (checkClassContaining(f)) {
                    Log.e("Fragment destroyed", f.javaClass.simpleName)
                }
            }
        }, true)
    }

    private fun findViewAt(viewGroup: ViewGroup, x: Int, y: Int): View? {
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child is ViewGroup) {
                val foundView = findViewAt(child, x, y)
                if (foundView != null && foundView.isShown) {
                    return foundView
                }
            } else {
                val location = IntArray(2)
                child.getLocationOnScreen(location)
                val rect = Rect(location[0], location[1],
                        location[0] + child.width,
                        location[1] + child.height)
                if (rect.contains(x, y)) {
                    return child
                }
            }
        }
        return null
    }

    private fun checkClassContaining(fragment: Fragment): Boolean =
            fragment.activity?.packageName?.let {
                fragment.javaClass.name.contains(it)
            } ?: false

    companion object {
        private const val SHAKE_THRESHOLD = 4f // m/S**2
        private const val MIN_TIME_BETWEEN_SHAKES = 1000

        private const val MARGIN_BOTTOM = 64f
        private const val MARGIN_END = 16f
    }
}
