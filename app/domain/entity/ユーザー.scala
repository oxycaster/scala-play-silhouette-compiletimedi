package domain.entity

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import domain.entity.ロール.ロール
import domain.valueobject.ユーザーID

object ロール {
  sealed abstract class ロール(val name: String)
  case object SystemAdmin extends ロール("SystemAdmin")
  case object Admin extends ロール("Admin")
  case object User extends ロール("User")
}

case class ユーザー(
                     id: ユーザーID,
                     ロール: ロール,
                     loginInfo: LoginInfo
                   ) extends Entity[ユーザーID] with Identity