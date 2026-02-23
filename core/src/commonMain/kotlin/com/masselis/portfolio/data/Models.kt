package com.masselis.portfolio.data

public data class Skill(val name: String, val progress: Float)
public data class TimelineEntry(val dateRange: String, val title: String, val description: String)
public data class Project(
    val title: String,
    val description: String,
    val bulletPoints: List<String>,
    val techStack: List<String>,
)
public data class ContactInfo(val label: String, val url: String, val iconName: String)
public data class RepoInfo(val name: String, val stars: String, val language: String)
