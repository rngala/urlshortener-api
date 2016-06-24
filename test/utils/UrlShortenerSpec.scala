package utils

import org.scalatestplus.play.PlaySpec

class UrlShortenerSpec extends PlaySpec {
  "UrlShortenerService" should {
    "return an deterministic 5 character alias from an URL" in {
      val alias = UrlShortener("http://www.nubank.com.br")

      alias must have length(5)
      // Should genereate the same over and over again...
      alias mustBe UrlShortener("http://www.nubank.com.br")
    }
  }
}
