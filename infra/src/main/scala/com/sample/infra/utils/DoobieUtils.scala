package com.sample.infra.utils

import cats.effect.IO
import com.sample.core.domain.User
import com.sample.infra.storage.{DbConf, H2, PostgreSQL}
import com.sample.lib.scalautils.Extensions._
import doobie._
import doobie.implicits._

object DoobieUtils {
  def transactor(conf: DbConf): doobie.Transactor[IO] = conf match {
    case c: H2 => Transactor.fromDriverManager[IO](c.driver, c.url, "", "")
    case c: PostgreSQL => Transactor.fromDriverManager[IO]("org.postgresql.Driver", c.url, c.user, c.pass)
  }

  def select(table: String, fields: Seq[String]): Fragment =
    fr"select" ++ Fragment.const(fields.mkString(", ")) ++ fr"from" ++ Fragment.const(table)

  def insert(table: String, fields: Seq[String], values: Fragment): Fragment =
    fr"insert into " ++ Fragment.const(table) ++ fr"(" ++ Fragment.const(fields.mkString(", ")) ++ fr") values (" ++ values ++ fr")"

  object Mappings {
    implicit val userIdMeta: Meta[User.Id] = Meta[String].xmap(User.Id.from(_).get, _.value)
  }

}
