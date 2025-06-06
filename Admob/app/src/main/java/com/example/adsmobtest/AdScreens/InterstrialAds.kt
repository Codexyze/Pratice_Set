package com.example.adsmobtest.AdScreens

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


fun interstialADS(activity: Activity) {
    val adRequest = AdRequest.Builder().build()

    InterstitialAd.load(activity,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            adError?.toString()?.let { Log.d(TAG, it) }

        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            Log.d(TAG, "Ad was loaded.".toString())
            interstitialAd.show(activity)
        }
    })
}

