package hu.ujszaszik.compose.inputfield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.ujszaszik.compose.inputfield.ui.theme.ComposeInputFieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeInputFieldTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        TestNumericInputField()
                        Spacer(modifier = Modifier.height(16.dp))
                        TestCreditCardInputField()
                    }
                }
            }
        }
    }
}