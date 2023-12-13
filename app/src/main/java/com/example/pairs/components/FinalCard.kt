package com.example.pairs.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pairs.R
import com.example.pairs.dataclasses.Ganador
import com.example.pairs.ui.theme.gwentFontFamily

@Composable
fun FinalCard(
    ganador: Ganador,
    onSalir: () -> Unit,
    onVolverJugar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.darkGray)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        border = BorderStroke(4.dp, Color.Black),
        modifier = Modifier
            .padding(20.dp)
            .then(modifier)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 20.dp)
        ) {
            Text(
                stringResource(id = R.string.ganador, ganador.jugadorId),
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = gwentFontFamily,
                textAlign = TextAlign.Center,
                style = LocalTextStyle.current.plus(
                    TextStyle(
                        color = Color.Black,
                        shadow = Shadow(Color.Black, Offset(6f, 6f))
                    )
                ),
            )
            Image(
                painterResource(id = ganador.picture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(BorderStroke(2.dp, Color.Black), CircleShape)
                    .size(250.dp)
                    .aspectRatio(1f)
            )
            Text(
                stringResource(id = R.string.final_message, ganador.points),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = gwentFontFamily,
                textAlign = TextAlign.Center,
                style = LocalTextStyle.current.plus(
                    TextStyle(
                        color = Color.Black,
                        shadow = Shadow(Color.Black, Offset(6f, 6f))
                    )
                ),
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            ) {
                ElevatedButton(
                    shape = RoundedCornerShape(5.dp),
                    onClick = { onSalir() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF966F33),
                    ),
                    contentPadding = PaddingValues(15.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = modifier
                ) {
                    Text(
                        stringResource(id = R.string.salir),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = gwentFontFamily,
                        textAlign = TextAlign.Center,
                        style = LocalTextStyle.current.plus(
                            TextStyle(
                                color = Color.Black,
                                shadow = Shadow(Color.Black, Offset(6f, 6f))
                            )
                        )
                    )
                }
                ElevatedButton(
                    shape = RoundedCornerShape(5.dp),
                    onClick = { onVolverJugar() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF966F33),
                    ),
                    contentPadding = PaddingValues(15.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = modifier
                ) {
                    Text(
                        stringResource(
                            id = R.string.volver_a_jugar
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = gwentFontFamily,
                        textAlign = TextAlign.Center,
                        style = LocalTextStyle.current.plus(
                            TextStyle(
                                color = Color.Black,
                                shadow = Shadow(Color.Black, Offset(6f, 6f))
                            )
                        )
                    )

                }
            }
        }
    }
}

