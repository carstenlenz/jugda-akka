package de.carsten_lenz.jugda

import java.net.{URLEncoder, URLDecoder}

/**
 * @author Carsten Lenz, AOE
 */
trait URLEncodingSupport {
  def decodeName(s: String) = URLDecoder.decode(s, "UTF-8")
  def encodeName(s: String) = URLEncoder.encode(s, "UTF-8")
}
