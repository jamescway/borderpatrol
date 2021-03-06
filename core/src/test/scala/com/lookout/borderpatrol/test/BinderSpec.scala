package com.lookout.borderpatrol.test

import java.net.URL

import com.lookout.borderpatrol._
import com.lookout.borderpatrol.Binder._
import com.twitter.finagle.Service
import com.twitter.finagle.httpx.{Status, Response, Request}
import com.twitter.finagle.httpx.path.Path
import com.twitter.util.{Await, Future}

class BinderSpec extends BorderPatrolSuite {
  val urls = Set(new URL("http://localhost:5679"))

  //  Managers
  val keymasterIdManager = Manager("keymaster", Path("/identityProvider"), urls)
  val keymasterAccessManager = Manager("keymaster", Path("/accessIssuer"), urls)
  val checkpointLoginManager = LoginManager("checkpoint", Path("/check"), urls, Path("/loginConfirm"),
    keymasterIdManager, keymasterAccessManager)

  // sids
  val one = ServiceIdentifier("one", urls, Path("/ent"), "enterprise", checkpointLoginManager)

  // Request helper
  def req(path: String): Request =
    Request(s"http://localhost${path.toString}")

  def mkTestService[A](f: (A) => Future[Response]) : Service[A, Response] = new Service[A, Response] {
    def apply(request: A) = f(request)
  }

  behavior of "ManagerBinder"

  it should "successfully connect to server and returns the response" in {
    val server = com.twitter.finagle.Httpx.serve(
      "localhost:5679", mkTestService[Request]{_ => Response(Status.NotAcceptable).toFuture })
    try {
      val bindReq = BindRequest[Manager](keymasterIdManager, req(keymasterIdManager.path.toString))
      val output = ManagerBinder(bindReq)
      Await.result(output).status should be(Status.NotAcceptable)
      /* Make sure client is cached in the cache */
      ManagerBinder.get(keymasterIdManager.name) should not be None
    } finally {
      server.close()
    }
  }

  behavior of "LoginManagerBinder"

  it should "successfully connect to server and returns the response" in {
    val server = com.twitter.finagle.Httpx.serve(
      "localhost:5679", mkTestService[Request]{_ => Response(Status.NotAcceptable).toFuture })
    try {
      val bindReq = BindRequest[LoginManager](checkpointLoginManager, req(checkpointLoginManager.path.toString))
      val output = LoginManagerBinder(bindReq)
      Await.result(output).status should be(Status.NotAcceptable)
      /* Make sure client is cached in the cache */
      LoginManagerBinder.get(checkpointLoginManager.name) should not be None
    } finally {
      server.close()
    }
  }

  behavior of "ServiceIdentifierBinder"

  it should "successfully connect to server and returns the response" in {
    val server = com.twitter.finagle.Httpx.serve(
      "localhost:5679", mkTestService[Request]{_ => Response(Status.NotAcceptable).toFuture })
    try {
      val bindReq = BindRequest[ServiceIdentifier](one, req(one.path.toString))
      val output = ServiceIdentifierBinder(bindReq)
      Await.result(output).status should be(Status.NotAcceptable)
      /* Make sure client is cached in the cache */
      ServiceIdentifierBinder.get(one.name) should not be None
    } finally {
      server.close()
    }
  }
}