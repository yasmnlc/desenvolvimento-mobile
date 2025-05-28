package com.example.alarmapp.ui.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import java.util.*

@Composable
fun TimePickerDialogHandler(
    show: Boolean,
    onTimeSelected: (Pair<Int, Int>) -> Unit,
    onDismiss: () -> Unit,
    context: Context
) {
    if (show) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                onTimeSelected(Pair(selectedHour, selectedMinute))
            },
            hour,
            minute,
            true
        ).apply {
            setOnDismissListener { onDismiss() }
            show()
        }
    }
}