package com.masselis.portfolio.data

import kotlinx.datetime.YearMonth
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import kotlin.jvm.JvmInline

public enum class Tag {
    Android,
    Compose,
    Architecture,
    Multiplatform,
    Resume,
}

public data class Skill(
    val name: String,
    val level: Level,
    val tags: Set<Tag>,
) {
    public constructor(
        name: String,
        level: Level,
        vararg tags: Tag,
    ) : this(name, level, tags.toSet())

    public enum class Level(public val fraction: Float) {
        Initiated(.25f),
        Confirmed(.5f),
        Expert(.75f),
        Mastery(1f);
    }
}

public data class TimelineEntry(
    val time: Time,
    val title: StringResource,
    val description: StringResource
) {
    public sealed interface Time

    @JvmInline
    public value class Moment(public val moment: YearMonth) : Time

    public data class Range(public val from: YearMonth, public val to: YearMonth) : Time

    @JvmInline
    public value class Pending(public val moment: YearMonth) : Time
}

public data class Project(
    val logo: DrawableResource,
    val image: DrawableResource,
    val title: StringResource,
    val bulletPoints: List<StringResource>,
    val descriptionMd: StringResource,
    val skills: List<Skill>,
)

public data class ContactInfo(val label: String, val url: String, val iconName: String)
public data class RepoInfo(val name: String, val stars: String, val language: String)
