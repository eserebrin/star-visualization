import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.image.Image

class Spaceship(val x: Int, val y: Int) {

    val img = new Image(getClass.getResource("/images/ship.png").toString)

    var velocity = 1L
    var distance = 0L

    def display(g: GraphicsContext): Unit = {
        g.drawImage(img, x, y)
    }

    def step(): Unit = {
        println(s"Distance: $distance,   velocity: $velocity")
        distance += velocity
        velocity *= 2
    }

}