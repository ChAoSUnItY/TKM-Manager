package io.github.chaosunity.tkmm.forge

import io.github.chaosunity.tkmm.TKMManager
import net.minecraft.client.Minecraft
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import java.net.URL
import java.nio.file.Paths

@Mod(TKMManager.MOD_ID)
object TKMManagerForge {
    val CONFIG: TKMMConfig
    val CONFIG_SPEC: ForgeConfigSpec
    
    init {
        val (config, configSpec) = ForgeConfigSpec.Builder().configure(::TKMMConfig)
        CONFIG = config
        CONFIG_SPEC = configSpec
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CONFIG_SPEC)
        MOD_BUS.addListener(TKMManagerForge::onClientSetup)
    }
    
    private fun onClientSetup(event: FMLClientSetupEvent) {
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
        
        if (CONFIG.latestFileSha512Hash.get() != latestFile.hashes.sha512) {
            val mc = Minecraft.getInstance()
            val options = mc.options
            val repository = mc.resourcePackRepository
            val resourcePackDir = mc.resourcePackDirectory
            
            TKMManager.downloadResourcePackFrom(URL(latestFile.fileUrl), resourcePackDir.resolve(latestFile.fileName))
            
            repository.reload()
            repository.addPack(latestFile.resourcePackId)
            options.updateResourcePacks(mc.resourcePackRepository)
            mc.reloadResourcePacks()
        }
    }
}