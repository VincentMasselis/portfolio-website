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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month.MARCH
import kotlinx.datetime.YearMonth
import portfolio.core.generated.resources.Res
import portfolio.core.generated.resources.chronodrive_banner
import portfolio.core.generated.resources.chronodrive_logo
import portfolio.core.generated.resources.cubeinstore
import portfolio.core.generated.resources.decathlon
import portfolio.core.generated.resources.embipos
import portfolio.core.generated.resources.equisense_banner
import portfolio.core.generated.resources.equisense_logo
import portfolio.core.generated.resources.home_assistant_banner
import portfolio.core.generated.resources.kadiska_banner
import portfolio.core.generated.resources.kadiska_logo
import portfolio.core.generated.resources.project_chronodrive_bullet_1
import portfolio.core.generated.resources.project_chronodrive_bullet_2
import portfolio.core.generated.resources.project_chronodrive_bullet_3
import portfolio.core.generated.resources.project_chronodrive_description
import portfolio.core.generated.resources.project_chronodrive_title
import portfolio.core.generated.resources.project_cubeinstore_bullet_1
import portfolio.core.generated.resources.project_cubeinstore_bullet_2
import portfolio.core.generated.resources.project_cubeinstore_bullet_3
import portfolio.core.generated.resources.project_cubeinstore_bullet_4
import portfolio.core.generated.resources.project_cubeinstore_description
import portfolio.core.generated.resources.project_cubeinstore_title
import portfolio.core.generated.resources.project_embisdk_bullet_1
import portfolio.core.generated.resources.project_embisdk_bullet_2
import portfolio.core.generated.resources.project_embisdk_bullet_3
import portfolio.core.generated.resources.project_embisdk_bullet_4
import portfolio.core.generated.resources.project_embisdk_description
import portfolio.core.generated.resources.project_embisdk_title
import portfolio.core.generated.resources.project_equisense_bullet_1
import portfolio.core.generated.resources.project_equisense_bullet_2
import portfolio.core.generated.resources.project_equisense_bullet_3
import portfolio.core.generated.resources.project_equisense_bullet_4
import portfolio.core.generated.resources.project_equisense_description
import portfolio.core.generated.resources.project_equisense_title
import portfolio.core.generated.resources.project_express_payment_bullet_1
import portfolio.core.generated.resources.project_express_payment_bullet_2
import portfolio.core.generated.resources.project_express_payment_bullet_3
import portfolio.core.generated.resources.project_express_payment_description
import portfolio.core.generated.resources.project_express_payment_title
import portfolio.core.generated.resources.project_home_assistant_bullet_1
import portfolio.core.generated.resources.project_home_assistant_bullet_2
import portfolio.core.generated.resources.project_home_assistant_bullet_3
import portfolio.core.generated.resources.project_home_assistant_description
import portfolio.core.generated.resources.project_home_assistant_title
import portfolio.core.generated.resources.project_kadiska_bullet_1
import portfolio.core.generated.resources.project_kadiska_bullet_2
import portfolio.core.generated.resources.project_kadiska_bullet_3
import portfolio.core.generated.resources.project_kadiska_bullet_4
import portfolio.core.generated.resources.project_kadiska_description
import portfolio.core.generated.resources.project_kadiska_title
import portfolio.core.generated.resources.project_tpms_advanced_bullet_1
import portfolio.core.generated.resources.project_tpms_advanced_bullet_2
import portfolio.core.generated.resources.project_tpms_advanced_bullet_3
import portfolio.core.generated.resources.project_tpms_advanced_description
import portfolio.core.generated.resources.project_tpms_advanced_title
import portfolio.core.generated.resources.project_xee_bullet_1
import portfolio.core.generated.resources.project_xee_bullet_2
import portfolio.core.generated.resources.project_xee_bullet_3
import portfolio.core.generated.resources.project_xee_description
import portfolio.core.generated.resources.project_xee_title
import portfolio.core.generated.resources.rxvincent_logo
import portfolio.core.generated.resources.timeline_career_start_description
import portfolio.core.generated.resources.timeline_career_start_title
import portfolio.core.generated.resources.timeline_decathlon_freelance_description
import portfolio.core.generated.resources.timeline_decathlon_freelance_title
import portfolio.core.generated.resources.timeline_equisense_description
import portfolio.core.generated.resources.timeline_equisense_title
import portfolio.core.generated.resources.timeline_first_developments_description
import portfolio.core.generated.resources.timeline_first_developments_title
import portfolio.core.generated.resources.timeline_htc_dream_description
import portfolio.core.generated.resources.timeline_htc_dream_title
import portfolio.core.generated.resources.tpms_advanced_banner
import portfolio.core.generated.resources.vivawallet
import portfolio.core.generated.resources.xee_banner
import portfolio.core.generated.resources.xee_logo

