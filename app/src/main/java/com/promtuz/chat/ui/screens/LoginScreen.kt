package com.promtuz.chat.ui.screens

import androidx.compose.foundation.layout.width

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.promtuz.chat.compositions.LocalBackStack
import com.promtuz.chat.navigation.AppRoutes
import com.promtuz.chat.navigation.Navigate
import com.promtuz.chat.ui.theme.PromtuzTheme
import com.promtuz.chat.ui.theme.adjustLight

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val backStack = LocalBackStack.current

    Box(
        modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO: Move Constants to Separate Files
            Text(
                "Welcome Back!",
                modifier = Modifier.padding(bottom = 6.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(48.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = username,
                onValueChange = {
                    username = it
                },
                label = {
                    Text(
                        "Username", color = adjustLight(
                            MaterialTheme.colorScheme.background, 0.6f
                        ),
                        fontWeight = FontWeight.W500
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                colors = outlinedTextFieldColors(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(
                        "Password", color = adjustLight(
                            MaterialTheme.colorScheme.background, 0.6f
                        ),
                        fontWeight = FontWeight.W500
                    )
                },
                colors = outlinedTextFieldColors(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    false,
                    onCheckedChange = { },
                )

                Text("Remember me?", color = MaterialTheme.colorScheme.onBackground)
            }

            Button(
                {
                    Navigate.base(backStack, AppRoutes.EncryptionKeyScreen)
                }, Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Continue", fontWeight = FontWeight.W500, fontSize = 16.sp)
            }


            TextButton(
                {

                }, Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text("Create an account", fontWeight = FontWeight.W500, fontSize = 16.sp)
            }
        }

        Row(
            modifier = Modifier.align(BiasAlignment(0f, 0.85f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                Icons.Rounded.Lock, "Lock", Modifier.size(16.dp), tint = adjustLight(
                    MaterialTheme.colorScheme.background, 0.6f
                )
            )

            Text(
                "End to End Encrypted",
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = adjustLight(
                    MaterialTheme.colorScheme.background, 0.6f
                )
            )
        }
    }
}

@Composable
fun outlinedTextFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = adjustLight(
            MaterialTheme.colorScheme.background, 0.3f
        ),
        focusedBorderColor = adjustLight(
            MaterialTheme.colorScheme.background, 0.5f
        )
    )
}


@Composable
@Preview
fun LoginScreenPreview(modifier: Modifier = Modifier) {
    PromtuzTheme(darkTheme = true, dynamicColor = true) {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            LoginScreen()
        }
    }
}