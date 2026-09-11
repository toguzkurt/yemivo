package com.tnc.yemivo.app.feature.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.random.Random

/**
 * Lightweight confetti burst for the Surprise Recipe reveal — a handful of colored rectangles
 * animated with a single ValueAnimator, no external library. Call [burst] once the view has a
 * real size (it defers itself via [post] if called before the first layout pass).
 */
class ConfettiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private class Particle(
        val x: Float,
        val y: Float,
        val vx: Float,
        val vy: Float,
        val rotation: Float,
        val rotationSpeed: Float,
        val color: Int,
        val size: Float
    )

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var particles: List<Particle> = emptyList()
    private var animator: ValueAnimator? = null

    init {
        isClickable = false
        isFocusable = false
    }

    fun burst() {

        if (width == 0 || height == 0) {
            post(::burst)
            return
        }

        particles = List(PARTICLE_COUNT) {
            Particle(
                x = Random.nextFloat() * width,
                y = -Random.nextFloat() * height * 0.3f,
                vx = (Random.nextFloat() - 0.5f) * width * 0.7f,
                vy = height * (0.4f + Random.nextFloat() * 0.5f),
                rotation = Random.nextFloat() * 360f,
                rotationSpeed = (Random.nextFloat() - 0.5f) * 720f,
                color = COLORS[Random.nextInt(COLORS.size)],
                size = PARTICLE_SIZE_MIN + Random.nextFloat() * (PARTICLE_SIZE_MAX - PARTICLE_SIZE_MIN)
            )
        }

        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = DURATION_MS
            interpolator = LinearInterpolator()
            addUpdateListener { invalidate() }
            start()
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val progress = animator?.animatedValue as? Float ?: return
        val alpha = ((1f - progress) * 255).toInt().coerceIn(0, 255)
        if (alpha <= 0) return

        val elapsedSeconds = progress * DURATION_MS / 1000f

        particles.forEach { particle ->

            val x = particle.x + particle.vx * elapsedSeconds
            val y = particle.y + particle.vy * elapsedSeconds +
                0.5f * GRAVITY * elapsedSeconds * elapsedSeconds
            val rotation = particle.rotation + particle.rotationSpeed * elapsedSeconds

            if (y > height) return@forEach

            canvas.save()
            canvas.translate(x, y)
            canvas.rotate(rotation)
            paint.color = particle.color
            paint.alpha = alpha
            canvas.drawRoundRect(
                -particle.size / 2,
                -particle.size / 4,
                particle.size / 2,
                particle.size / 4,
                particle.size / 6,
                particle.size / 6,
                paint
            )
            canvas.restore()

        }

    }

    private companion object {
        const val PARTICLE_COUNT = 36
        const val DURATION_MS = 1400L
        const val GRAVITY = 900f
        const val PARTICLE_SIZE_MIN = 10f
        const val PARTICLE_SIZE_MAX = 22f
        val COLORS = intArrayOf(
            0xFFE53935.toInt(),
            0xFFFF9800.toInt(),
            0xFFFFC107.toInt(),
            0xFF43A047.toInt(),
            0xFF3A6EDC.toInt(),
            0xFFAB47BC.toInt()
        )
    }

}
