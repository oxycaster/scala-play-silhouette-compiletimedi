import auth.SilhouetteModule
import com.softwaremill.macwire._
import executioncontext.ApplicationExecutionContext
import play.api.ApplicationLoader.Context
import play.api.cache.ehcache.EhCacheComponents
import play.api.i18n.I18nComponents
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import play.filters.HttpFiltersComponents
import router.Routes

class PlayApplicationLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application = {
    new PlayComponents(context).application
  }
}

class PlayComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents
    with controllers.AssetsComponents
    with I18nComponents
    with SilhouetteModule
    with EhCacheComponents
    with AppModule {
  lazy val ec: ApplicationExecutionContext = wire[ApplicationExecutionContext]
  lazy val prefix: String = "/"
  lazy val router: Routes = wire[Routes]
}