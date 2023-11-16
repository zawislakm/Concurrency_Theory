package Lab7.myProductions;

import Lab7.mesh.Vertex;
import Lab7.production.AbstractProduction;
import Lab7.production.PDrawer;

public class P2 extends AbstractProduction<Vertex> {

    public P2(Vertex _obj, PDrawer<Vertex> _drawer) {
        super(_obj, _drawer);
    }

    @Override
    public Vertex apply(Vertex t1) {
        System.out.println("p2");
        Vertex t2 = new Vertex(t1, t1.getRight(), "T2");
        t1.getRight().setLeft(t2);
        t1.setRight(t2);
        return t1;
    }
}
