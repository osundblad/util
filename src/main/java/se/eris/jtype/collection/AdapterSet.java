/*
 *    Copyright 2016 Olle Sundblad - olle@eris.se
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package se.eris.jtype.collection;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An immutable set using the supplied hashcode and equals functions. Note that null values are
 * not allowed in this set.
 * @param <T>
 */
public class AdapterSet<T> implements Set<T>, Serializable {

    public static <T> AdapterSet<T> from(final HashcodeEquals<T> he, final Collection<T> collection) {
        return new AdapterSet<T>(collection, he);
    }
    public static <T> AdapterSet<T> from(final HashcodeEquals<T> he, final T... items) {
        return new AdapterSet<T>(Arrays.asList(items), he);
    }
    private final Set<HashcodeEqualsDecorator<T>> set;

    private final HashcodeEquals<T> he;

    private AdapterSet(final Collection<T> collection, final HashcodeEquals<T> he) {
        this.set = collection.stream().map(o -> HashcodeEqualsDecorator.of(o, he)).collect(Collectors.toSet());
        this.he = he;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        try {
            return set.contains(HashcodeEqualsDecorator.of((T) o, he));
        } catch (final ClassCastException e) {
            return false;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return set.stream().map(HashcodeEqualsDecorator::getSubject).iterator();
    }

    @Override
    public boolean add(final Object o) {
        unsupportedOperationImmutable();
        return false;
    }

    @Override
    public boolean remove(final Object o) {
        unsupportedOperationImmutable();
        return false;
    }

    @Override
    public boolean addAll(final Collection c) {
        unsupportedOperationImmutable();
        return false;
    }

    @Override
    public void clear() {
        unsupportedOperationImmutable();
        return;
    }

    @Override
    public boolean removeAll(final Collection c) {
        unsupportedOperationImmutable();
        return false;
    }

    @Override
    public boolean retainAll(final Collection c) {
        unsupportedOperationImmutable();
        return false;
    }

    @Override
    public boolean containsAll(final Collection c) {
        if (this == c) {
            return true;
        }
        for (final Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return set.stream().map(HashcodeEqualsDecorator::getSubject).toArray();
    }

    @Override
    public Object[] toArray(final Object[] a) {
        return set.stream().map(HashcodeEqualsDecorator::getSubject).collect(Collectors.toList()).toArray(a);
    }

    @Override
    public Spliterator spliterator() {
        return set.stream().map(HashcodeEqualsDecorator::getSubject).spliterator();
    }

    private void unsupportedOperationImmutable() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " is immutable");
    }

    public Set<T> asSet() {
        return set.stream().map(HashcodeEqualsDecorator::getSubject).collect(Collectors.toSet());
    }

    public Stream<T> asStream() {
        return set.stream().map(HashcodeEqualsDecorator::getSubject);
    }

    @SuppressWarnings("ControlFlowStatementWithoutBraces")
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ((o == null) || (!(o instanceof Set))) return false;

        final Collection<?> that = (Collection<?>) o;

        return (set.size() == that.size()) && containsAll(that);
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (final HashcodeEqualsDecorator<T> adapter : set) {
            code += adapter.hashCode();
        }
        return code;
    }

    @Override
    public String toString() {
        return "AdapterSet{" +
                "set=" + set +
                ", he=" + he +
                '}';
    }

}
