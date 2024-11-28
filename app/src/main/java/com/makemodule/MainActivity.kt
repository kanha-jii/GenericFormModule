package com.makemodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.genericform.GenericForm
import com.genericform.enums.FieldType
import com.genericform.enums.FormFieldInputType
import com.genericform.models.FormField
import com.makemodule.ui.theme.MakeModuleTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MakeModuleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    println(innerPadding)
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                        Spacer(modifier = Modifier.padding(16.dp))
                        GenericForm(
                            fieldsType = FieldType.OUTLINED,
                            fields = listOf(
                                FormField(
                                    "name",
                                    "name",
                                    FormFieldInputType.TEXT,
                                    TextStyle.Default
                                ),
                                FormField(
                                    "email",
                                    "email",
                                    FormFieldInputType.EMAIL,
                                    TextStyle.Default
                                ),
                                FormField(
                                    "password",
                                    "password",
                                    FormFieldInputType.PASSWORD,
                                    TextStyle.Default
                                ),
                                FormField(
                                    "confirmPassword",
                                    "confirm password",
                                    FormFieldInputType.PASSWORD,
                                    TextStyle.Default
                                ),
                                FormField(
                                    "age",
                                    "age",
                                    FormFieldInputType.NUMBER,
                                    TextStyle.Default
                                ),
                                FormField(
                                    "card",
                                    "Credit Card",
                                    FormFieldInputType.CARDNUMBER,
                                    TextStyle.Default
                                ),
                                FormField(
                                    "cardMonth",
                                    "Month",
                                    FormFieldInputType.MONTHDROPDOWN,
                                    TextStyle.Default
                                ),
                                FormField(
                                    "cardYear",
                                    "Year",
                                    FormFieldInputType.YEARDROPDOWN,
                                    TextStyle.Default,
                                ),
                                FormField("custom",
                                    "Custom",
                                    FormFieldInputType.CUSTOM,
                                    TextStyle.Default,
                                    listOfFields = listOf(
                                        FormField(
                                            "cardMonth",
                                            "Month",
                                            FormFieldInputType.MONTHDROPDOWN,
                                            TextStyle.Default
                                        ),
                                        FormField(
                                            "cardYear",
                                            "Year",
                                            FormFieldInputType.YEARDROPDOWN,
                                            TextStyle.Default,
                                        ),
                                    )
                                ),
                                FormField("customdropdown",
                                    "Custom Dropdown",
                                    FormFieldInputType.CustomDropDown("Numbers",listOf("one", "two", "three")),
                                    TextStyle.Default),

                                FormField("checkbox",
                                    "Check Box",
                                    FormFieldInputType.CheckBox("Hobby",listOf("Cricket","Badminton","Tennis")),
                                    TextStyle.Default),

                                FormField("radio",
                                    "Radio Button",
                                    FormFieldInputType.RadioButton("Gender",listOf("Male","Female")),
                                    TextStyle.Default),

                            )
                        ) { data ->

                            println(data)
                        }


                        println("hello")

//                        MutableStateMapOfSample()

//                        Button(onClick = { showModal = true }) {
//                            Text("Show Modal Date Picker")
//                        }

//                        DatePickerDocked()

//                        if(showModal) {
//                            DatePickerModal(onDateSelected = {
//                                println(it)
//                                showModal = false
//                            },
//                                onDismiss = {
//                                    showModal = false
//                                })
//                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MakeModuleTheme {
        Greeting("Android")
    }
}

@Composable
private fun MutableStateMapOfSample() {
    val map: SnapshotStateMap<Int, String> = remember {
        mutableStateMapOf()
    }

    var counter by remember {
        mutableIntStateOf(0)
    }

    Button(onClick = {
        map[counter] = "Value: $counter"
        counter++
    }) {
        Text("Add $counter")
    }

    LazyColumn{
        items(map.size) {
            Text("key: $it, value: ${map[it]}")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("DOB") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

