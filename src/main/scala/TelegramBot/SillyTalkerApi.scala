package TelegramBot

import cats.effect._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.global

object SillyTalkerApi extends IOApp {
  val app: HttpRoutes[IO] = HttpRoutes
    .of[IO] {
      case GET -> Root / "getGetMyCommands" =>
        for {
          logger   <- Slf4jLogger.create[IO]
          _        <- logger.info(Response.toString)
          response <- Ok("Pong")
        } yield response
    }
  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(app.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
