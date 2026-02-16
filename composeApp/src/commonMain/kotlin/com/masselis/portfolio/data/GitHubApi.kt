package com.masselis.portfolio.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal class GitHubApi(
    private val client: HttpClient
) {

    @Serializable
    internal data class RepositoryStats(
        @SerialName("stargazers_count") val stargazersCount: Int,
        @SerialName("forks_count") val forksCount: Int,
        val language: String?,
    )

    internal suspend fun fetchStats(
        owner: String,
        repo: String
    ): RepositoryStats = client
        .get("https://api.github.com/repos/$owner/$repo")
        .body()
}