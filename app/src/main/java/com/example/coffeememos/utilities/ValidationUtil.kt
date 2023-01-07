package com.example.coffeememos.utilities

import android.content.Context
import com.example.coffeememos.R

class ValidationUtil {
    companion object {
        private const val TASTE_MAX_VALUE: Int = 5
        private const val TASTE_MINIMUM_VALUE: Int = 1
        private const val TEMPERATURE_MAX_VALUE = 120
        private const val EXTRACTION_TIME_MINUTES_MAX_VALUE = 59
        private const val EXTRACTION_TIME_SECONDS_MAX_VALUE = 59

        fun validateTastes(context: Context, values: List<Int>): String {
            var validateMessage = ""
            for (value in values) {
                if (value < TASTE_MINIMUM_VALUE) {
                    validateMessage = context.getString(R.string.minimum_value_validation, TASTE_MINIMUM_VALUE)
                    break
                } else if (value > TASTE_MAX_VALUE) {
                    validateMessage = context.getString(R.string.max_value_validation, TASTE_MAX_VALUE)
                    break
                }
            }

            return validateMessage
        }

        fun validateTemperature(context: Context, value: Int): String {
            var validateMessage = ""
            if (value > TEMPERATURE_MAX_VALUE) {
                validateMessage = context.getString(R.string.max_value_validation, TEMPERATURE_MAX_VALUE)
            }

            return validateMessage
        }

        fun validateExtractionTimeMinutes(context: Context, value: Int): String {
            var validateMessage = ""
            if (value > EXTRACTION_TIME_MINUTES_MAX_VALUE) {
                validateMessage = context.getString(R.string.max_value_validation, EXTRACTION_TIME_MINUTES_MAX_VALUE)
            }

            return validateMessage
        }

        fun validateExtractionTimeSeconds(context: Context, value: Int): String {
            var validateMessage = ""
            if (value > EXTRACTION_TIME_SECONDS_MAX_VALUE) {
                validateMessage = context.getString(R.string.max_value_validation, EXTRACTION_TIME_SECONDS_MAX_VALUE)
            }

            return validateMessage
        }
    }
}