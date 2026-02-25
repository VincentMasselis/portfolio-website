package com.masselis.portfolio.data

import com.masselis.portfolio.data.Skill.Level.Confirmed
import com.masselis.portfolio.data.Skill.Level.Expert
import com.masselis.portfolio.data.Skill.Level.Initiated
import com.masselis.portfolio.data.Skill.Level.Mastery
import com.masselis.portfolio.data.Tag.Android
import com.masselis.portfolio.data.Tag.Architecture
import com.masselis.portfolio.data.Tag.Compose
import com.masselis.portfolio.data.Tag.Multiplatform
import com.masselis.portfolio.data.Tag.Resume
import com.masselis.portfolio.data.TimelineEntry.Moment
import com.masselis.portfolio.data.TimelineEntry.Pending
import com.masselis.portfolio.data.TimelineEntry.Range
import kotlinx.datetime.YearMonth

public object PortfolioData {
    public val skills: List<Skill> = listOf(
        Skill("Kotlin", Mastery, Android, Multiplatform, Compose, Resume),
        Skill("iOS / Swift", Expert, Multiplatform, Resume),
        Skill("Coroutines/Flow", Mastery, Android, Multiplatform, Resume),
        Skill("Jetpack Compose", Expert, Android, Multiplatform, Compose, Resume),
        Skill("SwiftUI", Confirmed, Multiplatform, Resume),
        Skill("Android SDK", Mastery, Android, Compose, Resume, Architecture),
        Skill(
            "Compose Multiplatform",
            Expert,
            Android,
            Multiplatform,
            Compose,
            Resume,
            Architecture
        ),
        Skill("Clean Architecture", Expert, Android, Multiplatform, Resume, Architecture),
        Skill("ViewModel MVVM/MVI", Mastery, Android, Multiplatform, Resume, Architecture),
        Skill("Gradle Framework", Expert, Android, Multiplatform, Resume, Architecture),
        Skill("Dagger/Hilt/Koin/Metro", Mastery, Multiplatform, Resume),
        Skill("CI/CD Bitrise/Actions", Confirmed, Android, Multiplatform, Resume, Architecture),
        Skill("Testing Mockk/Mockito", Expert, Android, Multiplatform, Architecture),
        Skill("Crash monitoring", Expert, Android, Resume, Architecture),
        Skill("RxJava", Mastery, Android),
        Skill("Objective-C", Confirmed),
        Skill("Java", Confirmed, Android),
        Skill("Rust", Initiated),
        Skill("Go", Initiated),
        Skill("Home Assistant", Expert),
    )

    public val timelineEntries: List<TimelineEntry> = listOf(
        TimelineEntry(
            Moment(YearMonth(2009, 6)),
            "Mon premier smartphone Android",
            "Sans le savoir, j'ai acheté l'HTC Dream, le tout premier téléphone sous Android, qui tracera ma carrière pour les années à venir",
        ),
        TimelineEntry(
            Moment(YearMonth(2010, 10)),
            "Mes premiers developpements",
            "Système domotique X10 en C++",
        ),
        TimelineEntry(
            Range(
                YearMonth(2013, 7),
                YearMonth(2016, 9)
            ),
            "Début de carrière professionnelle sous iOS et Android",
            "Dès le début, les enjeux sont fort avec, entre autres, la refonte Android et iOS de l'application Chronodrive",
        ),
        TimelineEntry(
            Range(
                YearMonth(2016, 10),
                YearMonth(2020, 11),
            ),
            "Ma première startup Equisense",
            "Rejoint en early stage juste après leur levée de fond, j'ai conçu et développé from-scratch l'application iOS et Android, ensuite rejoint par 3 autres développeurs",
        ),
        TimelineEntry(
            Pending(YearMonth(2020, 12)),
            "Début du freelancing chez Decathlon",
            "Après 10 ans développement et une solide expérience, je décide, un peu par hasard, d'entrer dans le monde du Freelancing chez Decatlon"
        ),
    )

    public val projects: List<Project> = listOf(
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

    public val contacts: List<ContactInfo> = listOf(
        ContactInfo("R\u00E9seau Professionnel", "linkedin.com/in/vincentmasselis", "LinkedIn"),
        ContactInfo("Projets & Code", "github.com/RxVincent", "GitHub"),
        ContactInfo("R\u00E9seau Social", "bsky.app/profile/rxvincent.bsky.s", "Bluesky"),
        ContactInfo("Contact Direct", "vincent@rxvincent.com", "Email"),
    )
}
