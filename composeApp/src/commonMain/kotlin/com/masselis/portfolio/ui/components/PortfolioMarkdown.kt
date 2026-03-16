package com.masselis.portfolio.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.MarkdownColors

@Composable
internal fun PortfolioMarkdown(
    text: String,
    colors: MarkdownColors = markdownColor(
        text = LocalContentColor.current
    ),
    paragraphTypography: TextStyle = MaterialTheme.typography.bodyMedium,
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    Markdown(
        content = text,
        modifier = modifier,
        colors = colors,
        typography = markdownTypography(
            paragraph = paragraphTypography,
            textLink = TextLinkStyles(
                style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                )
            ),
        ),
    )
}


