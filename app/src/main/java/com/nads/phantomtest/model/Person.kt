package com.nads.phantomtest.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter

data class Person(
    val id:String,
    val name:String,
    @DrawableRes val image: Int,
    val like:Boolean
)
