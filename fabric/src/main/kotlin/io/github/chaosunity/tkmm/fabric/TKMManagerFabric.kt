package io.github.chaosunity.tkmm.fabric

import io.github.chaosunity.tkmm.TKMManager
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.Minecraft
import java.net.URL

object TKMManagerFabric: ClientModInitializer {
    lateinit var CONFIG: TKMMConfig
    
    override fun onInitializeClient() {
        AutoConfig.register(TKMMConfig::class.java, ::Toml4jConfigSerializer)
        CONFIG = AutoConfig.getConfigHolder(TKMMConfig::class.java).config
    }
    
    fun onMinecraftLoaded() {
        val latestVersion = TKMManager.fetchLatestVersion("1.19.4")

        if (latestVersion == null) {
            TKMManager.LOGGER.warn("Failed to fetch the latest version of Mod Translation Pack: No applicable version for 1.19.4")
            return
        }

        val latestFile = latestVersion.files.firstOrNull()

        if (latestFile == null) {
            TKMManager.LOGGER.warn("Failed to fetch the latest version of Mod Translation Pack: Could not find the file in release")
            return
        }

        if (CONFIG.latestFileSha512Hash != latestFile.hashes.sha512) {
            val mc = Minecraft.getInstance()
            val options = mc.options
            val repository = mc.resourcePackRepository
            val resourcePackDir = mc.resourcePackDirectory

            TKMManager.downloadResourcePackFrom(URL(latestFile.fileUrl), resourcePackDir.resolve(latestFile.fileName))

            repository.reload()
            repository.addPack(latestFile.resourcePackId)
            options.updateResourcePacks(mc.resourcePackRepository)
            mc.reloadResourcePacks()
            
            TKMManager.LOGGER.info(repository.availableIds.toString())
            
            CONFIG.latestFileSha512Hash = latestFile.hashes.sha512
        }
    }
}