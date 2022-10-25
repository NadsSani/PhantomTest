package com.nads.phantomtest.ui.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nads.phantomtest.R
import com.nads.phantomtest.model.Person
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun PersonCard(person: Person) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(shape = RoundedCornerShape(4.dp), color = MaterialTheme.colors.background) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                CoilImage(
                    imageModel = { person.image },
                    imageOptions = ImageOptions(
                        alignment = Alignment.Center
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = person.name, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(16.dp))
                if (person.like) {
                    CoilImage(imageModel = {R.drawable.likeicon},
                        imageOptions = ImageOptions(alignment = Alignment.Center)
                    , modifier = Modifier.height(50.dp).width(50.dp))
                }else{
                    CoilImage(imageModel = {R.drawable.dislikeicon},
                        imageOptions = ImageOptions(alignment = Alignment.Center)
                        , modifier = Modifier.height(50.dp).width(50.dp))
                }
                }
        }
    }
}
@Preview
@Composable
fun PersonCardPreview() {
    val person = Person(id ="1", name = "Nadeem",image = R.drawable.pichu,false)
    PersonCard(person =person )

}
