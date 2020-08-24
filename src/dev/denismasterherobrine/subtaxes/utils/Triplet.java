package dev.denismasterherobrine.subtaxes.utils;

import java.io.Serializable;
import java.util.Objects;

public class Triplet<A, B, C> implements Serializable {
    private static final long serialVersionUID = 2064106661974743357L;
    public A a;

    public B b;

    public C c;

    /**
     * Creates a new triplet
     * @param a The a value to use for this triplet
     * @param b The b value to use for this triplet
     * @param c The c value to use for this triplet
     */
    public Triplet(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * <p><code>String</code> representation of this
     * <code>Triplet</code>.</p>
     *
     *  @return <code>String</code> representation of this <code>Triplet</code>
     */
    @Override
    public String toString() {
        return a + ";" + b + ";" + c;
    }

    /**
     * <p>Generate a hash code for this <code>Triplet</code>.</p>
     *
     * <p>The hash code is calculated using both the name and
     * the value of the <code>Triplet</code>.</p>
     *
     * @return hash code for this <code>Triplet</code>
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (a != null ? a.hashCode() : 0);
        hash = 31 * hash + (b != null ? b.hashCode() : 0);
        hash = 31 * hash + (c != null ? c.hashCode() : 0);
        return hash;
    }

    /**
     * <p>Test this <code>Triplet</code> for equality with another
     * <code>Object</code>.</p>
     *
     * <p>If the <code>Object</code> to be tested is not a
     * <code>Triplet</code> or is <code>null</code>, then this method
     * returns <code>false</code>.</p>
     *
     * <p>Two <code>Triplet</code>s are considered equal if and only if
     * both the values are equal.</p>
     *
     * @param o the <code>Object</code> to test for
     * equality with this <code>Triplet</code>
     * @return <code>true</code> if the given <code>Object</code> is
     * equal to this <code>Triplet</code> else <code>false</code>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Triplet) {
            Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
            if (!Objects.equals(a, triplet.a))
                return false;
            if (!Objects.equals(b, triplet.b))
                return false;
            if (!Objects.equals(c, triplet.c))
                return false;
            return true;
        }
        return false;
    }
}
