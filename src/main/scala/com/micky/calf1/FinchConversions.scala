package com.micky.calf1

import com.twitter.util.{Future => TFuture, Promise => TPromise}
import scala.concurrent.{Future => SFuture, ExecutionContext}
import scala.util.{Success, Failure}

object FinchConversions{
  implicit class RichSFuture[A](f: SFuture[A]) {
    def asTwitter(implicit e: ExecutionContext): TFuture[A] = {
      val p: TPromise[A] = new TPromise[A]
      f.onComplete {
        case Success(value) => p.setValue(value)
        case Failure(exception) => p.setException(exception)
      }
      p
    }
  }
}

