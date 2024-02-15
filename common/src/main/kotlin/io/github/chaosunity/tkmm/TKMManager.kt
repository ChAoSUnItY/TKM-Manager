package io.github.chaosunity.tkmm

import io.github.chaosunity.tkmm.TKMManager.GLOBAL_JSON
import io.github.chaosunity.tkmm.model.Version
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

object TKMManager {
    const val MOD_ID = "tkmm"
    private val VERSION_API_URL: URL = URL("https://api.modrinth.com/v2/project/modstranslationpack/version")
    val GLOBAL_JSON = Json { ignoreUnknownKeys = true }
    val LOGGER = LoggerFactory.getLogger(MOD_ID)
    
    fun downloadResourcePackFrom(fromUrl: URL, targetFilePath: Path) {
        fromUrl.openStream().use { Files.copy(it, targetFilePath) }
    }
    
    fun fetchLatestVersion(mcVersion: String): Version? {
        return fetchVersions(mcVersion).firstOrNull()
    }

    fun fetchVersions(mcVersion: String): List<Version> {
        return fetchAllVersions().filter { it.game_versions.contains(mcVersion) }
    }
    
    fun fetchAllVersions(): List<Version> {
        val versioningResults = VERSION_API_URL.readText()

        return GLOBAL_JSON.decodeFromString(ListSerializer(Version.serializer()), versioningResults)
    }
}