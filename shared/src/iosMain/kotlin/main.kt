import androidx.compose.ui.window.ComposeUIViewController
import com.dksh.kmm.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
