package com.shadeien.math.copy;

public class Person implements Cloneable{
    public String name;
    public int age;
    public Address address;
    public Person() {}

    public Person(String name,int age){
        this.name = name;
        this.age = age;
        this.address = new Address();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        person.address = (Address) address.clone();

        return person;
    }

    public void setAddress(String provices,String city ){
        address.setAddress(provices, city);
    }
    public void display(String name){
        System.out.println(name+":"+"name=" + name + ", age=" + age +","+ address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
