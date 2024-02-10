package domain.entity

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import domain.valueobject.ユーザーID

case class ユーザー(
                     id: ユーザーID,
                     loginInfo: LoginInfo
                   ) extends Entity[ユーザーID] with Identity