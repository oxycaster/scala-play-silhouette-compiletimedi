package auth

import com.mohiva.play.silhouette.api.actions._
import com.mohiva.play.silhouette.api.crypto.{AuthenticatorEncoder, CrypterAuthenticatorEncoder, Signer}
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.util.{Clock, FingerprintGenerator, IDGenerator, PlayHTTPLayer}
import com.mohiva.play.silhouette.api.{Environment, EventBus, Silhouette, SilhouetteProvider}
import com.mohiva.play.silhouette.crypto.{JcaCrypter, JcaCrypterSettings, JcaSigner, JcaSignerSettings}
import com.mohiva.play.silhouette.impl.authenticators.{CookieAuthenticator, CookieAuthenticatorService, CookieAuthenticatorSettings}
import com.mohiva.play.silhouette.impl.util.{DefaultFingerprintGenerator, PlayCacheLayer, SecureRandomIDGenerator}
import executioncontext.ApplicationExecutionContext
import play.api.Configuration
import play.api.cache.{AsyncCacheApi, CacheApi}
import play.api.mvc.{BodyParsers, ControllerComponents, CookieHeaderEncoding, DefaultCookieHeaderEncoding}

import scala.concurrent.duration.FiniteDuration

trait SilhouetteModule {
  import com.softwaremill.macwire._
  implicit def ec: ApplicationExecutionContext
  def configuration: Configuration
  def defaultCacheApi: AsyncCacheApi

  lazy val cacheLayer = wire[PlayCacheLayer]

  def controllerComponents: ControllerComponents
  private val playBodyParsers = controllerComponents.parsers
  private val bodyParsers: BodyParsers.Default = new BodyParsers.Default(playBodyParsers)
  private val messagesApi = controllerComponents.messagesApi

  lazy val clock: Clock = Clock()
  lazy val eventBus: EventBus = EventBus()
  lazy val idGenerator = new SecureRandomIDGenerator
  lazy val fingerprintGenerator = new DefaultFingerprintGenerator(false)
  lazy val signer = new JcaSigner(new JcaSignerSettings("someSigner"))
  lazy val cookieHeaderEncoding = new DefaultCookieHeaderEncoding()

  private lazy val crypterConfig = configuration.get[String]("silhouette.authenticator.crypter")
  lazy val crypter = new JcaCrypter(JcaCrypterSettings(crypterConfig))
  lazy val authenticatorEncoder: CrypterAuthenticatorEncoder = wire[CrypterAuthenticatorEncoder]

  private lazy val authenticatorService = wireWith(SilhouetteAuthenticatorService.apply _)

  private object SilhouetteAuthenticatorService {
    def apply(
               fingerprintGenerator: FingerprintGenerator,
               idGenerator: IDGenerator,
               signer: Signer,
               cookieHeaderEncoding: CookieHeaderEncoding,
               authenticatorEncoder: AuthenticatorEncoder,
               clock: Clock,
               configuration: Configuration
             ): AuthenticatorService[CookieAuthenticator] = {
      val settings = CookieAuthenticatorSettings(
        cookieName = configuration.get[String]("silhouette.authenticator.cookieName"),
        cookiePath = configuration.get[String]("silhouette.authenticator.cookiePath"),
        cookieDomain = configuration.getOptional[String]("silhouette.authenticator.cookieDomain"),
        secureCookie = configuration.get[Boolean]("silhouette.authenticator.secureCookie"),
        httpOnlyCookie = configuration.get[Boolean]("silhouette.authenticator.httpOnlyCookie"),
        useFingerprinting = configuration.get[Boolean]("silhouette.authenticator.useFingerprinting"),
        authenticatorIdleTimeout = configuration.getOptional[FiniteDuration]("silhouette.authenticator.authenticatorIdleTimeout"),
        authenticatorExpiry = configuration.get[FiniteDuration]("silhouette.authenticator.authenticatorExpiry")
      )

      new CookieAuthenticatorService(
        settings,
        None,
        signer,
        cookieHeaderEncoding,
        authenticatorEncoder,
        fingerprintGenerator,
        idGenerator,
        clock,
      )
    }
  }

  private lazy val identityServiceImpl = wire[IdentityServiceImpl]

  lazy val silhouetteEnv: Environment[DefaultEnv] = Environment[DefaultEnv](identityServiceImpl, authenticatorService, Seq(), eventBus)
  private val securedAction: SecuredAction = new DefaultSecuredAction(new DefaultSecuredRequestHandler(new DefaultSecuredErrorHandler(messagesApi)), bodyParsers)
  private val unsecuredAction: UnsecuredAction = new DefaultUnsecuredAction(new DefaultUnsecuredRequestHandler(new DefaultUnsecuredErrorHandler(messagesApi)), bodyParsers)
  private val userAwareAction: UserAwareAction = new DefaultUserAwareAction(new DefaultUserAwareRequestHandler(), bodyParsers)

  lazy val silhouette: Silhouette[DefaultEnv] = wire[SilhouetteProvider[DefaultEnv]]

}
