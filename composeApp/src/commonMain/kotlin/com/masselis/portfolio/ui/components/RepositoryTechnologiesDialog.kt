package com.masselis.portfolio.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation.Url
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle

@Composable
internal fun RepositoryTechnologiesDialog(
    onDismissRequest: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    AlertDialog(
        text = {
            Text(
                buildAnnotatedString {
                    append("Ce site web a été fabriqué en utilisant les dernières technologies de ")
                    withLink(Url("https://kotlinlang.org/multiplatform")) {
                        withStyle(style = SpanStyle(fontWeight = Bold)) {
                            append("Kotlin et Compose Multiplatform")
                        }
                    }
                    append(". Grâce à cela, ce site est capable de fonctionner nativement sur Android, Web et iOS en utilisant")
                    withStyle(style = SpanStyle(fontWeight = Bold)) {
                        append(" le même code source.")
                    }
                }
            )
        },
        onDismissRequest = onDismissRequest,
        dismissButton = {
            TextButton({
                uriHandler.openUri("https://github.com/VincentMasselis/portfolio-website")
                onDismissRequest()
            }) {
                Text("Voir le code source")
            }
        },
        confirmButton = {
            TextButton(onDismissRequest) {
                Text("OK")
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}