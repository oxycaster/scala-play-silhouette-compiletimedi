package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class Application (cc: ControllerComponents) extends AbstractController(cc) {
  def index: Action[AnyContent] = Action {
    Ok("こんにちは！こんにちは！！こんにちは！！！")
  }
}
