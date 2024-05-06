package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner

@Composable
fun BackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    DisposableEffect(lifecycleOwner) {
        val backCallback = object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                if (enabled) {
                    onBack()
                }
            }
        }
        dispatcher.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}
