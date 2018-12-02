package com.sample.infra.storage

sealed trait DbConf extends Product with Serializable

case class H2(driver: String, url: String) extends DbConf

case class PostgreSQL(url: String, user: String, pass: String) extends DbConf
