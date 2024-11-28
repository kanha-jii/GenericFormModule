package com.genericform

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.genericform.enums.FormFieldInputType
import com.genericform.models.FormField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.genericform.enums.FieldType
import com.genericform.utils.MyDropDownMenuWithTextField
import com.genericform.utils.creditCardFilter


@SuppressLint("MutableCollectionMutableState")
@Composable
fun GenericForm(
    fields: List<FormField>,
    fieldsType: FieldType = FieldType.SIMPLE,
    onSubmit: (Map<String, TextFieldValue>) -> Unit) {

    if(fields.isEmpty()) return
    val formMapData = remember { mutableStateMapOf<String, TextFieldValue>() }

    if(fieldsType == FieldType.SIMPLE) {
        var showModal by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf("") }
        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fields.forEach { field ->
                when (field.inputType) {
                    FormFieldInputType.TEXT -> {
                        TextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style
                        )
                    }

                    FormFieldInputType.NUMBER -> TextField(
                        value = formMapData[field.name] ?: TextFieldValue(""),
                        onValueChange = {
                            formMapData[field.name] = it
                        },
                        label = { Text(field.label) },
                        textStyle = field.style,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )

                    FormFieldInputType.EMAIL -> {
                        TextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    FormFieldInputType.PASSWORD -> {
                        TextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }
                    // TODO: have to correct date picker
                    FormFieldInputType.DATE -> {
                        Row {
                            Button(onClick = {
                                showModal = true
                            }) {
                                Text("Select Date")
                            }

                            Text(selectedDate)
                            if (showModal) {
                                DatePickerModal(onDateSelected = {
                                    selectedDate = it.toString()
                                },
                                    onDismiss = {
                                        showModal = false
                                    })
                            }
                        }
                    }

                    // TODO: card-number field in non outline
                    FormFieldInputType.CARDNUMBER -> {

                    }
                    // TODO: month drop down field in non outline
                    FormFieldInputType.MONTHDROPDOWN -> {

                    }
                    // TODO: year drop down field in non outline
                    FormFieldInputType.YEARDROPDOWN -> {

                    }
                    // TODO: have to code custom field in non outline
                    FormFieldInputType.CUSTOM -> {

                    }

                    // TODO: implement simple custom drop down
                    FormFieldInputType.CUSTOMDROPDOWN -> {

                    }
                }
            }

            Button(onClick = { onSubmit(formMapData.toMap()) }) {
                Text("Submit")
            }
        }
    }
    else {
        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fields.forEach { field ->
                when (field.inputType) {
                    FormFieldInputType.TEXT -> {
                        OutlinedTextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            textStyle = field.style,
                            modifier = Modifier.width(IntrinsicSize.Max)
                        )
                    }
                    FormFieldInputType.NUMBER -> {
                        OutlinedTextField(value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.width(IntrinsicSize.Max)
                        )
                    }
                    FormFieldInputType.EMAIL -> {
                        var isEmailValid by remember { mutableStateOf(true) }
                        val emailRegex = Patterns.EMAIL_ADDRESS

                        OutlinedTextField(
                            value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                                isEmailValid = emailRegex.matcher(it.text).matches()
                                if(it.text.isEmpty()) {
                                    isEmailValid = true
                                }
                            },
                            label = {
                                Text(if (isEmailValid) field.label else "${field.label} (Invalid)")
                            },
                            isError = !isEmailValid,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.width(IntrinsicSize.Max)
                        )
                    }


                    FormFieldInputType.PASSWORD -> {
                        OutlinedTextField(value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier.width(IntrinsicSize.Max)
                        )
                    }

                    // TODO: implement data text field
                    FormFieldInputType.DATE -> {

                    }

                    FormFieldInputType.CARDNUMBER -> {
                        OutlinedTextField(value = formMapData[field.name] ?: TextFieldValue(""),
                            onValueChange = {
                                formMapData[field.name] = it
                            },
                            label = { Text(field.label) },
                            visualTransformation = {
                                creditCardFilter(it)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    FormFieldInputType.MONTHDROPDOWN -> {
                        MyDropDownMenuWithTextField(options = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"),
                            textFieldLabel = "Month", style = field.style )
                    }

                    FormFieldInputType.YEARDROPDOWN -> {
                        MyDropDownMenuWithTextField(options = listOf("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"),
                            textFieldLabel = "Year", style = field.style)
                    }

                    FormFieldInputType.CUSTOM -> {
                        Row() {
                            field.listOfFields.forEach {
                                when (it.inputType) {
                                    FormFieldInputType.TEXT -> {

                                    }
                                    FormFieldInputType.NUMBER -> {

                                    }
                                    FormFieldInputType.EMAIL -> {

                                    }
                                    FormFieldInputType.PASSWORD -> {

                                    }
                                    FormFieldInputType.DATE -> {

                                    }
                                    FormFieldInputType.CARDNUMBER -> {

                                    }
                                    FormFieldInputType.MONTHDROPDOWN -> {
                                        MyDropDownMenuWithTextField(options = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"),
                                            textFieldLabel = "Month", style = field.style, modifier = Modifier.weight(1f))
                                    }
                                    FormFieldInputType.YEARDROPDOWN -> {
                                        MyDropDownMenuWithTextField(options = listOf("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"),
                                            textFieldLabel = "Year", style = field.style, modifier = Modifier.weight(1f))
                                    }
                                    FormFieldInputType.CUSTOM -> {

                                    }

                                    // TODO: implement inner outline custom dropdown
                                    FormFieldInputType.CUSTOMDROPDOWN -> {

                                    }
                                }
                            }
                        }
                    }

                    // TODO: implement outline custom drop down
                    FormFieldInputType.CUSTOMDROPDOWN -> {

                    }
                }
            }
            Button(onClick = { onSubmit(formMapData.toMap()) }) {
                Text("Submit")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}