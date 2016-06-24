package controllers

import javax.inject._

import play.api.cache.CacheApi
import play.api.libs.json.Json
import play.api.mvc._
import utils.UrlShortener

/**
  * Encode and decodes URLs.
  */
@Singleton
class IndexController @Inject()(val cacheApi: CacheApi) extends Controller {

  /**
    * Redirects the alias for the correct URL if exists, return not found otherwise.
    */
  def get(alias: String) = Action {
    cacheApi.get[String](alias).map { url =>
      Redirect(url, PERMANENT_REDIRECT)
    }.getOrElse {
      NotFound
    }
  }

  /**
   * Returns an alias from some URL.
   */
  def create = Action(parse.json) { request =>
    (request.body \ "url").asOpt[String].map { url =>
      val alias = UrlShortener(url)
      // Persists
      cacheApi.set(alias, url)
      // Output alias as JSON
      Ok(Json.obj("alias" -> alias))
    }.getOrElse {
      BadRequest
    }
  }
}
