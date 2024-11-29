package com.genericform

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.genericform.enums.FormFieldInputType
import com.genericform.models.FormField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.genericform.enums.FieldType
import com.genericform.utils.CreateCheckBoxes
import com.genericform.utils.CreateRadioButtons
import com.genericform.utils.DatePickerModal
import com.genericform.utils.LoadImageWithPlaceholder
import com.genericform.utils.LoadStepper
import com.genericform.utils.MyDropDownMenuWithTextField
import com.genericform.utils.TextArea
import com.genericform.utils.creditCardFilter


@SuppressLint("MutableCollectionMutableState")
@Composable
fun GenericForm(
    fields: List<FormField>,
    fieldsType: FieldType = FieldType.SIMPLE,
    onSubmit: (Map<String, TextFieldValue>, Map<String, String>) -> Unit) {

    if(fields.isEmpty()) return

    val formMapData = remember { mutableStateMapOf<String, TextFieldValue>() }
    val storeOtherData = mutableMapOf<String, String>()

    if(fieldsType == FieldType.SIMPLE) {
        var showModal by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf("") }
        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fields.forEach { field ->
                when (field.inputType) {
                    is FormFieldInputType.Text -> {
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

                    is FormFieldInputType.Email -> {
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
                    is FormFieldInputType.CardNumber -> {

                    }

                    // TODO: month drop down field in non outline
                   is  FormFieldInputType.MonthDropDown -> {

                    }

                    // TODO: year drop down field in non outline
                    is FormFieldInputType.YearDropDown -> {

                    }

                    // TODO: have to code custom field in non outline
                    FormFieldInputType.CUSTOM -> {

                    }

                    // TODO: implement simple custom drop down
                    is FormFieldInputType.CustomDropDown -> {
                        field.inputType.options
                    }

                    // TODO: implement simple checkbox upper label code
                    is FormFieldInputType.CheckBox -> {
                        CreateCheckBoxes(field.inputType.fieldName,field.inputType.options)
                    }

                    // TODO: implement simple radio button
                    is FormFieldInputType.RadioButton -> {

                    }

                    // TODO: implement simple image pick
                    is FormFieldInputType.PickImage -> {

                    }

                    // TODO: implement simple stepper control
                    is FormFieldInputType.StepperControl -> {

                    }

                    is FormFieldInputType.TextArea -> TODO()
                }
            }

            Button(onClick = { onSubmit(formMapData.toMap(), storeOtherData) }) {
                Text("Submit")
            }
        }
    }
    else {
        Column(
            Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            fields.forEach { field ->
                when (field.inputType) {
                    is FormFieldInputType.Text -> {

                        OutlinedTextField(
                            value = formMapData[field.name] ?: TextFieldValue(field.inputType.text),
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
                    is FormFieldInputType.Email -> {
                        var isEmailValid by remember { mutableStateOf(true) }
                        val emailRegex = Patterns.EMAIL_ADDRESS

                        OutlinedTextField(
                            value = formMapData[field.name] ?: TextFieldValue(field.inputType.emailText),
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
                            modifier = Modifier.width(IntrinsicSize.Max),
                            maxLines = 1,

                        )
                    }

                    // TODO: implement date text field
                    FormFieldInputType.DATE -> {

                    }

                    is FormFieldInputType.CardNumber -> {
                        OutlinedTextField(value = formMapData[field.name] ?: TextFieldValue(field.inputType.cardText),
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

                    is FormFieldInputType.MonthDropDown -> {
                        MyDropDownMenuWithTextField(options = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"),
                            textFieldLabel = "Month", style = field.style, onValueChange = {
                                storeOtherData[field.name] = it
                            },
                            initialSelectValue = field.inputType.initialSelectedMonth)
                    }

                    is FormFieldInputType.YearDropDown -> {
                        MyDropDownMenuWithTextField(options = listOf("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"),
                            textFieldLabel = "Year", style = field.style, onValueChange = {
                                storeOtherData[field.name] = it
                            },
                            initialSelectValue = field.inputType.initialSelectedYear)
                    }

                    FormFieldInputType.CUSTOM -> {
                        Row {
                            field.listOfFields.forEach {
                                when (it.inputType) {
                                    is FormFieldInputType.Text -> {
                                    }
                                    FormFieldInputType.NUMBER -> {
                                    }
                                    is FormFieldInputType.Email -> {
                                    }
                                    FormFieldInputType.PASSWORD -> {
                                    }
                                    FormFieldInputType.DATE -> {
                                    }
                                    is FormFieldInputType.CardNumber -> {
                                    }
                                    is FormFieldInputType.MonthDropDown -> {
                                        MyDropDownMenuWithTextField(options = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"),
                                            textFieldLabel = "Month", style = field.style, modifier = Modifier.weight(1f), onValueChange = { selectedOption ->
                                                storeOtherData[field.name] = selectedOption
                                            },
                                            initialSelectValue = it.inputType.initialSelectedMonth)
                                    }
                                    is FormFieldInputType.YearDropDown -> {
                                        MyDropDownMenuWithTextField(options = listOf("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"),
                                            textFieldLabel = "Year", style = field.style, modifier = Modifier.weight(1f), onValueChange = { selectedOption ->
                                                storeOtherData[field.name] = selectedOption
                                            },
                                            initialSelectValue = it.inputType.initialSelectedYear)
                                    }

                                    // should not be implemented
                                    FormFieldInputType.CUSTOM -> {
                                    }

                                    is FormFieldInputType.CustomDropDown -> {
                                        if(it.inputType.options.isNotEmpty()) {
                                            MyDropDownMenuWithTextField(options = it.inputType.options,
                                                textFieldLabel = it.inputType.fieldName, style = field.style, onValueChange = {
                                                    storeOtherData[field.name] = it
                                                },
                                                initialSelectValue = it.inputType.initialSelectedOption)

                                        }
                                    }

                                    // TODO: implement inner checkbox upper label code
                                    is FormFieldInputType.CheckBox -> {
                                        CreateCheckBoxes(it.inputType.fieldName,it.inputType.options)
                                    }

                                    // TODO: implement inner radio button
                                    is FormFieldInputType.RadioButton -> {

                                    }

                                    // TODO: implement inner image pick
                                    is FormFieldInputType.PickImage -> {

                                    }

                                    // TODO: implement inner text area
                                    is FormFieldInputType.TextArea -> {

                                    }

                                    is FormFieldInputType.StepperControl -> {
                                    }
                                }
                            }
                        }
                    }

                    // TODO: have to implement initial select
                    is FormFieldInputType.CustomDropDown -> {
                        if(field.inputType.options.isNotEmpty()) {
                            MyDropDownMenuWithTextField(options = field.inputType.options,
                                textFieldLabel = field.inputType.fieldName, style = field.style, onValueChange = {
                                    storeOtherData[field.name] = it
                                })
                        }
                    }

                    // TODO: implement outline checkbox upper label code
                    // TODO: implement initial select checkbox
                    is FormFieldInputType.CheckBox -> {
                        CreateCheckBoxes(field.inputType.fieldName,field.inputType.options,field.inputType.initialSelectedOption)
                    }

                    // TODO: implement outline radio button upper label code
                    // TODO: implement initial select radio button
                    is FormFieldInputType.RadioButton -> {
                        CreateRadioButtons(field.inputType.fieldName,field.inputType.options,field.inputType.initialSelectedOption)
                    }

                    // TODO: implement outline image pick
                    // TODO: implement initial select image using URI, URL, BITMAP
                    is FormFieldInputType.PickImage-> {
                        val bi = remember { mutableStateOf<Bitmap?>(null) }
                        if(field.inputType.url.isNotEmpty()) {
                            LoadImageWithPlaceholder(bi,R.drawable.baseline_image_24,field.inputType.url)
                        }
                        else {
                            LoadImageWithPlaceholder(bi,R.drawable.baseline_image_24)
                        }

                    }

                    // TODO: implement initial text
                    is FormFieldInputType.TextArea -> {
                        var text by remember { mutableStateOf(field.inputType.textAreaText) }
                        val charLimit = 200
                        TextArea(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            charLimit = charLimit
                        )
                    }

                    // TODO: implement outline stepper control UI enhancement
                    is FormFieldInputType.StepperControl -> {
                        val stepper = remember { mutableIntStateOf(0) }
                        LoadStepper(stepper)

                    }
                }
            }

            Button(onClick = {
                onSubmit(formMapData.toMap(),storeOtherData)
            }) {
                Text("Submit")
            }
        }
    }
}


// other useful functions



