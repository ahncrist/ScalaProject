package TelegramBot.Model

import org.http4s.Uri

case class Config(telegramBaseUri: Uri, token: String)
