import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext

class ApplicationExecutionContext(actorSystem: ActorSystem)
  extends CustomExecutionContext(actorSystem, "application.dispatcher") {

}
