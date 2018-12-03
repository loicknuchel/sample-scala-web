package com.sample.core.storage

import com.sample.core.domain.{BasicUser, User}

import scala.language.higherKinds

trait SampleDb[F[_]] {
  def findUsers(): F[Seq[BasicUser]]

  def find(id: User.Id): F[Option[BasicUser]]

  def create(user: BasicUser): F[BasicUser]
}
