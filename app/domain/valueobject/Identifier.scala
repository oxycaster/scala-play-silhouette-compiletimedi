package domain.valueobject

trait Identifier[A] {
  def value: A
  override def equals(obj: Any): Boolean = obj match {
    case other: Identifier[_] => this.value == other.value
    case _ => false
  }
  override def hashCode: Int = value.hashCode
}
