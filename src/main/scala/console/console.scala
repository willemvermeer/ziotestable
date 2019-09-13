import java.io.IOException

import zio.ZIO

package object console {
  def println(line: String): ZIO[Console, Nothing, Unit] =
    ZIO.accessM(_.console println line)

  val readLine: ZIO[Console, IOException, String] =
    ZIO.accessM(_.console.readLine)
}
