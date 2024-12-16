package com.example.englishpatterns.presentation.common.customTabs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent.EXTRA_REMOTEVIEWS_CLICKED_ID
import com.example.englishpatterns.data.common.ClipboardUnit
import com.example.englishpatterns.presentation.common.LocalStorage
import com.example.englishpatterns.presentation.common.copyToClipboard

class CustomTabBottomToolbarBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (context == null) return

        val localStorage = LocalStorage.getInstance(context = context.applicationContext)
        val remoteViewId: Int = intent.getIntExtra(EXTRA_REMOTEVIEWS_CLICKED_ID, -1)

        val text = when (remoteViewId) {
            CustomTabToolbarViewIds.RussianButton.id -> localStorage.ruClipText
            CustomTabToolbarViewIds.EnglishButton.id -> localStorage.enClipText
            CustomTabToolbarViewIds.SelectedButton.id -> localStorage.selectedClipText
            else -> return
        }

        context.copyToClipboard(clipboardUnit = ClipboardUnit(text = text))
        Toast.makeText(context, "Copied text: $text", Toast.LENGTH_SHORT).show()
    }
}