package utils

import org.ehcache.{CacheManager, Status}
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.xml.XmlConfiguration
import play.api.{Application, Play, Plugin}

trait CachePlugin extends Plugin {
    def cacheManager: CacheManager
}

class EhCachePlugin(app: Application) extends CachePlugin {

    override lazy val enabled: Boolean = app.configuration.getString("ei.ehcacheplugin").contains("enabled")

    override def onStart() {
        cacheManager.init()
    }

    override def onStop() {
        if (cacheManager.getStatus == Status.AVAILABLE) cacheManager.close()
    }

    private lazy val xmlConfig = {
        val url = Play.classloader(app).getResource("config.xml")
        new XmlConfiguration(url)
    }

    lazy val cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig)
}

object CachePlugin {
    def apply()(implicit app: Application): EhCachePlugin =
        app.plugin[EhCachePlugin].getOrElse(throw new Exception("No EHCachePlugin registered"))
}
