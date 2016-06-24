URL Shortener API
=================

Stores and retrieves short URLs.

API Documentation
-----------------

#### GET /:alias

Decode the alias and redirect to the URL.

**Response:**

```
HTTP/1.1 301 Moved Permanently
Location: http://www.nubank.com.br/
```

#### POST /

**Request:**

Shorts an URL an returns the

```json
{"url":"http://www.nubank.com.br"}
```

**Response:**

```json
{"alias":"7e6d1"}
```
