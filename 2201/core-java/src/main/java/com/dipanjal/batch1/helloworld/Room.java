package com.dipanjal.batch1.helloworld;

//what is a class ?

/**
 * Blueprint of an Object
 * It represents a type: Animal(Human, Dog, Cat)
 */
public class Room {

    //when the class is loaded into JVM Stack Memory to run
    public static String roomId;

    //object variable, created when the object is created or instantiated into Heap Memory
    public double tableX;
    public double tableY;

    // no arg constructor
    public Room() {
        System.out.println("Constructor");
    }

    public Room(String roomId, double tableX, double tableY) {
        System.out.println("roomId:"+roomId);
        System.out.println("tableX:"+tableX);
        System.out.println("tableY:"+tableY);
        System.out.println(joinTableCoordinate(tableX, tableY));

        Room.roomId = roomId;
        this.tableX = tableX;
        this.tableY = tableY;
    }

    private String joinTableCoordinate(double x, double y) {
//        x = 0.0;
//        return String.valueOf(x*y);
//        return x+", "+y;
//        Double multiply = x * y;
//        return multiply.toString();
        return ((Double)(x * y)).toString();
    }

    public Room(String roomId, int noOfFans, int noOfWindows, double tableX, double tableY) {
        roomId = null;
        System.out.println("roomId:"+roomId);
    }

//    public static void main(String[] args) {
//        Room room = new Room("XYZ-101", 100.83948203840, 33.34566);
////        System.out.println(room.toString());
//    }
}
