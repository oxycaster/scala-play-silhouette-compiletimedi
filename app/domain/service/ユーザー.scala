package domain.service

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService

import scala.concurrent.Future
import domain.entity

class ユーザー extends IdentityService[entity.ユーザー] {
  def 新規登録(名前: String, メールアドレス: String, パスワード: String): Unit = ???

  def ログイン(メールアドレス: String, パスワード: String): Future[entity.ユーザー] = ???

  override def retrieve(loginInfo: LoginInfo): Future[Option[entity.ユーザー]] = ???

}
