package TelegramBot

import TelegramBot.Model.{Config, TGGroupChat, TGPrivateChat, TGResponse, TGResult}
import cats.effect.IO._
import cats.effect._
import org.http4s.circe._
import org.http4s.client.{Client, JavaNetClientBuilder}
import pureconfig._
import pureconfig.generic.auto._
import pureconfig.module.http4s._

import java.util.concurrent.{ExecutorService, Executors}

object ClientJSONResponser extends IOApp {
  val config: IO[Config]          = IO(ConfigSource.default.loadOrThrow[Config])
  val blockingEC: ExecutorService = Executors.newFixedThreadPool(2)
  val httpClient: Client[IO]      = JavaNetClientBuilder[IO](Blocker.liftExecutorService(blockingEC)).create
  def updateReciver: IO[Unit] = {
    val response: IO[TGResponse] = config.flatMap(sendAPIRequest)
    jsonResponseDecoder(response)
  }
  def sendAPIRequest(config: Config): IO[TGResponse] =
    httpClient
      .expect[TGResponse](
        s"${config.telegramBaseUri}${config.token}/getUpdates"
      )(jsonOf[IO, TGResponse])

  def jsonResponseDecoder(response: IO[TGResponse]): IO[Unit] = {
    response.flatMap(json =>
      if (json.ok)
        jsonIterator(json.result)
      else delay(println("Wrong API Request"))
    )

  }

  def jsonIterator(results: List[TGResult]): IO[Unit] =
    results match {
      case Nil     => delay(println("No updates"))
      case results => resultDecoder(results.last)
    }

  def resultDecoder(response: TGResult): IO[Unit] = {
    val chat_id: Long = response.message.chat match {
      case Right(groupChat: TGGroupChat)    => groupChat.id
      case Left(privateChat: TGPrivateChat) => privateChat.id
    }
    response.message.text match {
      case Some(text) =>
        val returnedText = textParser(text)
        config.flatMap(config => sendMessage(config, chat_id, returnedText))
      case None => failedRequest
    }
  }

  def sendMessage(config: Config, chat_id: Long, textToSend: String): IO[Unit] =
    httpClient.expect[Unit](s"${config.telegramBaseUri}${config.token}/sendMessage?chat_id=$chat_id&text=${textToSend
      .replace(" ", "%20")}")

  def failedRequest: IO[Unit] =
    delay(println("Wrong API Request"))

  def textParser(text: String): String =
    if (text == "/ping")
      "pong"
    else
      "I dont know what to say"

  def run(args: List[String]): IO[ExitCode] = updateReciver.as(ExitCode.Success)
}
