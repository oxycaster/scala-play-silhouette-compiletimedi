import play.api.mvc.ControllerComponents

trait AppModule {
  import com.softwaremill.macwire._
  def controllerComponents: ControllerComponents

  lazy val applicationController = wire[controllers.Application]
}
