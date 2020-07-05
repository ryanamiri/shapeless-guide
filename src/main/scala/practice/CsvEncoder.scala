package practice

import shapeless._

trait CsvEncoder[A] {
  def encode(a: A): List[String]
}

object CsvEncoder {
  implicit val stringEncoder: CsvEncoder[String] = (a: String) => List(a)
  implicit val intEncoder: CsvEncoder[Int] = (a: Int) => List(a.toString)
  implicit val boolEncoder: CsvEncoder[Boolean] = (a: Boolean) => List(if (a) "yes" else "no")
  implicit val doubleEncoder: CsvEncoder[Double] = (a: Double) => List(a.toString)

  implicit val hnilEncoder: CsvEncoder[HNil] = (_: HNil) => Nil
  implicit val cnilEncoder: CsvEncoder[CNil] = (_: CNil) => throw new IllegalStateException("I shouldn't be here.")

  implicit def hlistEncoder[H, T <: HList](implicit
                                           hEnc: CsvEncoder[H],
                                           tEnc: CsvEncoder[T]): CsvEncoder[H :: T] = (a: H :: T) => a match {
    case h :: t => hEnc.encode(h) ++ tEnc.encode(t)
  }
  implicit def genericEncoder[L, H](implicit
                                    b: Generic.Aux[L, H],
                                    hlistEncoder: CsvEncoder[H]
                                   ): CsvEncoder[L] = (a: L) => hlistEncoder.encode(Generic[L].to(a))

  implicit def clistEncoder[L, R <: Coproduct](implicit
                             lEnc: CsvEncoder[L],
                             rEnc: CsvEncoder[R]): CsvEncoder[L :+: R] = new CsvEncoder[L :+: R] {
    override def encode(a: L :+: R): List[String] = a match {
      case Inl(l) => lEnc.encode(l)
      case Inr(r) => rEnc.encode(r)
    }
  }

  implicit class CsvSyntax[A](a: A) {
    def toCsv(implicit csvEncoder: CsvEncoder[A]): List[String] = csvEncoder.encode(a)
  }

}
