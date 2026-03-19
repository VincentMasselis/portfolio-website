package com.masselis.portfolio.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownParagraph
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.MarkdownColors
import com.mikepenz.markdown.model.MarkdownInlineContent
import com.mikepenz.markdown.model.markdownAnnotator
import com.mikepenz.markdown.model.markdownInlineContent
import com.mikepenz.markdown.utils.getUnescapedTextInNode
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.ast.findChildOfType

@Composable
internal fun PortfolioMarkdown(
    text: String,
    colors: MarkdownColors = markdownColor(
        text = LocalContentColor.current
    ),
    paragraphTypography: TextStyle = MaterialTheme.typography.bodyMedium,
    inlineContent: MarkdownInlineContent = markdownInlineContent(),
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val annotator = remember(primaryColor) {
        markdownAnnotator { content, child ->
            if (child.type != MarkdownElementTypes.INLINE_LINK)
                return@markdownAnnotator false

            val linkLabel = child
                .findChildOfType(MarkdownElementTypes.LINK_TEXT)
                ?.getUnescapedTextInNode(content)
                ?: return@markdownAnnotator false

            val destination = child
                .findChildOfType(MarkdownElementTypes.LINK_DESTINATION)
                ?.getUnescapedTextInNode(content)
                ?: return@markdownAnnotator false

            when (linkLabel) {
                "[primaryColor]" -> {
                    withStyle(SpanStyle(color = primaryColor)) {
                        append(destination)
                    }
                    true
                }

                "[inline]" -> {
                    appendInlineContent(destination)
                    true
                }

                else -> false
            }
        }
    }
    Markdown(
        content = text,
        colors = colors,
        annotator = annotator,
        inlineContent = inlineContent,
        typography = markdownTypography(
            paragraph = paragraphTypography,
            textLink = TextLinkStyles(
                style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                )
            ),
        ),
        components = markdownComponents(
            paragraph = {
                MarkdownParagraph(
                    content = it.content,
                    node = it.node,
                    style = it.typography.paragraph,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        ),
        modifier = modifier,
    )
}


