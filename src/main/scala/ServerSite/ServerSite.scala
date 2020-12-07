package ServerSite

import cats.data.Kleisli
import cats.effect._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.global

object ServerSite extends IOApp {

  val app: Kleisli[IO, Request[IO], Response[IO]] = HttpRoutes
    .of[IO] {
      case GET -> Root / "Ping" =>
        for {
          logger   <- Slf4jLogger.create[IO]
          _        <- logger.info("I've recieved a get of Ping")
          response <- Ok("Pong")
        } yield response

      case GET -> Root / "Bazzegeuse" =>
        Ok("Violin Intensifies")
      case GET -> Root / "MA" =>
        Ok("Tu puta madre")
    }
    .orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(app)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
