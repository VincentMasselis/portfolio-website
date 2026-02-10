package com.masselis.portfolio.data

data class Skill(val name: String, val progress: Float)
data class TimelineEntry(val dateRange: String, val title: String, val description: String)
data class Project(
    val title: String,
    val description: String,
    val bulletPoints: List<String>,
    val techStack: List<String>,
)
data class ContactInfo(val label: String, val url: String, val iconName: String)
data class RepoInfo(val name: String, val stars: String, val language: String)

object PortfolioData {
    const val heroTitle = "ARCHITECTE D'APPLICATIONS\nANDROID & EXPERT RX"
    const val heroSubtitle = "Je transforme des besoins complexes en exp\u00E9riences mobiles fluides."

    val skills = listOf(
        Skill("Kotlin", 0.90f),
        Skill("Jetpack Compose", 0.85f),
        Skill("Android SDK", 0.90f),
        Skill("Clean Architecture", 0.80f),
        Skill("RxJava", 0.85f),
        Skill("iOS / Swift", 0.40f),
        Skill("Node.js", 0.30f),
    )

    val timelineEntries = listOf(
        TimelineEntry(
            "1999 \u2013 2011",
            "D\u00E9veloppement propre",
            "Freelance Android developer avec l'architecture tools",
        ),
        TimelineEntry(
            "2009 \u2013 2012",
            "Vincent Masselis",
            "D\u00E9veloppement Android dans Jetpack Compose.",
        ),
        TimelineEntry(
            "2003 \u2013 2025",
            "D\u00E9veloppement propre",
            "Freelance Android developer avec architecture robuste",
        ),
        TimelineEntry(
            "2023 \u2013 2033",
            "Vincent Masselis",
            "D\u00E9veloppement des startups \u2013 Jetpack Compose.",
        ),
        TimelineEntry(
            "2013 \u2013 2031",
            "Vincent Masselis",
            "Freelance Android modernes \u2013 Jetpack Compose.",
        ),
    )

    val projects = listOf(
        Project(
            title = "FITNESS COACH APP",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            bulletPoints = listOf(
                "Kotlin, Jetpack Compose",
                "4.8/5 \u00E9toiles",
                "100k+ t\u00E9l\u00E9chargements",
            ),
            techStack = listOf("Kotlin", "Jetpack Compose", "Room", "Retrofit"),
        ),
        Project(
            title = "CLIENT : LOGISTICS SOLUTIONS",
            description = "Outil de gestion de flotte professionnelle.",
            bulletPoints = listOf(
                "Outil de gestion de flotte",
                "D\u00E9ploy\u00E9 sur 5000 terminaux",
                "Crash-free rate : 99.9%",
            ),
            techStack = listOf("Kotlin", "MVVM", "Hilt", "Google Maps SDK"),
        ),
        Project(
            title = "SMART HOME CONTROLLER",
            description = "Application de domotique multiplateforme.",
            bulletPoints = listOf(
                "Contr\u00F4le IoT en temps r\u00E9el",
                "Interface Material Design 3",
                "Support hors-ligne",
            ),
            techStack = listOf("KMP", "Compose Multiplatform", "MQTT", "SQLDelight"),
        ),
    )

    val contacts = listOf(
        ContactInfo("R\u00E9seau Professionnel", "linkedin.com/in/vincentmasselis", "LinkedIn"),
        ContactInfo("Projets & Code", "github.com/RxVincent", "GitHub"),
        ContactInfo("R\u00E9seau Social", "bsky.app/profile/rxvincent.bsky.s", "Bluesky"),
        ContactInfo("Contact Direct", "vincent@rxvincent.com", "Email"),
    )

    val repos = listOf(
        RepoInfo("RxVincent-Utils", "150", "Kotlin"),
        RepoInfo("github-repo", "5.9", "JS"),
        RepoInfo("rxvincent-repo", "5.0", "Kotlin"),
    )
}
