package auth

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import domain.entity.ユーザー
import domain.valueobject.ユーザーID

import scala.concurrent.Future

class IdentityServiceImpl() extends IdentityService[ユーザー] {
  override def retrieve(loginInfo: LoginInfo): Future[Option[ユーザー]] = {
    val user: ユーザー = ユーザー(
      ユーザーID(loginInfo.providerKey),
      domain.entity.ロール.Admin,
      loginInfo
    )
    Future.successful(Some(user))
  }
}
