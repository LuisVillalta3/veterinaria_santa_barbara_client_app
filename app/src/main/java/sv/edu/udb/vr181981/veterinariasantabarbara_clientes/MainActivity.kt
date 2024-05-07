package sv.edu.udb.vr181981.veterinariasantabarbara_clientes

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.NavigationHost
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.VeterinariaSantaBarbara_clientesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            VeterinariaSantaBarbara_clientesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    NavigationHost()
                }
            }
        }
    }
}
