package io.github.chaosunity.tkmm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Version(@SerialName("game_versions") val game_versions: List<String>, @SerialName("files") val files: List<File>)

@Serializable
data class File(@SerialName("hashes") val hashes: Hash, @SerialName("url") val fileUrl: String, @SerialName("filename") val fileName: String) {
    val resourcePackId by lazy {
        "file/$fileName"
    }
}

@Serializable
data class Hash(@SerialName("sha512") val sha512: String)
