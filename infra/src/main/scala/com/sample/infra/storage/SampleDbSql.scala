package com.sample.infra.storage

import cats.effect.IO
import com.sample.core.domain.{BasicUser, User}
import com.sample.core.storage.SampleDb
import com.sample.infra.storage.tables.UserTable
import com.sample.infra.utils.DoobieUtils
import doobie.implicits._
import org.flywaydb.core.Flyway

class SampleDbSql(conf: DbConf) extends SampleDb[IO] {
  private val flyway = flywayBuilder(conf)
  private[storage] val xa = DoobieUtils.transactor(conf)

  def createTables(): IO[Int] = IO(flyway.migrate())

  def dropTables(): IO[Unit] = IO(flyway.clean())

  def findUsers(): IO[Seq[BasicUser]] =
    UserTable.selectAll().to[List].transact(xa)

  def find(id: User.Id): IO[Option[BasicUser]] =
    UserTable.selectById(id).option.transact(xa)

  def create(user: BasicUser): IO[BasicUser] =
    UserTable.insert(user).run.transact(xa).map(_ => user)

  private def flywayBuilder(conf: DbConf): Flyway = {
    val flyway = new Flyway()
    flyway.setLocations("classpath:sql")
    val (url, user, pass) = conf match {
      case c: H2 => (c.url, null, null)
      case c: PostgreSQL => (c.url, c.user, c.pass)
    }
    flyway.setDataSource(url, user, pass)
    flyway
  }
}
