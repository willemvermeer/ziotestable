import zio.UIO

trait Console {
  val console: Console.Service
}
object Console {
  trait Service {
    def println(line: String): UIO[Unit]
    val readLine: UIO[String]
  }
}
