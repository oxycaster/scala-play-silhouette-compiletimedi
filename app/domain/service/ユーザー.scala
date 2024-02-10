package domain.service

import scala.concurrent.Future
import domain.entity

class ユーザー {
  def 新規登録(名前: String, メールアドレス: String, パスワード: String): Unit = ???

  def ログイン(メールアドレス: String, パスワード: String): Future[entity.ユーザー] = ???
}
