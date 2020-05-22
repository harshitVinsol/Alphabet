package com.example.alphabet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
        validateOnChange()
        animation_speed.setText(animationSpeed.toString())
        vertical_spacing.setText(verticalSpacing.toString())
        horizontal_spacing.setText(horizontalSpacing.toString())
        button_make_changes.setOnClickListener {
            if(validateAnimationSpeed()){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateAnimationSpeed(): Boolean {
        return if (animation_speed.text.toString().isBlank() || animation_speed.text.toString().toLong() < 101) {
            animation_speed_input_layout.error = resources.getString(R.string.animation_speed_error)
            animation_speed.requestFocus()
            false
        } else {
            animation_speed_input_layout.error = null
            true
        }
    }

    private fun validateOnChange() {
        animation_speed.onChange {
            validateAnimationSpeed()
        }
    }
}
