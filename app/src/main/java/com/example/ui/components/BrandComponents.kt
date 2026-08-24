package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.*
import com.example.ui.theme.*

@Composable
fun BrandLogo(
    modifier: Modifier = Modifier,
    isLightMode: Boolean = false,
    showSignature: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_sekolin_emblem),
            contentDescription = "Logo Sekolin'ny Fiainana",
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BordureLegere, RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SEKOLIN'NY FIAINANA",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp,
                color = if (isLightMode) GrenatProfond else Creme
            )
        )

        if (showSignature) {
            Text(
                text = "APPRENDRE. COMPRENDRE. AGIR. RÉUSSIR.",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 0.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isLightMode) Moutarde else Moutarde
                )
            )
        }
    }
}

@Composable
fun BadgePill(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Creme,
    textColor: Color = GrenatProfond,
    icon: ImageVector? = null
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, BordureLegere)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )
        }
    }
}

@Composable
fun BandeauBordeaux(
    advice: BottomAdvice,
    modifier: Modifier = Modifier
) {
    // Charter Page 12: Encadré Conseil: fond crème foncé ou olive clair, titre olive/grenat, action en moutarde
    val (bgColor, iconColor, borderColor) = when (advice.type) {
        AdviceType.SIGNAL_ALERTE -> Triple(RougeDoux, RougeAlerte, RougeAlerte.copy(alpha = 0.4f))
        AdviceType.REGLE_DOR -> Triple(MoutardeClaire, Moutarde, Moutarde.copy(alpha = 0.6f))
        AdviceType.EXERCICE_PRATIQUE -> Triple(OliveClair, OliveProfond, Olive.copy(alpha = 0.4f))
        AdviceType.BON_REFLEXE -> Triple(VertDoux, VertSucces, VertSucces.copy(alpha = 0.4f))
        else -> Triple(Creme, GrenatProfond, BordureLegere)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("bandeau_bordeaux"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (advice.type) {
                            AdviceType.SIGNAL_ALERTE -> Icons.Default.Warning
                            AdviceType.REGLE_DOR -> Icons.Default.Star
                            AdviceType.EXERCICE_PRATIQUE -> Icons.Default.Edit
                            AdviceType.BON_REFLEXE -> Icons.Default.CheckCircle
                            else -> Icons.Default.Lightbulb
                        },
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(14.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = advice.title.uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = if (advice.type == AdviceType.SIGNAL_ALERTE) RougeAlerte else GrenatProfond,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = advice.content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Encre,
                    lineHeight = 20.sp
                )
            )
        }
    }
}

@Composable
fun KeyCardItem(
    card: KeyCard,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("key_card_${card.code}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Blanc),
        border = BorderStroke(1.dp, BordureLegere)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Creme),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = card.code,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = GrenatProfond
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = GrenatProfond
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = card.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Encre,
                        lineHeight = 19.sp
                    )
                )
            }
        }
    }
}

@Composable
fun DetailedBoxCard(
    box: DetailedBox,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("detailed_box"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Creme),
        border = BorderStroke(1.dp, BordureLegere)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = box.title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = GrenatProfond,
                    letterSpacing = 0.25.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            box.items.forEach { item ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Moutarde)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Encre,
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun VisualSchemaView(
    schema: SchemaData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("schema_visual_view"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Blanc),
        border = BorderStroke(1.dp, BordureLegere)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            if (schema.title.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoGraph,
                        contentDescription = null,
                        tint = OliveProfond,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = schema.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = GrenatProfond
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            when (schema.type) {
                SchemaType.FLOW_STEPS -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        schema.items.forEachIndexed { index, item ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 2.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(if (index == schema.items.size - 1) Grenat else Creme),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.stepNumber,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = if (index == schema.items.size - 1) Blanc else GrenatProfond,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GrenatProfond
                                    ),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = item.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 10.sp,
                                        lineHeight = 13.sp,
                                        color = EncreMoyenne
                                    ),
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                SchemaType.COMPARISON -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        schema.comparisonLeft?.let { (title, desc) ->
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = RougeDoux),
                                border = BorderStroke(1.dp, RougeAlerte.copy(alpha = 0.3f)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = RougeAlerte
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = desc,
                                        style = MaterialTheme.typography.bodySmall.copy(color = Encre)
                                    )
                                }
                            }
                        }
                        schema.comparisonRight?.let { (title, desc) ->
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = VertDoux),
                                border = BorderStroke(1.dp, VertSucces.copy(alpha = 0.3f)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = VertSucces
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = desc,
                                        style = MaterialTheme.typography.bodySmall.copy(color = Encre)
                                    )
                                }
                            }
                        }
                    }
                }
                SchemaType.STAIRS_PROGRESSION -> {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        schema.items.forEachIndexed { idx, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Creme)
                                    .border(1.dp, BordureLegere, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(Moutarde),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${idx + 1}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Encre
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = GrenatProfond
                                        )
                                    )
                                    Text(
                                        text = item.description,
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = EncreMoyenne)
                                    )
                                }
                                Text(
                                    text = item.stepNumber,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = OliveProfond
                                    )
                                )
                            }
                        }
                    }
                }
                else -> {
                    schema.items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BadgePill(text = item.stepNumber, backgroundColor = Creme, textColor = GrenatProfond)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${item.title} : ${item.description}",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Encre)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BrandProgressBar(
    progress: Float, // 0f to 1f
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    barColor: Color = Moutarde, // Page 15: Progression : moutarde pour l'état actif
    trackColor: Color = CremeFonce
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(height / 2))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(height / 2))
                .background(barColor)
        )
    }
}
