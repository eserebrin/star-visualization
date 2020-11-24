import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Star(r: Double) extends SpaceObject {

    var color = Color.White

    def draw(g: GraphicsContext, scale: Double, x: Int, y: Int): Unit = {
        g.setFill(color)
        g.fillOval(x, y, r * 2 * scale, r * 2 * scale)
    }

}