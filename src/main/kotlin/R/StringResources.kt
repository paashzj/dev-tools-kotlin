package R

import java.util.Locale

val strings = when (Locale.getDefault().language) {
    "en" -> EnStrings
    "zh" -> ChStrings
    else -> EnStrings
}

interface Strings {
    val aboutAuthor: String
    val commandPage: String
    val convertPage: String
    val devTools: String
    val homePage: String
    val simulatorPage: String
    val troubleShootPage: String
}

object EnStrings : Strings {
    override val aboutAuthor: String
        get() = "about author"
    override val commandPage: String
        get() = "command page"
    override val convertPage: String
        get() = "convert page"
    override val devTools: String
        get() = "dev tools"
    override val homePage: String
        get() = "home page"
    override val simulatorPage: String
        get() = "simulator page"
    override val troubleShootPage: String
        get() = "trouble shoot page"
}

object ChStrings : Strings {
    override val aboutAuthor: String
        get() = "关于作者"
    override val commandPage: String
        get() = "命令界面"
    override val convertPage: String
        get() = "转换界面"
    override val devTools: String
        get() = "开发者工具集"
    override val homePage: String
        get() = "主界面"
    override val simulatorPage: String
        get() = "模拟器界面"
    override val troubleShootPage: String
        get() = "问题定位界面"
}
