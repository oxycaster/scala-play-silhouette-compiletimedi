package auth

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import domain.entity.ユーザー

trait DefaultEnv extends Env {
  type I = ユーザー
  type A = CookieAuthenticator
}
