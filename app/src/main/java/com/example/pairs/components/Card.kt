package com.example.pairs.components

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pairs.R
import com.example.pairs.ui.theme.gwentFontFamily


@Composable
fun ContentCard(
    @DrawableRes cardImage: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(5),
        shadowElevation = 10.dp,
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
    ) {
        Box {
            Column {
                Image(
                    painterResource(id = cardImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .weight(0.75f)
                        .fillMaxSize()
                )
                Divider(
                    color = Color.Black,
                    thickness = 2.dp,
                    modifier = Modifier.weight(0.01f)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(0.3f)
                ) {
                    Image(
                        painterResource(id = R.drawable.text_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        stringResource(id = text),
                        fontSize = 12.sp,
                        fontFamily = gwentFontFamily,
                        softWrap = true,
                        textAlign = TextAlign.Center,
                        lineHeight = 10.sp,
                        modifier = Modifier.fillMaxSize()

                    )

                }
            }
        }
    }
}

@Composable
fun BackCard(
    @DrawableRes backImage: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(5),
        shadowElevation = 10.dp,
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
    ) {
        Column{
            Image(
                painterResource(id = backImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    //.weight(0.75f)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlipCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit,
    play: Boolean,
    matched:Boolean,
    modifier: Modifier = Modifier
) {
    @RawRes val animation:Int = if(matched) R.raw.good_animation else R.raw.bad_animation
    var isAnimationVisible by rememberSaveable { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))
    var isPlaying by remember { mutableStateOf(play) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(400, easing = FastOutSlowInEasing), label = ""
    )
    LaunchedEffect(key1 = progress, key2 = play) {
        isAnimationVisible = play
        if (progress == 1f) {
            isAnimationVisible = false
        } else if (progress == 0f) {
            isPlaying = false
            isAnimationVisible = false
        }
        isPlaying = play
    }
    Card(
        onClick = { onClick(cardFace) },
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier
            .height(132.dp)
            .graphicsLayer(rotationY = rotation.value)
            .then(modifier)
    ) {
        Box {
            if (rotation.value <= 90f) {
                front()
                if (isAnimationVisible) {
                    LottieAnimation(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.TopEnd),
                        composition = composition,
                        progress = progress,
                    )
                }
            } else {
                back()
            }

        }
    }
}


data class CardData(
    @StringRes val text: Int = R.string.eredin,
    @DrawableRes val image: Int = R.drawable.eredin_card,
    var id: Int = -1,
    var found: Boolean = false,
    var callBack: suspend (Boolean) -> Unit = {}
)
