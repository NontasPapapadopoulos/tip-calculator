package com.example.tippy

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView



private const val INITIAL_TIP_PERCENT=15

class MainActivity : AppCompatActivity() {


    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDescription = findViewById(R.id.tvTipDescription)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercentLabel.text="$INITIAL_TIP_PERCENT%"
        updateTipDescription()

        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTipPercentLabel.text="$progress%"

                computeTipAndTotal()
                updateTipDescription()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                computeTipAndTotal()
                updateTipDescription()
            }
        })
    }

    private fun computeTipAndTotal() {
        // Get the value of the base and tip percent
        if(etBaseAmount.text.isEmpty()) {
            tvTotalAmount.text=""
            tvTipAmount.text=""
            return
        }
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        // Compute the tip and total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount

        // Update the UI
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }

    private fun updateTipDescription() {
        if(seekBarTip.progress <10) {
            tvTipDescription.text = "Poor"
        } else if (seekBarTip.progress > 20) {
            tvTipDescription.text = "Amazing"
        } else {
            tvTipDescription.text = "Good"
        }
    }
}

