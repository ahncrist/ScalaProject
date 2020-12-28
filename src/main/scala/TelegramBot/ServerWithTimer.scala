package TelegramBot

import cats.effect._
import fs2.Stream
import scala.concurrent.duration._

object ServerWithTimer extends IOApp {

  def doSomething: IO[Unit] = IO(println("Something"))

  def usingStreams[A](doIO: => IO[A]): Stream[IO, A] = Stream.sleep(2.seconds).evalMap(_ => doIO)

  override def run(args: List[String]): IO[ExitCode] =
    usingStreams(doSomething).compile.drain.as(ExitCode.Success)
}
