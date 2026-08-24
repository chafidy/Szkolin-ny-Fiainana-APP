package com.example.data.model

data class Cahier(
    val id: Int,
    val number: String,
    val title: String,
    val theme: String,
    val subtitle: String,
    val summary: String,
    val pageStart: Int,
    val totalPlanches: Int = 20,
    val colorHex: Long = 0xFF681923,
    val iconName: String = "book",
    val phrasesAEmporter: List<String> = emptyList()
)

data class SchemaItem(
    val stepNumber: String,
    val title: String,
    val description: String,
    val highlight: String = ""
)

data class SchemaData(
    val type: SchemaType,
    val title: String = "",
    val subtitle: String = "",
    val items: List<SchemaItem> = emptyList(),
    val comparisonLeft: Pair<String, String>? = null,
    val comparisonRight: Pair<String, String>? = null,
    val formula: String? = null
)

enum class SchemaType {
    FLOW_STEPS,
    COMPARISON,
    STAIRS_PROGRESSION,
    TIMELINE,
    FORMULA_CALC,
    PYRAMID_CIRCLES,
    CHART_BARS,
    TABLE_GRID
}

data class KeyCard(
    val code: String, // e.g. "F.1", "R.1", "1"
    val title: String,
    val description: String
)

data class DetailedBox(
    val title: String,
    val items: List<String>,
    val highlightColor: Long = 0xFF6F783A
)

enum class AdviceType {
    CONSEIL_SEMAINE,
    A_RETENIR,
    REGLE_DOR,
    LE_SAIS_TU,
    SIGNAL_ALERTE,
    EXERCICE_PRATIQUE,
    BON_REFLEXE
}

data class BottomAdvice(
    val type: AdviceType,
    val title: String,
    val content: String
)

data class Planche(
    val id: String,
    val cahierId: Int,
    val pageNumber: Int,
    val sectionNumber: String, // e.g. "01 NOTION · COMPRENDRE"
    val title: String,
    val accroche: String,
    val schema: SchemaData? = null,
    val keyCards: List<KeyCard> = emptyList(),
    val detailedBoxes: List<DetailedBox> = emptyList(),
    val bottomAdvice: BottomAdvice? = null
)

data class QuizQuestion(
    val id: String,
    val cahierId: Int,
    val number: Int,
    val question: String,
    val optionA: String,
    val optionB: String,
    val correctOption: String, // "A" or "B"
    val explanation: String
)

data class LexiqueItem(
    val term: String,
    val definition: String,
    val cahierId: Int = 1
)

data class WorkshopField(
    val id: String,
    val label: String,
    val placeholder: String = "",
    val isNumber: Boolean = false,
    val suffix: String = ""
)

data class WorkshopDefinition(
    val id: String,
    val cahierId: Int,
    val title: String,
    val subtitle: String,
    val quote: String,
    val fields: List<WorkshopField>,
    val calculationType: String? = null // e.g. "BUDGET_CALC", "RATE_CALC", "HABIT_30"
)

