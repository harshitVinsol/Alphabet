package com.example.alphabet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.alphabet.AnimationActivity.Companion.ANIMATION_SPEED
import com.example.alphabet.AnimationActivity.Companion.HORIZONTAL_SPACING
import com.example.alphabet.AnimationActivity.Companion.VERTICAL_SPACING
import kotlinx.android.synthetic.main.activity_configuration.*

class ConfigurationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        val animationSpeed = intent.getLongExtra(ANIMATION_SPEED, 200L)
        val verticalSpacing = intent.getIntExtra(VERTICAL_SPACING, 16)
        val horizontalSpacing = intent.getIntExtra(HORIZONTAL_SPACING, 16)
        validateOnChange()
        animation_speed.setText(animationSpeed.toString())
        vertical_spacing.setText(verticalSpacing.toString())
        horizontal_spacing.setText(horizontalSpacing.toString())
        button_make_changes.setOnClickListener {
            if (validate()) {
                val intent = Intent(this, AnimationActivity::class.java)
                intent.putExtra(ANIMATION_SPEED, animation_speed.text.toString().toLong())
                intent.putExtra(VERTICAL_SPACING, vertical_spacing.text.toString().toInt())
                intent.putExtra(HORIZONTAL_SPACING, horizontal_spacing.text.toString().toInt())
                startActivity(intent)
            }
        }
    }

    /*
    A Boolean function validate to check the validation of animation speed, horizontal spacing and vertical spacing
     */
    private fun validate() =
        (validateAnimationSpeed() && validateHorizontalSpacing() && validateVerticalSpacing())

    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /*
    A boolean function to check the validation of animation speed i.e it should not be below 50
     */
    private fun validateAnimationSpeed(): Boolean {
        return if (animation_speed.text.toString().isBlank() || animation_speed.text.toString()
                .toLong() < 51
        ) {
            animation_speed_input_layout.error = resources.getString(R.string.animation_speed_error)
            animation_speed.requestFocus()
            false
        } else {
            animation_speed_input_layout.error = null
            true
        }
    }

    /*
    A boolean function to check the validation of horizontal spacing it should not be blank instead can be 0
     */
    private fun validateHorizontalSpacing(): Boolean {
        return if (horizontal_spacing.text.toString().isBlank()) {
            horizontal_spacing_input_layout.error =
                resources.getString(R.string.horizontal_spacing_error)
            horizontal_spacing.requestFocus()
            false
        } else {
            horizontal_spacing_input_layout.error = null
            true
        }
    }

    /*
    A boolean function to check the validation of vertical spacing it should not be blank instead can be 0
     */
    private fun validateVerticalSpacing(): Boolean {
        return if (vertical_spacing.text.toString().isBlank()) {
            vertical_spacing_input_layout.error =
                resources.getString(R.string.vertical_spacing_error)
            vertical_spacing.requestFocus()
            false
        } else {
            vertical_spacing_input_layout.error = null
            true
        }
    }

    /*
    A lambda function to validate the fields on the change event happening in them
     */
    private fun validateOnChange() {
        animation_speed.onChange {
            validateAnimationSpeed()
        }

        vertical_spacing.onChange {
            validateVerticalSpacing()
        }

        horizontal_spacing.onChange {
            validateHorizontalSpacing()
        }
    }
}
