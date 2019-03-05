package cm.lx.bean;

/**
 * @author linxingwei
 * @date 2019/3/4
 */
public class TwoTuple<A, B> {

    public A a;

    public B b;

    public TwoTuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A left(){
        return a;
    }

    public B right(){
        return b;
    }
}
