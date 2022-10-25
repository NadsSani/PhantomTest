package com.nads.phantomtest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nads.phantomtest.model.Person
import com.nads.phantomtest.ui.composables.PersonCard
import com.nads.phantomtest.ui.composables.SwipeCard
import com.nads.phantomtest.ui.theme.PhantomTestTheme
import com.nads.phantomtest.utils.SwipeResult
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhantomTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainNavigation("Android",mainViewModel, LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun MainNavigation(name: String,mainViewModel: MainViewModel,context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen" ){
        composable("splash_screen"){
            SplashScreen(navController)

        }
        composable("main_screen"){

            SwiperPage(mainViewModel,onSwiped = {result: SwipeResult, person: Person ->
                val swipeposition = mainViewModel.isLoading.value
                if (swipeposition){
                    dToast(context)
                }
                else{
                    mToast(context)
                }
                mainViewModel.executeNext(result,person)
            })
        }
    }

}


@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.5f
            , animationSpec = tween(
                durationMillis = 3000
                , easing = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                        OvershootInterpolator(2f).getInterpolation(it)
                    } else {
                        TODO("VERSION.SDK_INT < DONUT")
                    }
                }
            ))
        delay(3000L)
        navController.navigate("main_screen")
    }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)){
        Image(painter = painterResource(id = R.drawable.phantom_logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))



    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwiperPage(mainViewModel: MainViewModel,onSwiped: (result: SwipeResult, person: Person) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
       val personlist = mainViewModel.personlist

        Box(modifier = Modifier.fillMaxSize()) {
            personlist.forEachIndexed { _, person ->

                SwipeCard(
                    onSwiped = { onSwiped(it, person) }, modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopCenter)
                ) {
                    PersonCard(person = person)
                }
            }
        }
    }
}
private fun mToast(context: Context){
    Toast.makeText(context, "SWIPE UPWARDS", Toast.LENGTH_LONG).show()
}
private fun dToast(context: Context){
    Toast.makeText(context, "SWIPE DOWNWARDS", Toast.LENGTH_LONG).show()
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhantomTestTheme {
      // MainNavigation(name = "Nadeem", mainViewModel = )
    }
}
