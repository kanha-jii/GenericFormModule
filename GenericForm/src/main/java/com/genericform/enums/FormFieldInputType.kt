package com.genericform.enums

//enum class FormFieldInputType {
//    TEXT, NUMBER, EMAIL, PASSWORD, DATE, CARDNUMBER, MONTHDROPDOWN, YEARDROPDOWN, CUSTOM, CUSTOMDROPDOWN
//}

sealed class FormFieldInputType {
    data object TEXT : FormFieldInputType()
    data object NUMBER : FormFieldInputType()
    data object EMAIL : FormFieldInputType()
    data object PASSWORD : FormFieldInputType()
    data object DATE : FormFieldInputType()
    data object CARDNUMBER : FormFieldInputType()
    data object MONTHDROPDOWN : FormFieldInputType()
    data object YEARDROPDOWN : FormFieldInputType()
    data object PICKIMAGE : FormFieldInputType()
    data object CUSTOM : FormFieldInputType()
    data class CheckBox(val fieldName:String,val options: List<String>) : FormFieldInputType()
    data class RadioButton(val fieldName:String,val options: List<String>) : FormFieldInputType()
    data class CustomDropDown(val fieldName:String,val options: List<String>) : FormFieldInputType()
}