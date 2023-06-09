package com.ssafy.talkeasy.feature.aac.ui

// import com.ssafy.talkeasy.feature.aac.SampleData.Companion.memberName
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.feature.aac.R.string
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_errorContainer
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.util.ChatMessageManager
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import kotlinx.coroutines.flow.asSharedFlow

@Composable
fun AACTopBar(
    onRight: Boolean,
    showSOSRequestDialog: () -> Unit,
    showNotificationDialog: () -> Unit,
) {
    if (onRight) {
        Row {
            ButtonSOS(showSOSRequestDialog = showSOSRequestDialog)

            Spacer(modifier = Modifier.width(20.dp))

            ButtonAlarmAndSetting(showNotificationDialog = showNotificationDialog)
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 21.dp, start = 36.dp, end = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonSOS(showSOSRequestDialog = showSOSRequestDialog)

            ButtonAlarmAndSetting(showNotificationDialog = showNotificationDialog)
        }
    }
}

@Composable
fun ButtonSOS(showSOSRequestDialog: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { showSOSRequestDialog() },
        shape = shapes.extraSmall,
        color = md_theme_light_errorContainer
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            text = "SOS",
            color = md_theme_light_error,
            style = textStyleBold22
        )
    }
}

@Composable
fun ButtonAlarmAndSetting(
    showNotificationDialog: () -> Unit,
    followViewModel: FollowViewModel = viewModel(),
) {
    val (newAlarm, setNewAlarm) = remember {
        mutableStateOf(false)
    }
    val chatMessageFlow = ChatMessageManager.chatMessageFlow.asSharedFlow()

    LaunchedEffect(chatMessageFlow) {
        chatMessageFlow.collect { chatMessage ->
            // 채팅 메시지에 대한 처리를 여기서 합니다.
            followViewModel.requestFollowList()
            if (chatMessage.type == 2 || chatMessage.type == 1) {
                setNewAlarm(true)
            }
        }
    }

    Row {
        IconButton(
            onClick = {
                setNewAlarm(false)
                followViewModel.requestNotificationList()
                showNotificationDialog()
            }
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = if (newAlarm) {
                    painterResource(id = R.drawable.ic_alarm_new)
                } else {
                    painterResource(id = R.drawable.ic_alarm_default)
                },
                contentDescription = stringResource(string.image_alarm_default)
            )
        }

        // Spacer(modifier = Modifier.width(18.dp))
        //
        // IconButton(onClick = {}, enabled = false) {
        //     IconButton(onClick = {}) {
        //     Icon(
        //         modifier = Modifier.size(40.dp),
        //         painter = painterResource(id = R.drawable.ic_setting),
        //         contentDescription = stringResource(string.image_setting)
        //     )
        //     Icon(
        //         modifier = Modifier.size(40.dp),
        //         painter = painterResource(id = R.drawable.ic_setting),
        //         contentDescription = stringResource(string.image_setting),
        //         tint = Color.Transparent
        //     )
        // }
    }
}