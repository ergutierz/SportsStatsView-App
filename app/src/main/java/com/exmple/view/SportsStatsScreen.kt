package com.exmple.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exmple.model.HomeStats
import com.exmple.model.VisStats
import com.exmple.viewmodel.SportsViewModel

@Composable
fun SportsStatsScreen(
    viewState: SportsViewModel.ViewState,
    onAction: (SportsViewModel.Action) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = Color.LightGray,
                onClick = {
                    onAction(SportsViewModel.Action.FetchTeamStats)
                }
            ) {
                Icon(
                    modifier = Modifier.size(45.dp),
                    painter = rememberVectorPainter(Icons.Filled.PlayArrow),
                    tint = Color.Green,
                    contentDescription = "Refresh"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                viewState.sportsResponse != null -> SportsStats(viewState)
                viewState.isLoading -> LoadingBar()
                viewState.displayError -> ErrorDialog(onAction)
            }
        }
    }
}

@Composable
private fun SportsStats(viewState: SportsViewModel.ViewState) {
    viewState.sportsResponse?.matchUpStats?.mapNotNull { it }?.let { matchUpStats ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(matchUpStats) { matchUpStat ->
                StatsCard(
                    visTeamName = matchUpStat.visTeamName.orEmpty(),
                    visTeamStats = matchUpStat.visStats,
                    homeTeamName = matchUpStat.homeTeamName.orEmpty(),
                    homeTeamStats = matchUpStat.homeStats
                )
            }
        }
    }
}

@Composable
private fun StatsCard(
    visTeamName: String,
    visTeamStats: VisStats?,
    homeTeamName: String,
    homeTeamStats: HomeStats?
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "$visTeamName vs $homeTeamName",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatsColumn(
                    teamName = visTeamName,
                    score = visTeamStats?.score ?: 0,
                    firstDowns = visTeamStats?.firstDowns ?: 0,
                    passingYards = visTeamStats?.passYds ?: 0,
                    rushingYards = visTeamStats?.rushYds ?: 0
                )
                StatsColumn(
                    teamName = homeTeamName,
                    score = homeTeamStats?.score ?: 0,
                    firstDowns = homeTeamStats?.firstDowns ?: 0,
                    passingYards = homeTeamStats?.passYds ?: 0,
                    rushingYards = homeTeamStats?.rushYds ?: 0
                )
            }
        }
    }
}

@Composable
private fun StatsColumn(
    teamName: String,
    score: Int,
    firstDowns: Int,
    passingYards: Int,
    rushingYards: Int
) {
    Column {
        Text(
            text = teamName,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "First Downs: $firstDowns",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Passing Yards: $passingYards",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Rushing Yards: $rushingYards",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun LoadingBar() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorDialog(
    onAction: (SportsViewModel.Action) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onAction(SportsViewModel.Action.DismissError)
        },
        title = {
            Text(text = "Error")
        },
        text = {
            Text(text = "Please try again later")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAction(SportsViewModel.Action.DismissError)
                }
            ) {
                Text(text = "OK")
            }
        }
    )
}