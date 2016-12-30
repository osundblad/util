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
package se.eris.jtype.collection.math;

import java.util.*;

public class ImmutableSetCreator<E> implements SetCreator<E> {

    @SafeVarargs
    public static <T> Set<T> of(final T... items) {
        return immutable(Arrays.asList(items));
    }

    public static <T> Set<T> of(final Collection<T> collection) {
        return immutable(collection);
    }

    private static <T> Set<T> immutable(final Collection<T> collection) {
        return Collections.<T>unmodifiableSet(new HashSet<>(collection));
    }

    @Override
    public Set<E> from(final Collection<E> collection) {
        return immutable(collection);
    }

}
