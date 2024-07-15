package com.rmtz.calendar.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.rmtz.calendar.ui.theme.FlatUiColors.BasicPallete.Companion.BrokenWhite
import com.rmtz.calendar.ui.theme.FlatUiColors.BasicPallete.Companion.LightenDark
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.CasandoraYellow
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.FuelTown
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.JadeDust
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.Jigglypuff
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.JoustBlue
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.LightBlueBallerina
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.Megaman
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.NasuPurple
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.PastelRed
import com.rmtz.calendar.ui.theme.FlatUiColors.CanadianPallet.Companion.WildCaribbeanGreen
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.BlueGrey
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.Desire
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.GloomyPurple
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.HighBlue
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.NycTaxi
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.OrangeHibiscus
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.ReptileGreen
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.RoyalBlue
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.TurquoiseTopaz
import com.rmtz.calendar.ui.theme.FlatUiColors.GermanPallet.Companion.TwinkleBlue

object BaseLight {
    val BrokenWhite = Color(0xFFffffff)
    val LightenDark = Color(0xFFf1f2f6)
}

object BaseDark {
    val BrokenWhite = Color(0xFFf1f2f6)
    val LightenDark = Color(0xff1e1f22)
}

object GermanDark {
    val Desire = Color(0xFFeb3b5a)
    val OrangeHibiscus = Color(0xFFfd9644)
    val NycTaxi = Color(0xFFf7b731)
    val ReptileGreen = Color(0xFF26de81)
    val TurquoiseTopaz = Color(0xFF0fb9b1)
    val HighBlue = Color(0xFF2d98da)
    val RoyalBlue = Color(0xFF4b7bec)
    val GloomyPurple = Color(0xFF8854d0)
    val TwinkleBlue = Color(0xFFd1d8e0)
    val BlueGrey = Color(0xFF4b6584)
}

object GermanLight {
    val Desire = Color(0xFFfc5c65)
    val OrangeHibiscus = Color(0xFFfa8231)
    val NycTaxi = Color(0xFFfed330)
    val ReptileGreen = Color(0xFF20bf6b)
    val TurquoiseTopaz = Color(0xFF2bcbba)
    val HighBlue = Color(0xFF45aaf2)
    val RoyalBlue = Color(0xFF3867d6)
    val GloomyPurple = Color(0xFFa55eea)
    val TwinkleBlue = Color(0xFFa5b1c2)
    val BlueGrey = Color(0xFF778ca3)
}

object CanadianDark {
    val Jigglypuff =  Color(0xffff9ff3)
    val CasandoraYellow =  Color(0xfffeca57)
    val PastelRed =  Color(0xffff6b6b)
    val Megaman =  Color(0xff48dbfb)
    val WildCaribbeanGreen =  Color(0xff1dd1a1)
    val JadeDust =  Color(0xff00d2d3)
    val JoustBlue =  Color(0xff54a0ff)
    val NasuPurple =  Color(0xff5f27cd)
    val LightBlueBallerina =  Color(0xffc8d6e5)
    val FuelTown =  Color(0xff576574)
}

object CanadianLight {
    val Jigglypuff =  Color(0xfff368e0)
    val CasandoraYellow =  Color(0xffff9f43)
    val PastelRed =  Color(0xffee5253)
    val Megaman =  Color(0xff0abde3)
    val WildCaribbeanGreen =  Color(0xff10ac84)
    val JadeDust =  Color(0xff01a3a4)
    val JoustBlue =  Color(0xff2e86de)
    val NasuPurple =  Color(0xff341f97)
    val LightBlueBallerina =  Color(0xff8395a7)
    val FuelTown =  Color(0xff222f3e)
}

object FlatUiColors {
    @JvmInline
    value class BasicPallete internal constructor(val value: Int) {
        companion object {
            val LightenDark : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        BaseDark.LightenDark
                    } else {
                        BaseLight.LightenDark
                    }
                }

