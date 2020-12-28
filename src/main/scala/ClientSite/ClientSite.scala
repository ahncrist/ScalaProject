package ClientSite

import cats.effect._
import org.http4s.client.{Client, JavaNetClientBuilder}

import java.util.concurrent.Executors

object ClientSite extends IOApp {
  def ping: IO[Unit] = {
    val blockingEC             = Executors.newFixedThreadPool(5)
    val httpClient: Client[IO] = JavaNetClientBuilder[IO](Blocker.liftExecutorService(blockingEC)).create
    val response2              = httpClient.expect[String]("http://localhost:8080/Bazzegeuse")
    val response3              = httpClient.expect[String]("http://localhost:8080/MA")
    for {
      _ <- response2.map(responseString => println(responseString))
      _ <- response3.map(responseString => println(responseString))
    } yield ()
  }

  def run(args: List[String]): IO[ExitCode] = ping.as(ExitCode.Success)
}
