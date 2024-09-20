package com.company;

import java.util.HashMap;
import java.util.Scanner;

public class Main
{
    //Create Busy and Free lists
    static LinkedList <Block> busyList = new LinkedList<>();
    static LinkedList <Block> freeList = new LinkedList<>();

    //Busy list initially contains two occupied blocks

    public static void listFreeBlocks()
    {
        //Traverse the linked list and print out the free blocks

        System.out.println("Address\t\tSize\t\tBusy");
        System.out.println("------------------------------------");

        ListNode <Block> temp = new ListNode<>();
        temp = freeList.getHead();

        while(temp != null)
        {
            System.out.println(temp.getData().getAddress() + "\t\t" + temp.getData().sizeAddress() + "\t\t\t" + temp.getData().status());
            temp = temp.getNext();
        }
    }

    public static void listBusyBlocks()
    {
        //Traverse the linked list and print out the busy blocks

        System.out.println("Address\t\tSize\t\tBusy");
        System.out.println("------------------------------------");

        ListNode <Block> temp = new ListNode<>();
        temp = busyList.getHead();

        while(temp != null)
        {
            System.out.println(temp.getData().getAddress() + "\t\t" + temp.getData().sizeAddress() + "\t\t\t" + temp.getData().status());
            temp = temp.getNext();
        }
    }

























    public static void deallocateBlock() {
        listBusyBlocks();

        // take address from the user
        Scanner reader = new Scanner(System.in);
        System.out.println("\nEnter Address:");
        int address = reader.nextInt();

        // retrieve node
        ListNode<Block> busyTemp = new ListNode<>();
        busyTemp = busyList.getHead();
        ListNode<Block> deallocateNode = new ListNode<>();

        while (busyTemp != null) {
            if (busyTemp.getData().getAddress() == address) {
                deallocateNode = busyTemp;
                break;
            }
            busyTemp = busyTemp.getNext();
        }


        // retrieve freeList and start comparing nodes to free Nodes
        ListNode<Block> freeTemp = new ListNode<>();
        freeTemp = freeList.getHead();

        // creating node positions for variables to delete if necessary
        Block sandwich1Block = new Block(0, 0, true);
        Block sandwich2Block = new Block(0, 0, false);
        // booleans for deallocation systems
        boolean mergeDown = false;
        boolean mergeUp = false;
        boolean sandwich = false;
        boolean isolate = false;
        // position in free list for Allocation Node
        int position = 0;
        int sandwich1Position = 0;
        int sandwich2Position = 0;

        if (freeTemp == null) {
            freeTemp = deallocateNode;
        } else if (freeList.length() == 1) {

            // variables for addresses
            int tempAddress = freeTemp.getData().getAddress();
            int tempSize = freeTemp.getData().size;
            int deallocateAddress = deallocateNode.getData().getAddress();
            int deallocateSize = deallocateNode.getData().size;
            if (Math.abs(tempAddress - deallocateAddress) <= 1000) {
                 if (tempAddress > deallocateAddress) {

                     // combined block and node
                    Block mergeDBlock = new Block(deallocateAddress, tempSize + deallocateSize, false);
                    ListNode <Block> mergeDNode = new ListNode<>(mergeDBlock);

                    // remove temp from freelist and add new merged block and remove deallocated from busylist
                    freeList.clearList();
                    freeList.insertAtBegin(mergeDNode);
                    busyList.remove(busyList.getPosition(deallocateNode.getData()));

                 } else if (tempAddress < deallocateAddress) {

                    // combined block and node
                    Block mergeUBlock = new Block(tempAddress, tempSize + deallocateSize, false);
                    ListNode <Block> mergeUNode = new ListNode<>(mergeUBlock);

                    // remove temp from freelist and add new merge block
                    freeList.clearList();
                    freeList.insertAtBegin(mergeUNode);
                    busyList.remove(busyList.getPosition(deallocateNode.getData()));
                 }

            }
        } else {
            // looping through the freelist to determine deallocation system
            while (freeTemp != null) {
                ListNode<Block> nextNode = freeTemp.getNext();

                // checking if deallocated Node is sandwiched in
                if (nextNode != null) {

                    // merging in between two nodes
                    if (Math.abs(deallocateNode.getData().getAddress() - freeTemp.getData().getAddress()) <= 1000
                            && Math.abs(deallocateNode.getData().getAddress() - nextNode.getData().getAddress()) <= 1000) {

                        // getting data for the sandwich nodes
                        sandwich1Block.address = freeTemp.getData().getAddress();
                        sandwich1Block.size = freeTemp.getData().size;
                        sandwich1Block.status = freeTemp.getData().status;
                        // sandwich 2 data
                        sandwich2Block.address = nextNode.getData().getAddress();
                        sandwich2Block.size = nextNode.getData().size;
                        sandwich2Block.status = nextNode.getData().status;
                        // position data
                        position = freeList.getPosition(nextNode.getData());
                        sandwich1Position = freeList.getPosition(freeTemp.getData());
                        sandwich2Position = freeList.getPosition(nextNode.getData());

                        // if this boolean is on then sandwich system takes place
                        sandwich = true;
                        break;


                    //  merging down if next isn't null
                    } else if (Math.abs(deallocateNode.getData().getAddress() - nextNode.getData().getAddress()) <= 1000) {

                        // getting data for below block
                        sandwich2Block.address = nextNode.getData().getAddress();
                        sandwich2Block.size = nextNode.getData().size;
                        sandwich2Block.status = nextNode.getData().status;

                        // position data for below block
                        position = freeList.getPosition(freeTemp.getData());
                        sandwich2Position = freeList.getPosition(nextNode.getData());

                        // merge up on switch
                        mergeDown = true;
                        break;


                    // merging up if next isn't null
                    } else if (Math.abs(deallocateNode.getData().getAddress() - freeTemp.getData().getAddress()) <= 1000) {

                        // getting data for below block
                        sandwich1Block.address = freeTemp.getData().getAddress();
                        sandwich1Block.size = freeTemp.getData().size;
                        sandwich1Block.status = freeTemp.getData().status;

                        // position data for below block
                        position = freeList.getPosition(nextNode.getData());
                        sandwich1Position = freeList.getPosition(freeTemp.getData());

                        // merge up on switch
                        mergeUp = true;
                        break;


                    // isolated add in less than
                    } else if (deallocateNode.getData().address > freeTemp.getData().address &&
                            deallocateNode.getData().address < nextNode.getData().address) {
                        position = freeList.getPosition(nextNode.getData());
                    }

                    freeTemp = nextNode;


                // emd of non-null next node
                }

                //


            }

            if (sandwich) {

                // New size
                int sandwichMergedSize = sandwich1Block.size + sandwich2Block.size + deallocateNode.getData().size;

                // create a new merged node to place into the free list
                Block mergedFreeBlock = new Block(sandwich1Block.address, sandwichMergedSize, false);

                // place the block/node into the freelist
                freeList.insert(mergedFreeBlock, position);

                // remove sandwich 1 and 2 from the freelist and deallocation node from busy list
                freeList.remove(sandwich1Position);
                freeList.remove(sandwich2Position);
                busyList.remove(busyList.getPosition(deallocateNode.getData()));

            } else if (mergeDown) {

                // New size
                int mergeDownSize = sandwich2Block.size + deallocateNode.getData().size;

                // create a new merged node to place into the free list
                Block mergedDownBlock = new Block(deallocateNode.getData().address, mergeDownSize, false);

                // place block into free list
                freeList.insert(mergedDownBlock, position);

                // remove below block from freelist and deallocation node from busylist
                freeList.remove(sandwich2Position);
                busyList.remove(busyList.getPosition(deallocateNode.getData()));

            } else if (mergeUp) {

                // New size
                int mergeUpSize = sandwich1Block.size + deallocateNode.getData().size;

                // create a new merged node to place into the free list
                Block mergeUpBlock = new Block(deallocateNode.getData().address, mergeUpSize, false);

                // place block into free list
                freeList.insert(mergeUpBlock, position);

                // remove below block from freelist and deallocation node from busylist
                freeList.remove(sandwich1Position);
                busyList.remove(busyList.getPosition(deallocateNode.getData()));

            } else if (isolate) {

            //

            }
        }


    }
































