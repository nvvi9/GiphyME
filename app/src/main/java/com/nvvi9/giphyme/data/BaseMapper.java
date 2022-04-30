package com.nvvi9.giphyme.data;

public interface BaseMapper<T, R> {
    R map(T value);
}
