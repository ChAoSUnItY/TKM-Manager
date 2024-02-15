package io.github.chaosunity.tkmm.fabric

import io.github.chaosunity.tkmm.TKMManager
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = TKMManager.MOD_ID)
class TKMMConfig: ConfigData {
    var latestFileSha512Hash: String = "UNKNOWN"
}