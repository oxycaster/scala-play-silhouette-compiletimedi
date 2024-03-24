package domain.service

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import domain.entity
import domain.valueobject.ユーザーID

import scala.concurrent.Future

class ユーザー {
  def 新規登録(名前: String, メールアドレス: String, パスワード: String): Unit = ???

  def ログイン(メールアドレス: String, パスワード: String): Future[entity.ユーザー] = {
    val user: entity.ユーザー = entity.ユーザー(
      ユーザーID(メールアドレス),
      entity.ロール.User,
      LoginInfo(CredentialsProvider.ID, メールアドレス)
    )
    Future.successful(user)
  }
}
