import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Earth extends SpaceObject {

    def draw(g: GraphicsContext, scale: Double, x: Double, y: Double): Unit = {
        g.setFill(Color.LightBlue)
        g.fillOval(x, y, 1 * (scale / 109), 1 * (scale / 109))
    }

}