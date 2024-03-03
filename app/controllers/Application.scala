package controllers

import auth.{DefaultEnv, IdentityServiceImpl}
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.{Environment, EventBus, LoginEvent, LoginInfo, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import domain.entity.ユーザー
import domain.{entity, service}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

case class LoginForm(email: String, password: String)

class Application (
                    val env: Environment[DefaultEnv],
                    silhouette: Silhouette[DefaultEnv],
                    eventBus: EventBus,
                    userService: service.ユーザー,
                    cc: ControllerComponents,
                  )(implicit val ec: ExecutionContext) extends AbstractController(cc){

  private val loginForm = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def index: Action[AnyContent] = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok("こんにちは！こんにちは！！こんにちは！！！"))
  }

  def signin(): Action[AnyContent] = silhouette.UnsecuredAction { implicit request =>
    Ok(views.html.signin())
  }

  def authenticate(): Action[AnyContent] = silhouette.UserAwareAction.async { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => {
        Future.successful(BadRequest("ログイン失敗"))
      },
      loginData => {
        val loginInfo = LoginInfo(CredentialsProvider.ID, loginData.email)
        userService.ログイン(loginData.email, loginData.password).flatMap { user: entity.ユーザー =>
          for {
            authenticator <- silhouette.env.authenticatorService.create(loginInfo)
            cookie <- silhouette.env.authenticatorService.init(authenticator)
            result <- silhouette.env.authenticatorService.embed(cookie, {
              silhouette.env.eventBus.publish(LoginEvent(user, request))
              Ok("ログインに成功")
            })
          } yield result
        }
      }
    )
  }
}
