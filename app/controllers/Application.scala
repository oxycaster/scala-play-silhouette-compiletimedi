package controllers

import domain.{entity, service}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

case class LoginForm(email: String, password: String)

class Application (
                    userService: service.ユーザー,
                    cc: ControllerComponents,
                   )(implicit val ec: ExecutionContext) extends AbstractController(cc) {

  private val loginForm = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def index: Action[AnyContent] = Action {
    Ok("こんにちは！こんにちは！！こんにちは！！！")
  }

  def signin(): Action[AnyContent] = Action.async { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => {
        Future.successful(BadRequest("ログイン失敗"))
      },
      loginData => {
        userService.ログイン(loginData.email, loginData.password).map { user: entity.ユーザー =>
          Ok("ログイン成功")
        }
      }
    )
  }
}
