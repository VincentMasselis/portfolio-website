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
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.dialog_ok
import portfolio.composeapp.generated.resources.repo_dialog_intro
import portfolio.composeapp.generated.resources.repo_dialog_kmp_link
import portfolio.composeapp.generated.resources.repo_dialog_middle
import portfolio.composeapp.generated.resources.repo_dialog_same_source
import portfolio.composeapp.generated.resources.repo_dialog_see_source

@Composable
internal fun RepositoryTechnologiesDialog(
    onDismissRequest: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    AlertDialog(
        text = {
            Text(
                buildAnnotatedString {
                    append(stringResource(Res.string.repo_dialog_intro))
                    withLink(Url("https://kotlinlang.org/multiplatform")) {
                        withStyle(style = SpanStyle(fontWeight = Bold)) {
                            append(stringResource(Res.string.repo_dialog_kmp_link))
                        }
                    }
                    append(stringResource(Res.string.repo_dialog_middle))
                    withStyle(style = SpanStyle(fontWeight = Bold)) {
                        append(stringResource(Res.string.repo_dialog_same_source))
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
                Text(stringResource(Res.string.repo_dialog_see_source))
            }
        },
        confirmButton = {
            TextButton(onDismissRequest) {
                Text(stringResource(Res.string.dialog_ok))
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}