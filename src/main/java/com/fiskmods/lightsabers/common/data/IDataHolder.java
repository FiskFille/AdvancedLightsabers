package com.fiskmods.lightsabers.common.data;

public interface IDataHolder
{
    <T> void set(ALData<T> data, T value);

    <T> T get(ALData<T> data);
}
