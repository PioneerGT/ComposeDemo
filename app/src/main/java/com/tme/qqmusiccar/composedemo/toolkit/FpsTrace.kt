package com.tme.qqmusiccar.composedemo.toolkit

import android.app.Activity
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Choreographer
import android.view.FrameMetrics
import java.lang.reflect.Field

/**
 * Fps 监控
 * <p>
 *     Android APP刷新录帧率监控,我们常说的FPS
 *     https://zhuanlan.zhihu.com/p/609803514
 * </p>
 */
class FpsTrace {
    companion object {
        private const val TAG = "FpsTrace"
    }

    var fps: Float = 0f
        private set

    private var sumFrameCost = 0L
    private var sumFrame = 0L
    private var lastDuration = 0L
    private var lastFrames = 0L

    private var refreshRate: Float = 0f
    private var frameIntervalNanos: Float = 0f

    private var start: Long = 0L

    fun setup(activity: Activity) {
        refreshRate = activity.windowManager.defaultDisplay.refreshRate
        frameIntervalNanos = 1 / refreshRate * 1000000000

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.window.addOnFrameMetricsAvailableListener({ window, frameMetrics, dropCountSinceLastInvocation ->
                val frameMetricsCopy = FrameMetrics(frameMetrics)
                val vsyncTime = frameMetricsCopy.getMetric(FrameMetrics.VSYNC_TIMESTAMP)
                val intendedVsyncTime = frameMetricsCopy.getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP)
                val jitter = vsyncTime - intendedVsyncTime
                val dropFrame = (jitter / frameIntervalNanos).toLong()
                calcFps(dropFrame)
            }, Handler(Looper.getMainLooper()))
        } else {
            Handler(Looper.getMainLooper()).apply {
                looper.setMessageLogging {
                    if (it.toString().startsWith(">>>>>")) {
                        startMethod(it)
                    }
                    if (it.toString().startsWith("<<<<<")) {
                        endMethod(it)
                    }
                }
            }
        }
    }

    private fun calcFps(dropFrame: Long) {
        sumFrameCost += ((dropFrame + 1) * frameIntervalNanos / 1000000).toLong()
        sumFrame += 1

        val duration = sumFrameCost - lastDuration
        val collectFrame = sumFrame - lastFrames
        if (duration >= 200) {
            fps = Math.min(refreshRate, 1000.0f * collectFrame / duration)
            Log.i(TAG, ">>>>>>>fps->$fps,")
            lastFrames = sumFrame
            lastDuration = sumFrameCost
        }
    }

    private fun startMethod(it: String) {
        start = System.nanoTime()
    }

    private fun endMethod(it: String) {
        val intentStart = getIntendedFrameTimeNs(start)
        val jitter = System.nanoTime() - intentStart
        val dropFrame = (jitter / frameIntervalNanos).toLong()
        calcFps(dropFrame)
    }

    private fun getIntendedFrameTimeNs(defaultValue: Long): Long {
        val choreographer = Choreographer.getInstance()
        val getDeclaredField = Class::class.java.getDeclaredMethod("getDeclaredField", String::class.java)
        val field = getDeclaredField.invoke(choreographer.javaClass, "mDisplayEventReceiver") as Field
        field.isAccessible = true
        val vsyncReceiver = field.get(choreographer)
        return reflectGetObjectFieldValue(vsyncReceiver, "mTimestampNanos", defaultValue)
    }

    private fun reflectGetObjectFieldValue(instance: Any, fieldName: String, defaultValue: Long): Long {
        val field = instance.javaClass.getDeclaredField(fieldName)
        return field.get(instance) as? Long ?: defaultValue
    }
}