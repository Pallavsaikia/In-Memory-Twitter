package com.pallav.InmemoryTwitter.in_memory_db.decorators;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PriorityComparator {
}