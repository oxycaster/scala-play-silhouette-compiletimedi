package auth

import com.mohiva.play.silhouette.api.Authorization
import domain.entity.ユーザー
import domain.entity.ロール.ロール
import play.api.mvc.Request

import scala.concurrent.Future

case class WithRole(role: ロール) extends Authorization[ユーザー, DefaultEnv#A] {
  override def isAuthorized[B](user: ユーザー, authenticator: DefaultEnv#A)(implicit request: Request[B]): Future[Boolean] =
    Future.successful(user.ロール == role)
}