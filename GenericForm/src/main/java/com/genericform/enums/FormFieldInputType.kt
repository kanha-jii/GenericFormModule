package com.genericform.enums

//enum class FormFieldInputType {
//    TEXT, NUMBER, EMAIL, PASSWORD, DATE, CARDNUMBER, MONTHDROPDOWN, YEARDROPDOWN, CUSTOM, CUSTOMDROPDOWN
//}

sealed class FormFieldInputType {
    data class Text(val text:String = "") : FormFieldInputType()
    data object NUMBER : FormFieldInputType()
    data class Email(val emailText:String = "") : FormFieldInputType()
    data object PASSWORD : FormFieldInputType()
    data object DATE : FormFieldInputType()
    data class CardNumber(val cardText:String = "") : FormFieldInputType()
    data class MonthDropDown(val initialSelectedMonth:String = "") : FormFieldInputType()
    data class YearDropDown(val initialSelectedYear:String = "") : FormFieldInputType()
    data class PickImage(val url:String = "") : FormFieldInputType()
    // TODO: spell check text area, restrict particular words text area
    data class TextArea(val textAreaText:String = "") : FormFieldInputType()
    data object CUSTOM : FormFieldInputType()
    data class CheckBox(val fieldName:String,val options: List<String>,val initialSelectedOption:String = "") : FormFieldInputType()
    data class RadioButton(val fieldName:String,val options: List<String>,val initialSelectedOption:String = "") : FormFieldInputType()
    data class CustomDropDown(val fieldName:String,val options: List<String>,val initialSelectedOption:String = "") : FormFieldInputType()
    data class StepperControl(val textInMid:Boolean) : FormFieldInputType()
}