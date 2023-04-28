package me.lord.posc.utilities.tuples;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Triplet<A, B, C> implements Serializable, Iterable<Object>, Comparable<Triplet<A, B, C>> {
	@Serial
	private static final long serialVersionUID = 2850881044478706787L;

	private A a;
	private B b;
	private C c;

	public Triplet(A valueA, B valueB, C valueC) {
		a = valueA;
		b = valueB;
		c = valueC;
	}

	public Object get(int i) {
		return switch (i) {
			case 0 -> a;
			case 1 -> b;
			case 2 -> c;
			default -> throw new IndexOutOfBoundsException(i);
		};
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}

	public C getC() {
		return c;
	}

	public void remove(int i) {
		switch (i) {
			case 0 -> a = null;
			case 1 -> b = null;
			case 2 -> c = null;
			default -> throw new IndexOutOfBoundsException(i);
		}
	}

	public void removeA() {
		a = null;
	}

	public void removeB() {
		b = null;
	}

	public void removeC() {
		c = null;
	}

	public void setA(A a) {
		this.a = a;
	}

	public void setB(B b) {
		this.b = b;
	}

	public void setC(C c) {
		this.c = c;
	}

	@NotNull
	@Override
	public Iterator<Object> iterator() {
		return new Itr();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public int compareTo(@NotNull Triplet o) {
		Integer comparison = null;

		for (int i = 0; i < 3; i++) {
			if (!(this.get(i) instanceof Comparable tElement)) continue;

			Comparable oElement = (Comparable) o.get(i);

			comparison = tElement.compareTo(oElement);
			if (comparison != 0) return comparison;
		}

		if (comparison == null) {
			throw new RuntimeException("Triplet impossible to compare");
		}

		return comparison;
	}

	private class Itr implements Iterator<Object> {
		int cursor = 0;

		int lastRet = -1;

		public boolean hasNext() {
			return cursor != 2;
		}

		public Object next() {
			try {
				int i = cursor;
				Object next = get(i);
				lastRet = i;
				cursor = i + 1;
				return next;
			} catch (IndexOutOfBoundsException e) {
				throw new NoSuchElementException(e);
			}
		}

		public void remove() {
			if (lastRet < 0) throw new IllegalStateException();

			try {
				Triplet.this.remove(lastRet);
				if (lastRet < cursor)
					cursor--;
				lastRet = -1;
			} catch (IndexOutOfBoundsException e) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
