package TelegramBot.Model

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class TGPrivateChat(
  id: Long,
  first_name: String,
  last_name: Option[String],
  username: Option[String],
  type_chat: String
)

object TGPrivateChat {
  implicit val tgPrivateChatDecoder: Decoder[TGPrivateChat] =
    Decoder.forProduct5("id", "first_name", "last_name", "username", "type")(TGPrivateChat.apply)
}

case class TGGroupChat(id: Long, title: String, type_chat: String)

object TGGroupChat {
  implicit val tgGroupChatDecoder: Decoder[TGGroupChat] =
    Decoder.forProduct3("id", "title", "type")(TGGroupChat.apply)
}

case class TGFrom(
  id: Long,
  first_name: String,
  last_name: Option[String],
  username: Option[String],
  language_code: String
)

object TGFrom {
  implicit val tgFromDecoder: Decoder[TGFrom] = deriveDecoder
}

case class TGMessage(
  message_id: Long,
  from: TGFrom,
  chat: Either[TGPrivateChat, TGGroupChat],
  date: Long,
  text: Option[String]
)

object TGMessage {
  implicit val chatDecoder: Decoder[Either[TGPrivateChat, TGGroupChat]] =
    TGPrivateChat.tgPrivateChatDecoder.either(TGGroupChat.tgGroupChatDecoder)
  implicit val tgMessageDecoder: Decoder[TGMessage] = deriveDecoder
}

case class TGResult(update_id: Long, message: TGMessage)

object TGResult {
  implicit val tgResultDecoder: Decoder[TGResult] = deriveDecoder
}

case class TGResponse(ok: Boolean, result: List[TGResult])

object TGResponse {
  implicit val tgResponseDecoder: Decoder[TGResponse] = deriveDecoder
}
