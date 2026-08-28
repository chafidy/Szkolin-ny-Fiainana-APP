package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Cahier
import com.example.data.repository.BookData
import com.example.ui.components.BadgePill
import com.example.ui.components.BrandLogo
import com.example.ui.components.BrandProgressBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.ProgressUiState
import com.example.ui.viewmodel.ReaderUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    progressState: ProgressUiState,
    readerState: ReaderUiState,
    onSelectCahier: (Int) -> Unit,
    onOpenPlanche: (Int, Int) -> Unit,
    onToggleBookmark: (String, Int) -> Unit = { _, _ -> },
    onNavigateToProgress: () -> Unit,
    onNavigateToAteliers: () -> Unit,
    onNavigateToQuiz: (Int) -> Unit,
    onNavigateToLexique: (Int) -> Unit,
    onNavigateToAbout: (Int) -> Unit = {},
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bookmarkedPlanches = remember(readerState.bookmarkedIds) {
        readerState.bookmarkedIds.mapNotNull { BookData.getPlancheById(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_sekolin_emblem),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, MinimalOutline, RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Sekolin'ny Fiainana",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary
                                )
                            )
                            Text(
                                text = "L'École de la Vie · 8 Cahiers",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MinimalTextSecondary
                                )
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier.testTag("home_search_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Rechercher une notion",
                            tint = MinimalTextPrimary
                        )
                    }
                    IconButton(
                        onClick = { onNavigateToAbout(0) },
                        modifier = Modifier.testTag("home_about_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "À Propos & Mentions",
                            tint = MinimalTextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MinimalBackground
                )
            )
        },
        containerColor = MinimalBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Hero Brand Header Banner (Charter: Fond grenat + texte crème + accent moutarde)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("hero_brand_banner"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Grenat),
                    border = BorderStroke(1.dp, GrenatProfond)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "COLLECTION COMPLÈTE · 8 CAHIERS",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Moutarde,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Ce que l'école ne t'apprend pas",
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        color = Creme,
                                        fontWeight = FontWeight.ExtraBold,
                                        lineHeight = 28.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Apprendre. Comprendre. Agir. Réussir.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Creme.copy(alpha = 0.9f),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            Image(
                                painter = painterResource(id = R.drawable.ic_sekolin_emblem),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, Moutarde.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Progress line inside hero
                        BrandProgressBar(
                            progress = progressState.totalPlanchesRead.toFloat() / 160f,
                            height = 8.dp,
                            barColor = Moutarde,
                            trackColor = Creme.copy(alpha = 0.2f)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = Moutarde,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${progressState.currentStreak} j",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Creme,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Moutarde,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${progressState.totalPlanchesRead}/160 lues",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Creme,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }

                            Button(
                                onClick = onNavigateToProgress,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Moutarde,
                                    contentColor = Encre
                                ),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("home_view_progress_btn")
                            ) {
                                Text(
                                    text = "Suivi",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }
                        }
                    }
                }
            }

            // Quick Continue Reading Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOpenPlanche(readerState.currentCahierId, readerState.currentPlancheIndex)
                        }
                        .testTag("continue_reading_card"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Blanc),
                    border = BorderStroke(1.dp, BordureLegere)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Creme),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Grenat,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "REPRENDRE LA LECTURE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OliveProfond,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )
                            val currentCahier = BookData.cahiers.find { it.id == readerState.currentCahierId } ?: BookData.cahiers.first()
                            val currentPlanches = BookData.getPlanchesForCahier(currentCahier.id)
                            val currentPlanche = currentPlanches.getOrNull(readerState.currentPlancheIndex) ?: currentPlanches.firstOrNull()
                            Text(
                                text = currentPlanche?.title ?: "Introduction",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GrenatProfond
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Cahier ${currentCahier.number} · ${currentCahier.title}",
                                style = MaterialTheme.typography.bodySmall.copy(color = EncreMoyenne),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = EncreMoyenne
                        )
                    }
                }
            }

            // Section: Mes Signets & Favoris (Planches Épinglées)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp, end = 2.dp, top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = Grenat,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "MES SIGNETS & FAVORIS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = GrenatProfond
                            )
                        )
                    }

                    if (bookmarkedPlanches.isNotEmpty()) {
                        BadgePill(
                            text = "${bookmarkedPlanches.size} épinglée${if (bookmarkedPlanches.size > 1) "s" else ""}",
                            backgroundColor = Moutarde.copy(alpha = 0.2f),
                            textColor = GrenatProfond
                        )
                    }
                }
            }

            if (bookmarkedPlanches.isNotEmpty()) {
                items(bookmarkedPlanches) { planche ->
                    val cahier = BookData.cahiers.find { it.id == planche.cahierId } ?: BookData.cahiers.first()
                    val plancheIndex = BookData.getPlancheIndexInCahier(planche)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelectCahier(planche.cahierId)
                                onOpenPlanche(planche.cahierId, plancheIndex)
                            }
                            .testTag("pinned_planche_${planche.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                        border = BorderStroke(1.dp, Moutarde.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Grenat),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = cahier.number,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = Blanc,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Cahier ${cahier.number} · ${cahier.title}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MinimalTextSecondary,
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                IconButton(
                                    onClick = { onToggleBookmark(planche.id, planche.cahierId) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Retirer des favoris",
                                        tint = Grenat,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = planche.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MinimalTextPrimary
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = planche.accroche,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MinimalTextSecondary,
                                    lineHeight = 17.sp
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BadgePill(
                                    text = planche.sectionNumber,
                                    backgroundColor = MinimalSecondaryContainer,
                                    textColor = MinimalOnPrimaryContainer
                                )

                                Button(
                                    onClick = {
                                        onSelectCahier(planche.cahierId)
                                        onOpenPlanche(planche.cahierId, plancheIndex)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Grenat,
                                        contentColor = Blanc
                                    ),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoStories,
                                        contentDescription = null,
                                        tint = Blanc,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Lire en 1 clic",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Blanc,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurfaceContainer.copy(alpha = 0.5f)),
                        border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MinimalSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.BookmarkBorder,
                                    contentDescription = null,
                                    tint = MinimalTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Aucune planche épinglée pour le moment",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MinimalTextPrimary
                                    )
                                )
                                Text(
                                    text = "En lisant une planche, touchez l'icône de signet pour l'épingler et y revenir en un clic.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MinimalTextSecondary,
                                        lineHeight = 16.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Section Header: Les 8 Cahiers
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LES 8 CAHIERS THÉMATIQUES",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = GrenatProfond
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Chaque cahier est autonome et immédiatement applicable",
                            style = MaterialTheme.typography.bodySmall.copy(color = EncreMoyenne)
                        )
                    }
                }
            }

            // List of the 8 Cahiers
            items(BookData.cahiers) { cahier ->
                val summary = progressState.cahiersProgress.find { it.cahierId == cahier.id }
                val readPlanches = summary?.planchesReadCount ?: 0
                val totalPlanches = summary?.totalPlanches ?: 20
                val progressPercent = summary?.completionPercentage ?: 0
                val quizScore = summary?.quizScore
                val isQuizPassed = summary?.isQuizPassed ?: false

                CahierCard(
                    cahier = cahier,
                    readPlanches = readPlanches,
                    totalPlanches = totalPlanches,
                    progressPercent = progressPercent,
                    quizScore = quizScore,
                    isQuizPassed = isQuizPassed,
                    onOpen = {
                        onSelectCahier(cahier.id)
                        onOpenPlanche(cahier.id, 0)
                    },
                    onQuiz = { onNavigateToQuiz(cahier.id) },
                    onLexique = { onNavigateToLexique(cahier.id) }
                )
            }

            // Method & Advice Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("method_card"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Creme),
                    border = BorderStroke(1.dp, BordureLegere)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TipsAndUpdates,
                                contentDescription = null,
                                tint = Grenat,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "COMMENT UTILISER CETTE COLLECTION",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GrenatProfond,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "« Ne lis pas les huit cahiers à la suite. Prends une planche, applique son conseil pendant une semaine, puis passe à la suivante. Huit cahiers lus valent moins qu'une seule habitude installée. »",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Encre,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                lineHeight = 21.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "— Safidy Raharijesy · SFD Consulting",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GrenatProfond,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Section: À Propos, Mentions & Confidentialité Google Play
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_about_footer_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                    border = BorderStroke(1.dp, MinimalOutline)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Grenat,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "À Propos & L'Œuvre",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MinimalTextPrimary
                                    )
                                )
                            }
                            BadgePill(
                                text = "Édition 2026",
                                backgroundColor = CremeFonce,
                                textColor = GrenatProfond
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Sekolin'ny Fiainana est un recueil de 160 planches pratiques réparties en 8 cahiers pour développer autonomie financière, clarté mentale et résilience.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MinimalTextSecondary,
                                lineHeight = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onNavigateToAbout(0) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = Grenat,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Auteur & Vision",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            Button(
                                onClick = { onNavigateToAbout(1) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GrenatProfond,
                                    contentColor = Blanc
                                ),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PrivacyTip,
                                    contentDescription = null,
                                    tint = Blanc,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Confidentialité",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CahierCard(
    cahier: Cahier,
    readPlanches: Int,
    totalPlanches: Int,
    progressPercent: Int,
    quizScore: Int?,
    isQuizPassed: Boolean,
    onOpen: () -> Unit,
    onQuiz: () -> Unit,
    onLexique: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .testTag("cahier_card_${cahier.number}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Blanc),
        border = BorderStroke(1.dp, BordureLegere)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circle with Number
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Grenat),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cahier.number,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Blanc,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = cahier.title.uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GrenatProfond
                        )
                    )
                    Text(
                        text = cahier.theme,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = OliveProfond,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                BadgePill(
                    text = "$progressPercent%",
                    backgroundColor = if (progressPercent == 100) VertDoux else Creme,
                    textColor = if (progressPercent == 100) VertSucces else GrenatProfond
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = cahier.summary,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Encre,
                    lineHeight = 17.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            BrandProgressBar(
                progress = readPlanches.toFloat() / totalPlanches.coerceAtLeast(1),
                height = 6.dp,
                barColor = Moutarde
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action Pills Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$readPlanches / $totalPlanches lues",
                        style = MaterialTheme.typography.labelSmall.copy(color = EncreMoyenne),
                        maxLines = 1,
                        softWrap = false
                    )
                    if (isQuizPassed) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Quiz validé",
                            tint = VertSucces,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Creme,
                        border = BorderStroke(1.dp, BordureLegere),
                        modifier = Modifier.clickable { onLexique() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = Grenat,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Lexique",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GrenatProfond
                                ),
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isQuizPassed) VertDoux else MoutardeClaire,
                        border = BorderStroke(1.dp, if (isQuizPassed) VertSucces.copy(alpha = 0.4f) else Moutarde.copy(alpha = 0.4f)),
                        modifier = Modifier.clickable { onQuiz() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                tint = if (isQuizPassed) VertSucces else Grenat,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (quizScore != null) "Quiz: $quizScore/10" else "Quiz",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isQuizPassed) VertSucces else GrenatProfond
                                ),
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    }
                }
            }
        }
    }
}
