import auth.DefaultEnv
import com.mohiva.play.silhouette.api.{Environment, EventBus, Silhouette}
import domain.service
import executioncontext.ApplicationExecutionContext
import play.api.mvc.ControllerComponents

trait AppModule {
  import com.softwaremill.macwire._

  implicit def ec: ApplicationExecutionContext
  def controllerComponents: ControllerComponents
  def silhouette: Silhouette[DefaultEnv]
  def silhouetteEnv: Environment[DefaultEnv]

  def eventBus: EventBus

  lazy val userService = wire[service.ユーザー]
  lazy val applicationController = wire[controllers.Application]
}
