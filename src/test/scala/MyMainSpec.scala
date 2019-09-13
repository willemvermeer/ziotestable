import Console.Service
import org.scalatest.{ Matchers, WordSpec }
import zio.{ DefaultRuntime, Ref, UIO }

class MyMainSpec extends WordSpec with Matchers with DefaultRuntime with ConsoleProgram {

  case class Test(ref: Ref[Vector[String]]) extends Console {
    val console: Service = new Service {
      def println(line: String): UIO[Unit] = ref.update(_ :+ line).unit
      val readLine: UIO[String] = UIO("Wiem")
    }
  }

  "Console service" should {
    "greet the name" in {
      val result = unsafeRun {
        for {
          state <- Ref.make(Vector.empty[String])
          _ <- program.provide(Test(state))
          result <- state.get
        } yield result
      }
      result shouldBe Vector(
        "Good morning, what is your name?",
        "Good to meet you, Wiem!")
    }
  }
}
