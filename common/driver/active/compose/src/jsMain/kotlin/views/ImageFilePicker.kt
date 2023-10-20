package views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.darkrockstudios.libraries.mpfilepicker.FilePicker

@Composable
actual fun ImageFilePicker() {
    var showFilePicker by remember { mutableStateOf(true) }

    val fileType = listOf("jpg", "png", "jpeg")
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        showFilePicker = false
        // do something with the file
    }
}