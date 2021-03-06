package com.lookout.borderpatrol.crypto

import java.security.Key
import javax.crypto.Mac
import com.lookout.borderpatrol.util.Combinators.tap

/**
 * Mixin for being able to sign bytes
 * It implements HMAC, recommended to use a decent hashing algorithm like
 * SHA256
 */
trait Signer {
  val algo: String
  val key: Key
  def hmac: Mac =
    tap(Mac.getInstance(algo))(mac => mac.init(key))

  /**
   * Sign the input bytes using configured algorithm
   * @param bytes
   * @return Signature
   */
  def sign(bytes: IndexedSeq[Byte]): Signature =
    hmac.doFinal(bytes.toArray).toIndexedSeq
}

