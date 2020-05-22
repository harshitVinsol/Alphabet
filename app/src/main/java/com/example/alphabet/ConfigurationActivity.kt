package com.example.alphabet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alphabet.MainActivity.Companion.ANIMATION_SPEED
import com.example.alphabet.MainActivity.Companion.HORIZONTAL_SPACING
import com.example.alphabet.MainActivity.Companion.VERTICAL_SPACING
import kotlinx.android.synthetic.main.activity_configuration.*

class ConfigurationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        var animationSpeed = intent.getLongExtra(ANIMATION_SPEED, 200L)
        var verticalSpacing = intent.getIntExtra(VERTICAL_SPACING, 16)
        var horizontalSpacing = intent.getIntExtra(HORIZONTAL_SPACING, 16)

        animation_speed.setText(animationSpeed.toString())
        vertical_spacing.setText(verticalSpacing.toString())
        horizontal_spacing.setText(horizontalSpacing.toString())
        button_make_changes.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
