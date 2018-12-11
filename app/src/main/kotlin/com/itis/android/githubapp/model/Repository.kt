package com.itis.android.githubapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Repository(var id: Int = 0,
                      var name: String,
                      @SerializedName("full_name")
                      var fullName: String,
                      @SerializedName("private")
                      var repPrivate: Boolean = false,
                      @SerializedName("html_url")
                      var htmlUrl: String,
                      var description: String?,
                      var language: String,
                      var owner: User,
                      @SerializedName("default_branch")
                      var defaultBranch: String,
                      @SerializedName("created_at")
                      var createdAt: Date,
                      @SerializedName("updated_at")
                      var updatedAt: Date,
                      @SerializedName("pushed_at")
                      var pushedAt: Date,
                      @SerializedName("git_url")
                      var gitUrl: String,
                      @SerializedName("ssh_url")
                      var sshUrl: String,
                      @SerializedName("clone_url")
                      var cloneUrl: String,
                      @SerializedName("svn_url")
                      var svnUrl: String,
                      var size: Int = 0,
                      @SerializedName("stargazers_count")
                      var stargazersCount: Int = 0,
                      @SerializedName("watchers_count")
                      var watchersCount: Int = 0,
                      @SerializedName("forks_count")
                      var forksCount: Int = 0,
                      @SerializedName("open_issues_count")
                      var openIssuesCount: Int = 0,
                      @SerializedName("subscribers_count")
                      var subscribersCount: Int = 0,
                      var fork: Boolean = false,
                      var parent: Repository,
                      @SerializedName("has_issues")
                      var hasIssues: Boolean = false,
                      @SerializedName("has_projects")
                      var hasProjects: Boolean = false,
                      @SerializedName("has_downloads")
                      var hasDownloads: Boolean = false,
                      @SerializedName("has_wiki")
                      var hasWiki: Boolean = false,
                      @SerializedName("has_pages")
                      var hasPages: Boolean = false,
                      var sinceStargazersCount: Int = 0
)
