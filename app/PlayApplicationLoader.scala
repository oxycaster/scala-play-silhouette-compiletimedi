import com.softwaremill.macwire._
import play.api.ApplicationLoader.Context
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
    with AppModule {
  lazy val ec = new ApplicationExecutionContext(actorSystem)
  lazy val prefix: String = "/"
  lazy val router: Routes = wire[Routes]
}