            val BrokenWhite : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        BaseDark.BrokenWhite
                    } else {
                        BaseLight.BrokenWhite
                    }
                }
        }
    }

    @JvmInline
    value class GermanPallet internal constructor(val value: Int) {
        companion object {
            val Desire : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.Desire
                    } else {
                        GermanLight.Desire
                    }
                }
            val OrangeHibiscus : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.OrangeHibiscus
                    } else {
                        GermanLight.OrangeHibiscus
                    }
                }
            val NycTaxi : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.NycTaxi
                    } else {
                        GermanLight.NycTaxi
                    }
                }
            val ReptileGreen : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.ReptileGreen
                    } else {
                        GermanLight.ReptileGreen
                    }
                }
            val TurquoiseTopaz : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.TurquoiseTopaz
                    } else {
                        GermanLight.TurquoiseTopaz
                    }
                }
            val HighBlue : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.HighBlue
                    } else {
                        GermanLight.HighBlue
                    }
                }
            val RoyalBlue : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.RoyalBlue
                    } else {
                        GermanLight.RoyalBlue
                    }
                }
            val GloomyPurple : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.GloomyPurple
                    } else {
                        GermanLight.GloomyPurple
                    }
                }
            val TwinkleBlue : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.TwinkleBlue
                    } else {
                        GermanLight.TwinkleBlue
                    }
                }
            val BlueGrey : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        GermanDark.BlueGrey
                    } else {
                        GermanLight.BlueGrey
                    }
                }
        }
    }

    @JvmInline
    value class CanadianPallet internal constructor(val value: Int) {
        companion object {
            val Jigglypuff : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.Jigglypuff
                    } else {
                        CanadianLight.Jigglypuff
                    }
                }
            val CasandoraYellow : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.CasandoraYellow
                    } else {
                        CanadianLight.CasandoraYellow
                    }
                }
            val PastelRed : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.PastelRed
                    } else {
                        CanadianLight.PastelRed
                    }
                }
            val Megaman : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.Megaman
                    } else {
                        CanadianLight.Megaman
                    }
                }
            val WildCaribbeanGreen : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.WildCaribbeanGreen
                    } else {
                        CanadianLight.WildCaribbeanGreen
                    }
                }
            val JadeDust : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.JadeDust
                    } else {
                        CanadianLight.JadeDust
                    }
                }
            val JoustBlue : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.JoustBlue
                    } else {
                        CanadianLight.JoustBlue
                    }
                }
            val NasuPurple : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.NasuPurple
                    } else {
                        CanadianLight.NasuPurple
                    }
                }
            val LightBlueBallerina : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.LightBlueBallerina
                    } else {
                        CanadianLight.LightBlueBallerina
                    }
                }
            val FuelTown : Color
                @Composable
                get() {
                    return if (isSystemInDarkTheme()) {
                        CanadianDark.FuelTown
                    } else {
                        CanadianLight.FuelTown
                    }
                }
        }
    }

    @Composable
    fun Color.inverse(): Color {
        return when(this.toArgb()) {
            /*Base*/
            LightenDark.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    BaseLight.LightenDark
                } else {
                    BaseDark.LightenDark
                }
            }
            BrokenWhite.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    BaseLight.BrokenWhite
                } else {
                    BaseDark.BrokenWhite
                }
            }
            /*German*/
            Desire.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.Desire
                } else {
                    GermanDark.Desire
                }
            }
            OrangeHibiscus.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.OrangeHibiscus
                } else {
                    GermanDark.OrangeHibiscus
                }
            }
            NycTaxi.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.NycTaxi
                } else {
                    GermanDark.NycTaxi
                }
            }
            ReptileGreen.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.ReptileGreen
                } else {
                    GermanDark.ReptileGreen
                }
            }
            TurquoiseTopaz.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.TurquoiseTopaz
                } else {
                    GermanDark.TurquoiseTopaz
                }
            }
            HighBlue.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.HighBlue
                } else {
                    GermanDark.HighBlue
                }
            }
            RoyalBlue.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.RoyalBlue
                } else {
                    GermanDark.RoyalBlue
                }
            }
            GloomyPurple.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.GloomyPurple
                } else {
                    GermanDark.GloomyPurple
                }
            }
            TwinkleBlue.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.TwinkleBlue
                } else {
                    GermanDark.TwinkleBlue
                }
            }
            BlueGrey.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    GermanLight.BlueGrey
                } else {
                    GermanDark.BlueGrey
                }
            }
            /*Canadian*/
            Jigglypuff.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.Jigglypuff
                } else {
                    CanadianDark.Jigglypuff
                }
            }
            CasandoraYellow.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.CasandoraYellow
                } else {
                    CanadianDark.CasandoraYellow
                }
            }
            PastelRed.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.PastelRed
                } else {
                    CanadianDark.PastelRed
                }
            }
            Megaman.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.Megaman
                } else {
                    CanadianDark.Megaman
                }
            }
            WildCaribbeanGreen.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.WildCaribbeanGreen
                } else {
                    CanadianDark.WildCaribbeanGreen
                }
            }
            JadeDust.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.JadeDust
                } else {
                    CanadianDark.JadeDust
                }
            }
            JoustBlue.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.JoustBlue
                } else {
                    CanadianDark.JoustBlue
                }
            }
            NasuPurple.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.NasuPurple
                } else {
                    CanadianDark.NasuPurple
                }
            }
            LightBlueBallerina.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.LightBlueBallerina
                } else {
                    CanadianDark.LightBlueBallerina
                }
            }
            FuelTown.toArgb() -> {
                if (isSystemInDarkTheme()) {
                    CanadianLight.FuelTown
                } else {
                    CanadianDark.FuelTown
                }
            }
            else -> BaseLight.BrokenWhite
        }
    }
}