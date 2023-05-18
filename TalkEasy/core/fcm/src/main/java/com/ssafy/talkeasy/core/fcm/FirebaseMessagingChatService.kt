package com.ssafy.talkeasy.core.fcm

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.ssafy.talkeasy.core.domain.entity.response.FCMChat
import com.ssafy.talkeasy.feature.common.SharedPreferences
import com.ssafy.talkeasy.feature.common.util.ChatMessageManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessagingChatService
@Inject constructor() : FirebaseMessagingService(), FirebaseTokenProvider {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onNewToken(token: String) {
        sharedPreferences.appToken = token
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // 메시지 수신
        message.notification?.let {
            Log.d(TAG, "onMessageReceived: message: title ${it.title}")
            Log.d(TAG, "onMessageReceived: message: body $${it.body}")
            val chat = Gson().fromJson(it.body, FCMChat::class.java)
            ChatMessageManager.chatMessageFlow.tryEmit(chat)
        }
    }

    override fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(
                    "FirebaseMessagingService",
                    "Fetching FCM registration token failed",
                    task.exception
                )
                return@OnCompleteListener
            }

            val token = task.result
            sharedPreferences.appToken = token
            Log.d("getFirebaseToken", "getFirebaseToken: $token")
        })
    }

    companion object {

        private const val TAG = "FirebaseMessagingService"
    }
}