import java.io.IOException

import Console.Service
import zio.{ DefaultRuntime, IO, UIO, ZIO }

object MyMain extends App with ConsoleProgram {
  val programLive: IO[IOException, String] = program.provide(Live)
  new DefaultRuntime{}.unsafeRun(programLive)
}

trait ConsoleProgram {
  val program: ZIO[Console, IOException, String] =
    for {
      _    <- console.println("Good morning, what is your name?")
      name <- console.readLine
      _    <- console.println(s"Good to meet you, $name!")
    } yield name
}

trait Live extends Console {
  val console: Service = new Service {
    def println(line: String): UIO[Unit] = UIO.effectTotal(scala.Console.println(line))
    val readLine: UIO[String] = UIO(scala.io.StdIn.readLine)
  }
}
object Live extends Live
