package Lab7.mesh;

import Lab7.production.PDrawer;

public class GraphDrawer implements PDrawer<Vertex> {

    @Override
    public void draw(Vertex v) {
        //go left
        while (v.mLeft != null) {
            v = v.mLeft;
        }
        //plot
        while (v.mRight != null) {
            System.out.print(v.mLabel + "--");
            v = v.mRight;
        }
        System.out.println(v.mLabel);
    }
}
