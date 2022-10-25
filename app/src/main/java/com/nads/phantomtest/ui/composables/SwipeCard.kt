package com.nads.phantomtest.ui.composables


import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nads.phantomtest.ui.theme.PhantomTestTheme
import com.nads.phantomtest.utils.SwipeResult


@ExperimentalMaterialApi
@Composable
fun SwipeCard(
    modifier: Modifier = Modifier,
    onSwiped: (result: SwipeResult) -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    val swiped = remember { mutableStateOf(false) }
    BoxWithConstraints(modifier = modifier) {
        val swipeState = rememberSwipeState(
            maxWidth = constraints.maxWidth.toFloat(),
            maxHeight = constraints.maxHeight.toFloat()
        )
        if (swiped.value.not()) {
            Box(
                modifier = Modifier
                    .swiper(
                        state = swipeState,
                        onDragAccepted = {
                            swiped.value = true
                            onSwiped(SwipeResult.ACCEPT)
                        },
                        onDragRejected = {
                            swiped.value = true
                            onSwiped(SwipeResult.REJECT)
                        }
                       , onSwipeDownwards = {
                            swiped.value = true
                            onSwiped(SwipeResult.SWIPEDOWN)
                        },
                        onSwipeUpwards = {
                            swiped.value = true
                            onSwiped(SwipeResult.SWIPEUP)
                        }
                    ),
                content = content
            )
        }
    }
}

@Preview
@ExperimentalMaterialApi
@Composable
fun PreviewSwipeCard() {
    PhantomTestTheme(){
        SwipeCard(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center), onSwiped = { /*TODO*/ }) {
//            PersonCard(Person(
//                "",
//                "Nadeem rawther", "12-12-2012", "sajeena nivas , Maruthummoodu, 6915596", "+91 12334"
//            ))
        }
    }
}