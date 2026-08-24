package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.BadgePill
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(initialTab) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "À Propos & Mentions",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MinimalTextPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("about_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = MinimalTextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            shareAppInfo(context)
                        },
                        modifier = Modifier.testTag("about_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Partager l'application",
                            tint = Grenat
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tabs: À Propos vs Politique de Confidentialité
            PrimaryTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MinimalBackground,
                contentColor = Grenat,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(selectedTab),
                        color = Grenat
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "À Propos & Vision",
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PrivacyTip,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Confidentialité",
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            when (selectedTab) {
                0 -> AboutTabContent(context = context)
                1 -> PrivacyPolicyTabContent(context = context)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AboutTabContent(context: Context) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Hero Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MinimalSurface),
                border = BorderStroke(1.dp, Moutarde.copy(alpha = 0.6f))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sekolin_emblem),
                        contentDescription = "Emblème Sekolin'ny Fiainana",
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.5.dp, Moutarde, RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Sekolin'ny Fiainana",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = GrenatProfond
                        )
                    )

                    Text(
                        text = "L'École de la Vie · Guide Pratique d'Autonomie",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MinimalTextSecondary,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BadgePill(
                            text = "Version 1.0.0",
                            backgroundColor = MinimalSecondaryContainer,
                            textColor = MinimalOnPrimaryContainer
                        )
                        BadgePill(
                            text = "100% Hors-ligne",
                            backgroundColor = VertDoux,
                            textColor = VertSucces
                        )
                        BadgePill(
                            text = "8 Cahiers · 160 Planches",
                            backgroundColor = MoutardeClaire,
                            textColor = GrenatProfond
                        )
                    }
                }
            }
        }

        // Section: L'Auteur
        item {
            AboutSectionCard(
                title = "L'Auteur",
                icon = Icons.Default.Person,
                accentColor = Grenat
            ) {
                Text(
                    text = "Safidy Raharijesy",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GrenatProfond
                    )
                )

                Text(
                    text = "Auteur, formateur, Designer et ingénieur logiciel — Antananarivo, Madagascar",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MinimalTextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Concepteur d'interfaces et d'outils numériques, il travaille depuis plusieurs années sur des projets d'entreprise à Madagascar : plateformes métier, applications de terrain, systèmes de gestion. Il est aussi le fondateur de SFD Consulting.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MinimalTextPrimary,
                        lineHeight = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Cette collection est née d'un constat personnel : tout ce qu'il a dû apprendre seul sur l'argent, la négociation, les contrats ou la façon de se relever après un échec, il l'a appris tard, et souvent en le payant cher.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MinimalTextPrimary,
                        lineHeight = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Il a voulu que les jeunes Malgaches disposent, dès seize ans, de ce que personne ne leur enseigne : des méthodes claires, illustrées, avec des exemples d'ici et des montants en ariary. Huit cahiers, cent soixante planches, seize ateliers — écrits, dessinés et mis en page par une seule personne, sur son temps libre.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MinimalTextPrimary,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        // Section: Sa Démarche
        item {
            AboutSectionCard(
                title = "Sa Démarche",
                icon = Icons.Default.Explore,
                accentColor = Moutarde
            ) {
                val principles = listOf(
                    Triple(
                        "Concevoir ici, pour ici",
                        "Chaque exemple, chaque montant, chaque situation vient du contexte malgache.",
                        Icons.Default.LocationOn
                    ),
                    Triple(
                        "Une méthode par page",
                        "Ce qui ne tient pas sur une planche n'est pas encore assez clair.",
                        Icons.Default.Filter1
                    ),
                    Triple(
                        "Montrer avant d'expliquer",
                        "Un schéma comprend en cinq secondes ce qu'un paragraphe met une page à dire.",
                        Icons.Default.Visibility
                    ),
                    Triple(
                        "Rester utilisable",
                        "Chaque planche se termine par une chose à essayer dans la semaine.",
                        Icons.Default.TaskAlt
                    )
                )

                principles.forEachIndexed { idx, (title, desc, icon) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurfaceContainer.copy(alpha = 0.6f)),
                        border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Moutarde.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = GrenatProfond,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GrenatProfond
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MinimalTextSecondary,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section: La Vision & La Mission
        item {
            AboutSectionCard(
                title = "La Vision & La Mission",
                icon = Icons.Default.Lightbulb,
                accentColor = Olive
            ) {
                Text(
                    text = "Sekolin'ny Fiainana (L'École de la Vie) est un projet d'éducation populaire et d'autonomie personnelle fondé sur une conviction essentielle : les clés de la réussite financière, professionnelle, relationnelle et citoyenne doivent être accessibles à tous, de manière claire, visuelle et immédiatement applicable.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MinimalTextPrimary,
                        lineHeight = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "L'œuvre synthétise 8 domaines cruciaux en 160 planches synthétiques avec des ateliers concrets, des maximes d'action et des quiz d'auto-évaluation pour transformer la connaissance théorique en réflexes du quotidien.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MinimalTextPrimary,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        // Section: Les 8 Piliers d'Apprentissage
        item {
            AboutSectionCard(
                title = "Les 8 Piliers du Savoir (160 Planches)",
                icon = Icons.Default.AutoStories,
                accentColor = Grenat
            ) {
                val pillars = listOf(
                    "Cahier 01" to "Mon Argent, Mes Choix — Budget, épargne, investissement & maîtrise financière",
                    "Cahier 02" to "Gagner & Construire — Revenus, compétences de pointe, négociation & valeur",
                    "Cahier 03" to "Empreinte & Relations — Réseau, écoute active, communication & réputation",
                    "Cahier 04" to "Corps & Esprit — Énergie vitale, sommeil, clarté mentale & discipline",
                    "Cahier 05" to "Le Pouvoir du Temps — Priorités, focus profond, arbitrage & long terme",
                    "Cahier 06" to "Résilience & Tempêtes — Épreuves, gestion de crise, rebond & stoïcisme",
                    "Cahier 07" to "Le Monde & Ses Règles — Droit pratique, institutions, contrats & citoyenneté",
                    "Cahier 08" to "Transmettre & Laisser — Mentorat, impact durable, héritage & sagesse"
                )

                pillars.forEachIndexed { idx, (cahier, desc) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MinimalSurfaceContainer.copy(alpha = 0.4f)),
                        border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Grenat),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${idx + 1}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Blanc,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = cahier,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GrenatProfond
                                    )
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MinimalTextSecondary,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section: Le Contacter
        item {
            AboutSectionCard(
                title = "Le Contacter",
                icon = Icons.Default.Mail,
                accentColor = Grenat
            ) {
                Text(
                    text = "Pour les retours de lecteurs, les échanges avec les écoles, universités et organisations :",
                    style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextSecondary)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Email card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSecondaryContainer),
                    border = BorderStroke(1.dp, MinimalOutline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = Grenat,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "sekolinyfiainana@gmail.com",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GrenatProfond
                                )
                            )
                        }

                        Row {
                            IconButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Email Sekolin'ny Fiainana", "sekolinyfiainana@gmail.com")
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Email copié !", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copier l'email",
                                    tint = MinimalTextPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    sendEmail(context, "sekolinyfiainana@gmail.com")
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Envoyer un email",
                                    tint = Grenat,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Telephone card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSecondaryContainer),
                    border = BorderStroke(1.dp, MinimalOutline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Grenat,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "+261 34 94 925 58",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GrenatProfond
                                )
                            )
                        }

                        Row {
                            IconButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Téléphone", "+261349492558")
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "Numéro copié !", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copier le numéro",
                                    tint = MinimalTextPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    try {
                                        val intent = Intent(Intent.ACTION_DIAL).apply {
                                            data = Uri.parse("tel:+261349492558")
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Impossible de composer le numéro", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Appeler",
                                    tint = Grenat,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Facebook card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MinimalSecondaryContainer),
                    border = BorderStroke(1.dp, MinimalOutline),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            try {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.facebook.com/search/top?q=Sekolin%27ny%20Fiainana")
                                )
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Recherche Facebook : Sekolin'ny Fiainana", Toast.LENGTH_SHORT).show()
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = null,
                                tint = Grenat,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Facebook : Sekolin'ny Fiainana",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GrenatProfond
                                    )
                                )
                                Text(
                                    text = "Pour les retours de lecteurs, les écoles et les organisations",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MinimalTextSecondary,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = "Ouvrir",
                            tint = Grenat,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { shareAppInfo(context) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Grenat, contentColor = Blanc)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = Blanc,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Partager l'application avec un proche",
                        color = Blanc,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Section: Mentions Légales & Droits
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MinimalSurfaceContainer.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Mentions & Droits d'auteur",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MinimalTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "© 2026 Sekolin'ny Fiainana. Tous droits réservés. L'ensemble des textes, maquettes et représentations visuelles sont protégés par le droit de la propriété intellectuelle. Destiné à un usage personnel et pédagogique.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MinimalTextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun PrivacyPolicyTabContent(context: Context) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Privacy Hero summary
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = VertDoux),
                border = BorderStroke(1.dp, VertSucces.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(VertSucces),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Blanc,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Garantie Vie Privée & Zéro Collecte",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = VertSucces
                            )
                        )
                        Text(
                            text = "Vos données restent à 100% sur votre appareil. Aucun serveur distant, aucun traqueur publicitaire.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Encre,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }
            }
        }

        // Section 1: Introduction
        item {
            PrivacySectionCard(
                number = "1",
                title = "Engagement de Confidentialité"
            ) {
                Text(
                    text = "La présente Politique de Confidentialité s'applique à l'application mobile Sekolin'ny Fiainana (L'École de la Vie), conçue pour offrir un contenu éducatif complet en mode autonome.\n\nNous nous engageons fermement à respecter la vie privée de nos utilisateurs conformément au Règlement Général sur la Protection des Données (RGPD) et aux exigences du Google Play Store.",
                    style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextPrimary, lineHeight = 20.sp)
                )
            }
        }

        // Section 2: Données Collectées
        item {
            PrivacySectionCard(
                number = "2",
                title = "Nature des Données & Stockage Local"
            ) {
                Text(
                    text = "L'application ne requiert aucune création de compte, aucun identifiant, aucune adresse email obligatoire et ne collecte aucune donnée à caractère personnel nominative.\n\nLes éléments suivants sont stockés UNIQUEMENT sur la mémoire locale de votre appareil (via la base de données Room) :",
                    style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextPrimary, lineHeight = 20.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                BulletPoint(text = "Progression de lecture (planches lues / non lues)")
                BulletPoint(text = "Signets et planches épinglées en favoris")
                BulletPoint(text = "Notes personnelles et réflexions saisies par l'utilisateur")
                BulletPoint(text = "Réponses et formulaires d'ateliers pratiques")
                BulletPoint(text = "Scores et historiques de quiz d'auto-évaluation")
                BulletPoint(text = "Objectifs d'habitude et heure du rappel quotidien")
            }
        }

        // Section 3: Permissions Android
        item {
            PrivacySectionCard(
                number = "3",
                title = "Permissions Système Requises"
            ) {
                Text(
                    text = "L'application utilise un ensemble strictement minimal d'autorisations Android nécessaires à son bon fonctionnement :",
                    style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextPrimary, lineHeight = 20.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                PermissionExplanation(
                    permission = "POST_NOTIFICATIONS (Android 13+)",
                    purpose = "Permet d'afficher la notification locale de rappel de lecture programmée par l'utilisateur à l'heure souhaitée. Aucun service de push distant n'est utilisé."
                )
                Spacer(modifier = Modifier.height(6.dp))
                PermissionExplanation(
                    permission = "RECEIVE_BOOT_COMPLETED & SCHEDULE_EXACT_ALARM",
                    purpose = "Permet à l'AlarmManager de reprogrammer l'alarme de rappel quotidienne locale après le redémarrage de l'appareil de l'utilisateur."
                )
            }
        }

        // Section 4: Absence de Tiers & Publicité
        item {
            PrivacySectionCard(
                number = "4",
                title = "Pas de Tiers, Pas de Publicité, Pas de Traqueurs"
            ) {
                Text(
                    text = "• Aucun SDK publicitaire : L'application est exempte de toute bannière ou publicité tierce.\n• Aucun traqueur analytique externe : Vos habitudes de lecture ne sont ni transmises, ni vendues, ni profilées.\n• Aucun cookie : Aucune technologie de traçage n'est intégrée.",
                    style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextPrimary, lineHeight = 20.sp)
                )
            }
        }

        // Section 5: Suppression & Contrôle des Données
        item {
            PrivacySectionCard(
                number = "5",
                title = "Contrôle & Suppression des Données"
            ) {
                Text(
                    text = "Comme l'ensemble des données est strictement consigné dans le stockage isolé de l'application sur votre appareil :\n\n• Vous pouvez réinitialiser vos ateliers ou notes depuis l'interface à tout moment.\n• La désinstallation de l'application ou l'effacement du stockage via les Paramètres d'Android supprime définitivement toutes vos données locales sans laisser aucune trace.",
                    style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextPrimary, lineHeight = 20.sp)
                )
            }
        }

        // Section 6: Contact & Délégué à la Protection des Données
        item {
            PrivacySectionCard(
                number = "6",
                title = "Contact & Délégué à la Protection des Données"
            ) {
                Text(
                    text = "Pour toute question relative à cette politique de confidentialité ou pour toute demande d'information supplémentaire :\n\nResponsable / Contact DPO :\nsekolinyfiainana@gmail.com\n\nDernière mise à jour : Août 2026",
                    style = MaterialTheme.typography.bodySmall.copy(color = MinimalTextPrimary, lineHeight = 20.sp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = { sendEmail(context, "sekolinyfiainana@gmail.com") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Contacter le responsable de confidentialité")
                }
            }
        }
    }
}

@Composable
private fun AboutSectionCard(
    title: String,
    icon: ImageVector,
    accentColor: androidx.compose.ui.graphics.Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
        border = BorderStroke(1.dp, MinimalOutline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MinimalTextPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
private fun PrivacySectionCard(
    number: String,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MinimalSurface),
        border = BorderStroke(1.dp, MinimalOutline)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(Grenat),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Blanc,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MinimalTextPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            content()
        }
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Grenat
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MinimalTextSecondary,
                lineHeight = 18.sp
            )
        )
    }
}

@Composable
private fun PermissionExplanation(
    permission: String,
    purpose: String
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MinimalSurfaceContainer),
        border = BorderStroke(1.dp, MinimalOutline.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = permission,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = GrenatProfond
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = purpose,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MinimalTextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            )
        }
    }
}

private fun shareAppInfo(context: Context) {
    try {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Découvrez « Sekolin'ny Fiainana » (L'École de la Vie) : le guide pratique d'autonomie en 8 cahiers et 160 planches pour maîtriser vos finances, votre temps, vos relations et vos projets. Contact : sekolinyfiainana@gmail.com"
            )
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Partager Sekolin'ny Fiainana")
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Impossible d'ouvrir le menu de partage.", Toast.LENGTH_SHORT).show()
    }
}

private fun sendEmail(context: Context, email: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, "Sekolin'ny Fiainana - Contact & Retours")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback to copying
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Email", email)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Email copié : $email", Toast.LENGTH_LONG).show()
    }
}
