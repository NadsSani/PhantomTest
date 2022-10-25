package com.nads.phantomtest.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs


@Composable
@ExperimentalMaterialApi
fun rememberSwipeState(maxWidth: Float, maxHeight: Float): Swipe =
    remember { Swipe(maxWidth, maxHeight) }


open class Swipe(val maxWidth: Float, val maxHeight: Float) {
    val offsetX = Animatable(0f)
    val offsetY = Animatable(0f)

    fun reset(scope: CoroutineScope) = scope.launch {
        launch { offsetX.animateTo(0f, tween(400)) }
        launch { offsetY.animateTo(0f, tween(400)) }
    }

    fun accepted(scope: CoroutineScope) = scope.launch {
        offsetX.animateTo(maxWidth * 2, tween(400))
    }

    fun rejected(scope: CoroutineScope) = scope.launch {
        offsetX.animateTo(-(maxWidth * 2), tween(400))
    }
    fun onswipeupwards(scope: CoroutineScope) = scope.launch {

    }
    fun onswipedownwards(scope: CoroutineScope) = scope.launch {

    }
    fun drag(scope: CoroutineScope, x: Float, y: Float) = scope.launch {
        launch { offsetX.animateTo(x) }
        launch { offsetY.animateTo(y) }
    }
}


fun Modifier.swiper(
    state: Swipe,
    onDragReset: () -> Unit = {},
    onDragAccepted: () -> Unit,
    onDragRejected: () -> Unit,
    onSwipeUpwards: () -> Unit,
    onSwipeDownwards: () -> Unit
): Modifier = composed {
    val scope = rememberCoroutineScope()
    Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                when {
                    abs(state.offsetX.targetValue) < state.maxWidth / 4 -> {
                        state
                            .reset(scope)
                            .invokeOnCompletion { onDragReset() }
                    }
                    state.offsetX.targetValue > 0 -> {
                        state
                            .accepted(scope)
                            .invokeOnCompletion { onDragAccepted() }
                    }
                    state.offsetX.targetValue < 0 -> {
                        state
                            .rejected(scope)
                            .invokeOnCompletion { onDragRejected() }
                    }
                    state.offsetY.targetValue > 0 -> {
                        state
                            .onswipeupwards(scope)
                            .invokeOnCompletion { onSwipeUpwards() }
                    }
                    state.offsetY.targetValue < 0 -> {
                        state
                            .onswipedownwards(scope)
                            .invokeOnCompletion { onSwipeDownwards() }
                    }
                }
            },
            onDrag = { change, dragAmount ->
                val original = Offset(state.offsetX.targetValue, state.offsetY.targetValue)
                val summed = original + dragAmount
                val newValue = Offset(
                    x = summed.x.coerceIn(-state.maxWidth, state.maxWidth),
                    y = summed.y.coerceIn(-state.maxHeight, state.maxHeight)
                )
                change.consumePositionChange()
                state.drag(scope, newValue.x, newValue.y)
            }
        )
    }.graphicsLayer(
        translationX = state.offsetX.value,
        translationY = state.offsetY.value,
        rotationZ = (state.offsetX.value / 60).coerceIn(-40f, 40f),
        alpha = ((state.maxWidth - abs(state.offsetX.value)) / state.maxWidth).coerceIn(0f, 1f)
    )
}


