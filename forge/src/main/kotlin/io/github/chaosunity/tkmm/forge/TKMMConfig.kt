package io.github.chaosunity.tkmm.forge

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue

class TKMMConfig(builder: ForgeConfigSpec.Builder) {
    val latestFileSha512Hash: ConfigValue<String>

    init {
        latestFileSha512Hash = builder.comment(
            "The current TKMM's referenced latest version of Mod Translation Pack.",
            "This field should only be modified by TKMM."
        ).define("latestFileSha512Hash", "UNKNOWN")
    }
}