package com.example.englishpatterns.presentation.common.customTabs

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import com.example.englishpatterns.R
import com.lib.lokdroid.core.logD
import com.lib.lokdroid.core.logE

class ChatGptCustomTabManager {

    private var client: CustomTabsClient? = null
    private var session: CustomTabsSession? = null
    private var customTabsServiceConnection: ServiceConnection? = null

    fun launchCustomTab(url: String, context: Context) {
        val actionIntent = Intent(
            context,
            CustomTabBottomToolbarBroadcastReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            actionIntent,
            PendingIntent.FLAG_MUTABLE
        )

        val secondaryToolbarViews = RemoteViews(context.packageName, R.layout.custom_tab_toolbar)
        val clickableIds = CustomTabToolbarViewIds.clickableId()

        val customTabsIntent = CustomTabsIntent.Builder(session)
            .setSecondaryToolbarViews(
                secondaryToolbarViews,
                clickableIds,
                pendingIntent
            ).build()

        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    fun prepareCustomTab(context: Context, url: String) {
        bindCustomTabsService(context = context, url = url)
    }

    private fun bindCustomTabsService(context: Context, url: String) {
        logD("bindCustomTabService() called.")

        val currentCustomTabsServiceConnection = customTabsServiceConnection

        if (client != null && currentCustomTabsServiceConnection != null) {
            logE("UNBINDING")
            context.unbindService(currentCustomTabsServiceConnection)
            client = null
            session = null
        }

        val defaultBrowserPackageName = CustomTabsClient.getPackageName(context, null) ?: return

        customTabsServiceConnection = createConnection(url = url).also {
            CustomTabsClient.bindCustomTabsService(context, defaultBrowserPackageName, it)
        }
    }

    private fun createConnection(url: String): CustomTabsServiceConnection {
        return object : CustomTabsServiceConnection() {

            override fun onCustomTabsServiceConnected(
                name: ComponentName,
                client: CustomTabsClient
            ) {
                logD("onCustomTabsServiceConnected() called. url: $url")

                this@ChatGptCustomTabManager.client = client
                val warmedUpSuccessfully = this@ChatGptCustomTabManager.client?.warmup(0L)

                logD("warmedUpSuccessfully: $warmedUpSuccessfully")

                session = this@ChatGptCustomTabManager.client?.newSession(
                    createCustomTabsCallback()
                )

                val isPagePreRenderedSuccessfully = session?.mayLaunchUrl(
                    Uri.parse(url),
                    null,
                    null
                )

                logD("isPagePreRenderedSuccessfully: $isPagePreRenderedSuccessfully")
            }

            override fun onServiceDisconnected(name: ComponentName) {
                logD("onServiceDisconnected() called.")

                client = null
                session = null
            }
        }
    }

    private fun createCustomTabsCallback(): CustomTabsCallback = object : CustomTabsCallback() {

        override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
            val event = when (navigationEvent) {
                NAVIGATION_ABORTED -> "NAVIGATION_ABORTED"
                NAVIGATION_FAILED -> "NAVIGATION_FAILED"
                NAVIGATION_FINISHED -> "NAVIGATION_FINISHED"
                NAVIGATION_STARTED -> "NAVIGATION_STARTED"
                TAB_SHOWN -> "TAB_SHOWN"
                TAB_HIDDEN -> "TAB_HIDDEN"
                else -> navigationEvent.toString()
            }

            logD("onNavigationEvent( $event )")
        }
    }
}