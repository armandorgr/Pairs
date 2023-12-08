package com.example.pairs.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pairs.R
import com.example.pairs.ui.theme.gwentFontFamily
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton


@Composable
fun PlayersCard(
    @DrawableRes image: Int,
    text: String,
    isReversed: Boolean,
    modifier: Modifier = Modifier
) {
    val cornerShapeNormal = RoundedCornerShape(
        topStart = 10.dp,
        bottomStart = 10.dp)
    val cornerShapeReversed = RoundedCornerShape(
        topEnd = 10.dp,
        bottomEnd = 10.dp)
    val corner = if (!isReversed) cornerShapeReversed else cornerShapeNormal
    Box(
        Modifier
            .clip(
                corner
            )
            .border(
                BorderStroke(2.dp, Color.Black),
                corner
            )
            .then(modifier)
    ) {
        Image(
            painterResource(id = R.drawable.players_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            val picture = @Composable {
                Image(
                    painterResource(id = image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(BorderStroke(2.dp, Color.Black), CircleShape)
                        .weight(0.7f)
                        .aspectRatio(1f)
                )
            }
            val textComposable = @Composable {
                Text(
                    text = text,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = gwentFontFamily,
                    textAlign = TextAlign.Center,
                    style = LocalTextStyle.current.plus(
                        TextStyle(
                            color = Color.Black,
                            shadow = Shadow(Color.Black, Offset(6f, 6f))
                        )
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
            if (isReversed) {
                textComposable()
                picture()
            } else {
                picture()
                textComposable()
            }
        }
    }
}

@Composable
fun VolumeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    ElevatedButton(
        shape = RoundedCornerShape(5.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF966F33),
        ),
        contentPadding = PaddingValues(15.dp),
        border = BorderStroke(2.dp, Color.Black),
        modifier = modifier
    ) {
        Icon(
            painterResource(id = R.drawable.speaker_icon),
            contentDescription = null
        )
    }
}

@Composable
fun PlayersPointsRow(
    player1: @Composable () -> Unit,
    player2: @Composable () -> Unit,
    button: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ){
        player1()
        button()
        player2()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayersCardPreview() {
    Column(Modifier.fillMaxSize()) {
        PlayersPointsRow(
            player1 = { PlayersCard(
                R.drawable.geralt_profile,
                isReversed = false,
                text = stringResource(id = R.string.jugador, 1, 0),
                modifier = Modifier
                    .size(height = 80.dp, width = 160.dp)
                    .weight(0.35f, fill = false)
            ) },
            player2 = {
                PlayersCard(
                    R.drawable.ciri_profile,
                    isReversed = true,
                    text = stringResource(id = R.string.jugador, 2, 0),
                    modifier = Modifier
                        .size(height = 80.dp, width = 160.dp)
                        .weight(0.35f, fill = false)
                )
            },
            button = { VolumeButton(onClick = {}, modifier = Modifier.weight(0.1f))},
            modifier = Modifier.weight(0.5f, fill = false))

    }
}