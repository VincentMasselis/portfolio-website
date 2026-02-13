package com.masselis.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.me

@Composable
internal fun MyselfImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(Res.drawable.me),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}