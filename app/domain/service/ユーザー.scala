package domain.service

import domain.entity

import scala.concurrent.Future

class ユーザー {
  def 新規登録(名前: String, メールアドレス: String, パスワード: String): Unit = ???

  def ログイン(メールアドレス: String, パスワード: String): Future[entity.ユーザー] = ???
}
