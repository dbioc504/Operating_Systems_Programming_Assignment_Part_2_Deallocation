package com.company;

public class Block {

    int address;    //Store address of block
    int size;       //Size of block
    boolean status; //Status of block. Value is true if busy, false otherwise.

    public Block(int address, int size, boolean status)
    {
        this.address = address;
        this.size = size;
        this.status = status;
    }

    public int getAddress()
    {
        return address;
    }
    public int sizeAddress()
    {
        return size;
    }
    public boolean status()
    {
        return status;
    }

}
