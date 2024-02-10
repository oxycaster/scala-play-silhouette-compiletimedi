import com.mohiva.play.silhouette.api.util.Clock
import com.mohiva.play.silhouette.api.{Environment, EventBus, SilhouetteProvider}
import com.mohiva.play.silhouette.impl.util.SecureRandomIDGenerator
import play.api.mvc.ControllerComponents
import domain.service

trait AppModule {
  import com.softwaremill.macwire._
  implicit def ec: ApplicationExecutionContext

  def controllerComponents: ControllerComponents

  lazy val clock = Clock()
  lazy val eventBus = EventBus()
  lazy val idGenerator = new SecureRandomIDGenerator
//  private lazy val env: Environment[DefaultEnv] = Environment[DefaultEnv](
//    userService, authenticatorService, Seq(), eventBus
//  )
  //lazy val env: Environment[DefaultEnv] = wire[SilhouetteProvider[DefaultEnv]]

  lazy val userService = wire[service.ユーザー]
  lazy val applicationController = wire[controllers.Application]
}
