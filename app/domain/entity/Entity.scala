package domain.entity

import domain.valueobject.Identifier

trait Entity[ID <: Identifier[_]] {
  val id: ID
  override def equals(obj: Any): Boolean = obj match {
    case other: Entity[_] => this.id == other.id
    case _ => false
  }
  override def hashCode: Int = id.hashCode
}
