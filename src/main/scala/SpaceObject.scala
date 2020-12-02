import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class SpaceObject(val color: Color, var x: Double, val r: Double) {

    def draw(g: GraphicsContext, scale: Double): Unit = {
        val y = UniverseVisualizer.displayHeight / 2 - r * scale
        val d = r * 2 * scale

        g.setFill(color)
        g.fillOval(x, y, d, d)
    }

    def moveLeft(): Unit = x += 2
    def moveRight(): Unit = x -= 2

}