public object PortfolioData {
    public val availability: LocalDate = LocalDate(2026, MARCH, 30)
    public val skills: Set<Skill> = setOf(
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
        Skill("Fragment/XML", Expert, Android, Resume),
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
        Skill("Python", Initiated),
        Skill("Système embarqués", Confirmed),
    )

    public val timelineEntries: List<TimelineEntry> = listOf(
        TimelineEntry(
            Moment(YearMonth(2009, 6)),
            Res.string.timeline_htc_dream_title,
            Res.string.timeline_htc_dream_description,
        ),
        TimelineEntry(
            Moment(YearMonth(2010, 10)),
            Res.string.timeline_first_developments_title,
            Res.string.timeline_first_developments_description,
        ),
        TimelineEntry(
            Range(
                YearMonth(2013, 7),
                YearMonth(2016, 9)
            ),
            Res.string.timeline_career_start_title,
            Res.string.timeline_career_start_description,
        ),
        TimelineEntry(
            Range(
                YearMonth(2016, 10),
                YearMonth(2020, 11),
            ),
            Res.string.timeline_equisense_title,
            Res.string.timeline_equisense_description,
        ),
        TimelineEntry(
            Pending(YearMonth(2020, 12)),
            Res.string.timeline_decathlon_freelance_title,
            Res.string.timeline_decathlon_freelance_description,
        ),
    )