    public static void main(String[] args)
    {
        int choice;
        Scanner s = new Scanner(System.in) ;

        //Create memory blocks
        Block b1 = new Block(10000, 500,true);
        Block b2 = new Block(11000, 500, true);

        //Create linked list nodes containing memory blocks
        ListNode n1 = new ListNode <Block>(b1);
        ListNode n2 = new ListNode <Block>(b2);

        // custom blocks
//        Block freeBlock1 = new Block(9500, 500, false);
        Block freeBlock2 = new Block(10500, 500, false);
//        ListNode freeNode1 = new ListNode(freeBlock1);
        ListNode freeNode2 = new ListNode(freeBlock2);
//        freeList.insertAtEnd(freeNode1);
        freeList.insertAtEnd(freeNode2);


        //Insert blocks into busy list
        busyList.insertAtEnd(n1);
        busyList.insertAtEnd(n2);

        do {
            System.out.println("Memory Deallocation Simulator:");
            System.out.println("1) List all free memory blocks");
            System.out.println("2) List all busy memory blocks");
            System.out.println("3) Deallocate block");
            System.out.println("4) Reset memory");
            System.out.println("5) Quit");
            choice = s.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    listFreeBlocks();
                    System.out.println();
                    break;
                case 2:

                    listBusyBlocks();
                    System.out.println();
                    break;

                case 3:
                    // actual function calls
                    deallocateBlock(); //When this method is implemented, uncomment this line.

                    System.out.println("\n");
                    break;

                case 4:
                    //Clear lists and populate busy list with two blocks
                    freeList.clearList();
                    busyList.clearList();
                    b1 = new Block(10000, 500,true);
                    b2 = new Block(11000, 500, true);
                    n1 = new ListNode <>(b1);
                    n2 = new ListNode <>(b2);
                    busyList.insertAtEnd(n1);
                    busyList.insertAtEnd(n2);
                    break;

                case 5:
                    //Quit program
                    System.out.println("Goodbye");
            }

        } while (choice != 5);

    }


}