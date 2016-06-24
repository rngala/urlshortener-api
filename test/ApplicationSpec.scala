import org.scalatestplus.play._
import play.api.cache.CacheApi
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import utils.UrlShortener

class ApplicationSpec extends PlaySpec with OneAppPerTest {
  val url = "http://www.nubank.com.br"
  val alias = UrlShortener(url)

  "IndexController" when {
    "GET /:alias" should {
      "redirect the alias for the URL" in {
        val cacheApi = app.injector.instanceOf[CacheApi]
        cacheApi.set(alias, url)

        val get = route(app, FakeRequest(GET, s"/$alias")).get
        status(get) mustBe PERMANENT_REDIRECT
        header("Location", get) mustBe Some(url)
      }
    }

    "POST /" should {
      "return the url alias and persist at the cache" in {
        val create = route(app, FakeRequest(POST, "/")
          .withJsonBody(
            Json.obj("url" -> url)
          )).get

        status(create) mustBe OK
        contentType(create) mustBe Some("application/json")
        val output = contentAsJson(create)
        (output \ "alias").as[String] mustBe alias

        val cacheApi = app.injector.instanceOf[CacheApi]
        cacheApi.get(alias) mustBe Some(url)
      }
    }
  }
}
