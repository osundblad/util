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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class HashSetCreator<T> implements SetCreator<T> {

    @SafeVarargs
    public static <E> HashSet<E> of(final E... items) {
        return new HashSet<>(Arrays.asList(items));
    }

    public static <E> HashSet<E> of(final Collection<E> collection) {
        return new HashSet<>(collection);
    }

    @Override
    public HashSet<T> from(final Collection<T> collection) {
        return new HashSet<>(collection);
    }

}
