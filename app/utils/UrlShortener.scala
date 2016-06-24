package utils

import java.util.UUID

/**
  * Converts an URL in a deterministic 5 character hash.
  */
object UrlShortener {
  def apply(url: String): String = UUID.nameUUIDFromBytes(url.getBytes).toString.substring(0, 5)
}
