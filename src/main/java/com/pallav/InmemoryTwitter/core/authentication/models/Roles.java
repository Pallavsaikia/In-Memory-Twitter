package com.pallav.InmemoryTwitter.core.authentication.models;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.Index;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PrimaryKey;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PriorityComparator;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.Unique;

import java.util.Comparator;

public record Roles(@PrimaryKey("id")
                        String id,
                        @Index("name")
                        @Unique("name")
                        String name){

    @PriorityComparator
    public static Comparator<Roles> sortByName() {
        return Comparator.comparing(Roles::name);
    }

    @Override
    public String toString() {
        return "Roles{id='" + id + "', name='" + name + "'}";
    }
}
