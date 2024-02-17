package controllers

import com.mohiva.play.silhouette.api.{Environment, EventBus, LoginEvent, LoginInfo, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import domain.entity.ユーザー
import domain.{entity, service}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import util.DefaultEnv

import scala.concurrent.{ExecutionContext, Future}

case class LoginForm(email: String, password: String)

class Application (
                    silhouette: Silhouette[DefaultEnv],
                    eventBus: EventBus,
                    userService: service.ユーザー,
                    cc: ControllerComponents,
                  )(implicit val ec: ExecutionContext) extends AbstractController(cc) {

  private val loginForm = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def index: Action[AnyContent] = silhouette.SecuredAction {
    Ok("こんにちは！こんにちは！！こんにちは！！！")
  }

  def signin(): Action[AnyContent] = Action.async { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => {
        Future.successful(BadRequest("ログイン失敗"))
      },
      loginData => {
        val loginInfo = LoginInfo(CredentialsProvider.ID, loginData.email)
        userService.ログイン(loginData.email, loginData.password).map { user: entity.ユーザー =>
          eventBus.publish(LoginEvent[ユーザー](user, request))
          Ok("ログイン成功")
        }
      }
    )
  }
}
