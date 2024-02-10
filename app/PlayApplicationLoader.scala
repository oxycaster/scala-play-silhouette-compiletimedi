import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import play.api.ApplicationLoader.Context
import play.filters.HttpFiltersComponents
import router.Routes

class PlayApplicationLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application = new PlayComponents(context).application
}

class PlayComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents
    with controllers.AssetsComponents {
  lazy val applicationController = new controllers.Application(controllerComponents)
  lazy val router: Routes = new Routes(httpErrorHandler, applicationController, assets)
}