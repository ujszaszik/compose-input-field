package hu.ujszaszik.compose.inputfield.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.res.ResourcesCompat

@Composable
fun fontFamilyResource(resId: Int): FontFamily {
    return FontFamily(
        typeface = ResourcesCompat.getFont(LocalContext.current, resId)!!
    )
}