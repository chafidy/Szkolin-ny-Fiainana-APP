package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// =========================================================================
// SEKOLIN'NY FIAINANA - CHARTE GRAPHIQUE OFFICIELLE (05 PALETTE COULEUR & 14 DIGITAL UI)
// =========================================================================

// 1. Couleurs Officielles de la Marque
val Grenat = Color(0xFF681923)          // Couleur signature • Boutons principaux • Titres forts (#681923)
val GrenatProfond = Color(0xFF4D111A)   // Contraste • Textes sur fonds clairs • Variantes sombres (#4D111A)
val Olive = Color(0xFF6F783A)           // Secondaire • Catégories • Pictogrammes (#6F783A)
val OliveProfond = Color(0xFF56602B)    // Contraste • Détails • Éléments secondaires (#56602B)
val OliveClair = Color(0xFFF1F4E4)      // Fond doux encadré conseil
val Moutarde = Color(0xFFD6A12A)        // Accent • Mots-clés • CTA • Chiffres • Progression (#D6A12A)
val MoutardeClaire = Color(0xFFFBF4E4)  // Fond accent doux
val Creme = Color(0xFFF4EBD9)           // Fond principal • Cartes • Respiration (55-65%) (#F4EBD9)
val CremeClair = Color(0xFFFAF5EC)      // Fond canvas ultra doux
val CremeFonce = Color(0xFFEADCC6)      // Surface container / Pill inactif (#EADCC6)
val Encre = Color(0xFF211D1B)           // Texte courant (#211D1B)
val EncreMoyenne = Color(0xFF5A524D)    // Légendes • Sous-titres • Métadonnées
val Blanc = Color(0xFFFFFFFF)           // Fond de carte surélevée / Contraste
val BordureLegere = Color(0xFFE2D5C0)   // Bordure discrète recommandée par la charte

// Status / Validation (Harmonisés avec la charte)
val VertSucces = Color(0xFF4E7230)
val VertDoux = Color(0xFFEFF5E8)
val RougeAlerte = Color(0xFFA32328)
val RougeDoux = Color(0xFFFCEBEC)
val JauneDoux = MoutardeClaire
val GrenatDoux = Color(0xFFF7ECEE)

// Semantic Theme Mappings
val BrandPrimary = Grenat
val BrandOnPrimary = Blanc
val BrandPrimaryContainer = GrenatProfond
val BrandOnPrimaryContainer = Blanc
val BrandSecondary = Olive
val BrandOnSecondary = Blanc
val BrandSecondaryContainer = CremeFonce
val BrandOnSecondaryContainer = GrenatProfond
val BrandAccent = Moutarde
val BrandBackground = CremeClair
val BrandSurface = Creme
val BrandSurfaceContainer = Blanc
val BrandOutline = BordureLegere
val BrandTextPrimary = Encre
val BrandTextSecondary = EncreMoyenne

// Backward-compatible minimal aliases
val MinimalBackground = CremeClair
val MinimalSurface = Blanc
val MinimalSurfaceContainer = Creme
val MinimalPrimary = Grenat
val MinimalPrimaryContainer = GrenatProfond
val MinimalOnPrimaryContainer = Blanc
val MinimalSecondaryContainer = CremeFonce
val MinimalOutline = BordureLegere
val MinimalOutlineLight = Color(0xFFEFE6D5)
val MinimalTextPrimary = Encre
val MinimalTextSecondary = EncreMoyenne


