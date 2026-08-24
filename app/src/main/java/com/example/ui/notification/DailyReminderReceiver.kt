package com.example.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DailyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getDatabase(context)
                val settings = db.progressDao().getUserSettings().firstOrNull()

                if (action == Intent.ACTION_BOOT_COMPLETED || action == Intent.ACTION_MY_PACKAGE_REPLACED) {
                    // Reschedule after phone reboot if enabled
                    if (settings?.reminderEnabled == true) {
                        DailyReminderManager.scheduleDailyReminder(
                            context = context,
                            hour = settings.reminderHour,
                            minute = settings.reminderMinute
                        )
                    }
                } else {
                    // Alarm fired - show reminder notification
                    val streak = settings?.currentStreak ?: 1
                    val habitName = settings?.selectedHabitName ?: "Lecture quotidienne"

                    DailyReminderManager.showReadingNotification(
                        context = context,
                        title = "Sekolin'ny Fiainana • $habitName",
                        message = "Ta série est à $streak jour(s) ! Ouvre l'application pour compléter ta planche du jour."
                    )

                    // Schedule next day reminder
                    val hour = settings?.reminderHour ?: 20
                    val minute = settings?.reminderMinute ?: 30
                    if (settings?.reminderEnabled != false) {
                        DailyReminderManager.scheduleDailyReminder(
                            context = context,
                            hour = hour,
                            minute = minute
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }
}
