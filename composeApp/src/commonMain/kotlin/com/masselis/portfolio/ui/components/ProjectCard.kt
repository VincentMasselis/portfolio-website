package com.masselis.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.Project
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier,
) {
    var showDetails by rememberSaveable { mutableStateOf(false) }
    Card(
        onClick = { showDetails = true },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(project.image),
            contentDescription = project.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(project.logo),
                    contentDescription = project.title,
                    modifier = Modifier.height(20.dp),
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = project.bulletPoints.joinToString(separator = "\n") { "• $it" },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 2.dp),
            )
        }
    }
    if (showDetails) {
        DetailDialog(
            project = project,
            onDismissRequest = { showDetails = false }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailDialog(
    project: Project,
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = AlertDialogDefaults.shape,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            modifier = Modifier.heightIn(max = 560.dp),
        ) {
            val scrollState = rememberScrollState()
            Box {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                ) {
                    Image(
                        painter = painterResource(project.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Column(modifier = Modifier.padding(24.dp)) {
                        SelectionContainer {
                            Text(
                                text = project.title,
                                style = MaterialTheme.typography.headlineSmall,
                                color = AlertDialogDefaults.titleContentColor,
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        SelectionContainer {
                            Text(
                                text = project.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = AlertDialogDefaults.textContentColor,
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            project.skills.forEach { skill ->
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary,
                                ) {
                                    Text(
                                        text = skill.name,
                                        modifier = Modifier.padding(4.dp, 2.dp)
                                    )
                                }
                                Spacer(Modifier.width(4.dp))
                            }
                        }
                        Spacer(Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = onDismissRequest) { Text("OK") }
                        }
                    }
                }
                VerticalScrollbar(
                    scrollState = scrollState,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight(),
                )
            }
        }
    }
}