    private val skillMap = skills.associateBy { it.name }
    public val projects: List<Project> = listOf(
        Project(
            logo = Res.drawable.decathlon,
            image = Res.drawable.cubeinstore,
            title = Res.string.project_cubeinstore_title,
            bulletPoints = listOf(
                Res.string.project_cubeinstore_bullet_1,
                Res.string.project_cubeinstore_bullet_2,
                Res.string.project_cubeinstore_bullet_3,
                Res.string.project_cubeinstore_bullet_4,
            ),
            descriptionMd = Res.string.project_cubeinstore_description,
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Jetpack Compose"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Clean Architecture"]!!,
                skillMap["Gradle Framework"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["CI/CD Bitrise/Actions"]!!,
                skillMap["Testing Mockk/Mockito"]!!,
                skillMap["Crash monitoring"]!!
            )
        ),
        Project(
            logo = Res.drawable.rxvincent_logo,
            image = Res.drawable.tpms_advanced_banner,
            title = Res.string.project_tpms_advanced_title,
            bulletPoints = listOf(
                Res.string.project_tpms_advanced_bullet_1,
                Res.string.project_tpms_advanced_bullet_2,
                Res.string.project_tpms_advanced_bullet_3,
            ),
            descriptionMd = Res.string.project_tpms_advanced_description,
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Jetpack Compose"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Clean Architecture"]!!,
                skillMap["ViewModel MVVM/MVI"]!!,
                skillMap["Gradle Framework"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["CI/CD Bitrise/Actions"]!!,
                skillMap["Crash monitoring"]!!,
            )
        ),
        Project(
            logo = Res.drawable.decathlon,
            image = Res.drawable.vivawallet,
            title = Res.string.project_express_payment_title,
            bulletPoints = listOf(
                Res.string.project_express_payment_bullet_1,
                Res.string.project_express_payment_bullet_2,
                Res.string.project_express_payment_bullet_3,
            ),
            descriptionMd = Res.string.project_express_payment_description,
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Jetpack Compose"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Clean Architecture"]!!,
                skillMap["ViewModel MVVM/MVI"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["Testing Mockk/Mockito"]!!,
                skillMap["Crash monitoring"]!!
            )
        ),
        Project(
            logo = Res.drawable.kadiska_logo,
            image = Res.drawable.kadiska_banner,
            title = Res.string.project_kadiska_title,
            bulletPoints = listOf(
                Res.string.project_kadiska_bullet_1,
                Res.string.project_kadiska_bullet_2,
                Res.string.project_kadiska_bullet_3,
                Res.string.project_kadiska_bullet_4,
            ),
            descriptionMd = Res.string.project_kadiska_description,
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Java"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["Fragment/XML"]!!,
                skillMap["CI/CD Bitrise/Actions"]!!,
                skillMap["Rust"]!!,
            )
        ),
        Project(
            logo = Res.drawable.rxvincent_logo,
            image = Res.drawable.home_assistant_banner,
            title = Res.string.project_home_assistant_title,
            bulletPoints = listOf(
                Res.string.project_home_assistant_bullet_1,
                Res.string.project_home_assistant_bullet_2,
                Res.string.project_home_assistant_bullet_3,
            ),
            descriptionMd = Res.string.project_home_assistant_description,
            skills = listOf(
                skillMap["Home Assistant"]!!,
                skillMap["Python"]!!,
                skillMap["Système embarqués"]!!,
            )
        ),
        Project(
            logo = Res.drawable.decathlon,
            image = Res.drawable.embipos,
            title = Res.string.project_embisdk_title,
            bulletPoints = listOf(
                Res.string.project_embisdk_bullet_1,
                Res.string.project_embisdk_bullet_2,
                Res.string.project_embisdk_bullet_3,
                Res.string.project_embisdk_bullet_4,
            ),
            descriptionMd = Res.string.project_embisdk_description,
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["Coroutines/Flow"]!!,
                skillMap["Jetpack Compose"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Compose Multiplatform"]!!,
                skillMap["Clean Architecture"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["RxJava"]!!,
                skillMap["Java"]!!,
            )
        ),
        Project(
            logo = Res.drawable.equisense_logo,
            image = Res.drawable.equisense_banner,
            title = Res.string.project_equisense_title,
            bulletPoints = listOf(
                Res.string.project_equisense_bullet_1,
                Res.string.project_equisense_bullet_2,
                Res.string.project_equisense_bullet_3,
                Res.string.project_equisense_bullet_4,
            ),
            descriptionMd = Res.string.project_equisense_description,
            skills = listOf(
                skillMap["Kotlin"]!!,
                skillMap["RxJava"]!!,
                skillMap["iOS / Swift"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Fragment/XML"]!!,
                skillMap["ViewModel MVVM/MVI"]!!,
                skillMap["Dagger/Hilt/Koin/Metro"]!!,
                skillMap["Crash monitoring"]!!,
                skillMap["Go"]!!,
                skillMap["Système embarqués"]!!,
            )
        ),
        Project(
            logo = Res.drawable.xee_logo,
            image = Res.drawable.xee_banner,
            title = Res.string.project_xee_title,
            bulletPoints = listOf(
                Res.string.project_xee_bullet_1,
                Res.string.project_xee_bullet_2,
                Res.string.project_xee_bullet_3,
            ),
            descriptionMd = Res.string.project_xee_description,
            skills = listOf(
                skillMap["iOS / Swift"]!!,
                skillMap["Objective-C"]!!,
                skillMap["Java"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Fragment/XML"]!!,
                skillMap["Système embarqués"]!!,
            )
        ),
        Project(
            logo = Res.drawable.chronodrive_logo,
            image = Res.drawable.chronodrive_banner,
            title = Res.string.project_chronodrive_title,
            bulletPoints = listOf(
                Res.string.project_chronodrive_bullet_1,
                Res.string.project_chronodrive_bullet_2,
                Res.string.project_chronodrive_bullet_3,
            ),
            descriptionMd = Res.string.project_chronodrive_description,
            skills = listOf(
                skillMap["iOS / Swift"]!!,
                skillMap["Objective-C"]!!,
                skillMap["Java"]!!,
                skillMap["Android SDK"]!!,
                skillMap["Fragment/XML"]!!,
            )
        ),
    )
}
